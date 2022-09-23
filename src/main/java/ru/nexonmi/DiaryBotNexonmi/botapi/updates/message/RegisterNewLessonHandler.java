package ru.nexonmi.DiaryBotNexonmi.botapi.updates.message;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.nexonmi.DiaryBotNexonmi.botapi.updates.service.GetUserInterface;
import ru.nexonmi.DiaryBotNexonmi.botapi.service.MessageService;
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
            return messageService.getReplyMessage(message.getChatId(), MessageEnum.REGISTER_NEW_LESSON.replayCode);
        }

        UserEntity user = get(message.getChatId());
        String newLessonName = message.getText().substring(messageService.getSourceText(MessageEnum.REGISTER_NEW_LESSON.commandCode).length());
        newLessonName = newLessonName.trim();
        try {
            user.getDiary().registerLesson(newLessonName);
        } catch (LessonAlreadyExistsException e) {
            return messageService.getReplyMessage(message.getChatId(),
                    messageService.getSourceText("replay.message.register_new_lesson_error_already_exists_beginning").concat(
                            newLessonName).concat(" ").concat(
                            messageService.getSourceText("replay.message.register_new_lesson_error_already_exists_end")));
        } catch (Exception e) {
            return messageService.getReplyMessage(message.getChatId(),
                    messageService.getSourceText("replay.message.register_new_lesson_error") + " error:" + e.getMessage());
        }
        save(user);
        return messageService.getReplyMessage(message.getChatId(),
                messageService.getSourceText("replay.message.register_new_lesson_ok_beginning").concat(
                        newLessonName).concat(" ").concat(
                        messageService.getSourceText("replay.message.register_new_lesson_ok_end")));

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
