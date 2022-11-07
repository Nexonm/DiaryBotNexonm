package ru.nexonmi.DiaryBotNexonmi.botapi.updates.message;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.nexonmi.DiaryBotNexonmi.botapi.updates.service.GetUserInterface;
import ru.nexonmi.DiaryBotNexonmi.botapi.service.MessageService;
import ru.nexonmi.DiaryBotNexonmi.botapi.updates.service.SaveUserInterface;
import ru.nexonmi.DiaryBotNexonmi.data.repository.DataRepository;
import ru.nexonmi.DiaryBotNexonmi.domain.entity.UserEntity;
import ru.nexonmi.DiaryBotNexonmi.domain.error.LessonAlreadyExistsException;

import java.util.LinkedList;
import java.util.List;


class RegisterNewLessonHandler extends InputMessageHandler implements GetUserInterface, SaveUserInterface {


    public RegisterNewLessonHandler(MessageService messageService, DataRepository repository) {
        super(messageService, repository);
    }

    @Override
    protected SendMessage handleMessage(Message message) {
        if (message.getText().length() <= messageService.getSourceText(MessageEnum.REGISTER_NEW_LESSON.commandCode).length()) {
            return messageService.getReplyMessage(message.getChatId(),
                    messageService.getSourceText(MessageEnum.REGISTER_NEW_LESSON.replayCode));
        }

        UserEntity user = get(message.getChatId());
        LinkedList<String> newLessons = new LinkedList<>(List.of(
                message.getText()
                        .substring(messageService.getSourceText(MessageEnum.REGISTER_NEW_LESSON.commandCode).length())
                        .split("\n")
        ));

        StringBuilder errString = new StringBuilder("");
        for (String lessonName : new LinkedList<>(newLessons)) {
            try {
                System.out.println("lessonName: " + lessonName);
                if (lessonName.trim().length() > 0) {
                    user.getDiary().registerLesson(lessonName.trim());
                }else{
                    newLessons.removeFirstOccurrence(lessonName);
                }
            } catch (LessonAlreadyExistsException e) {
                System.out.println("error.existsLesson: " + lessonName);
                if (errString.length() == 0) {
                    errString.append(messageService.getSourceText("replay.message.register_new_lesson_error_already_exists_beginning"));
                    errString.append(e.getLessonName());
                } else {
                    errString.append(", ");
                    errString.append(e.getLessonName());
                }
                newLessons.removeFirstOccurrence(lessonName);
            } catch (Exception e) {
                e.printStackTrace();
                return messageService.getReplyMessage(message.getChatId(),
                        messageService.getSourceText("replay.message.register_new_lesson_error") + " error:" + e.getMessage());
            }
        }


        if (errString.length() > 0) {
            errString.append(messageService.getSourceText("replay.message.register_new_lesson_error_already_exists_end"));
        }
        save(user);
        StringBuilder strBuilder = new StringBuilder();
        if (newLessons.size() > 1) {
            strBuilder.append(messageService.getSourceText("replay.message.register_new_lesson_ok_beginning_few")).append(":\n");
            for (String lessonName : newLessons)
                if (lessonName != null)
                    if (lessonName.trim().length() > 0)
                        strBuilder.append(lessonName.trim()).append("\n");
            strBuilder.append(messageService.getSourceText("replay.message.register_new_lesson_ok_end_few"));
            strBuilder.append("\n").append(errString.toString());
        } else if (newLessons.size() == 0 && errString.length() > 0) {
            strBuilder.append(errString);
        } else {
            strBuilder.append(messageService.getSourceText("replay.message.register_new_lesson_ok_beginning_one")).append(" ");
            strBuilder.append(newLessons.getFirst()).append(" ");
            strBuilder.append(messageService.getSourceText("replay.message.register_new_lesson_ok_end_one"));
            strBuilder.append("\n").append(errString);
        }
        return messageService.getReplyMessage(message.getChatId(),
                strBuilder.toString());
    }

    @Override
    public void save(UserEntity user) {
        repository.save(user);
    }

    @Override
    public UserEntity get(long chat_id) {
        return repository.get(chat_id);
    }
}
