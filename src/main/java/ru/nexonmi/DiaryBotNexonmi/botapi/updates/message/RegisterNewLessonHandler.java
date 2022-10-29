package ru.nexonmi.DiaryBotNexonmi.botapi.updates.message;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.nexonmi.DiaryBotNexonmi.botapi.updates.service.GetUserInterface;
import ru.nexonmi.DiaryBotNexonmi.botapi.botservice.MessageService;
import ru.nexonmi.DiaryBotNexonmi.botapi.updates.service.SaveUserInterface;
import ru.nexonmi.DiaryBotNexonmi.data.repository.DataRepository;
import ru.nexonmi.DiaryBotNexonmi.domain.entity.UserEntity;
import ru.nexonmi.DiaryBotNexonmi.domain.error.LessonAlreadyExistsException;


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
        String[] newLessons = message.getText()
                .substring(messageService.getSourceText(MessageEnum.REGISTER_NEW_LESSON.commandCode).length())
                .split("\n");
        try {
            for (String lessonName : newLessons)
                if (lessonName.trim().length() > 0)
                    user.getDiary().registerLesson(lessonName.trim());
        } catch (LessonAlreadyExistsException e) {
            return messageService.getReplyMessage(message.getChatId(),
                    messageService.getSourceText("replay.message.register_new_lesson_error_already_exists_beginning").concat(
                            e.getLessonName()).concat(" ").concat(
                            messageService.getSourceText("replay.message.register_new_lesson_error_already_exists_end")));
        } catch (Exception e) {
            return messageService.getReplyMessage(message.getChatId(),
                    messageService.getSourceText("replay.message.register_new_lesson_error") + " error:" + e.getMessage());
        }
        save(user);
        StringBuilder strBuilder = new StringBuilder();
        if (newLessons.length > 1) {
            strBuilder.append(messageService.getSourceText("replay.message.register_new_lesson_ok_beginning_few")).append(":\n");
            for (String lessonName : newLessons)
                if (lessonName.trim().length() > 0)
                    strBuilder.append(lessonName.trim()).append("\n");
            strBuilder.append(messageService.getSourceText("replay.message.register_new_lesson_ok_end_few"));
        } else {
            strBuilder.append(messageService.getSourceText("replay.message.register_new_lesson_ok_beginning_one")).append(" ");
            strBuilder.append(newLessons[0]).append(" ");
            strBuilder.append(messageService.getSourceText("replay.message.register_new_lesson_ok_end_one"));
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
