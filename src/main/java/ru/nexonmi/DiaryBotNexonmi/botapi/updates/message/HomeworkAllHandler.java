package ru.nexonmi.DiaryBotNexonmi.botapi.updates.message;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.nexonmi.DiaryBotNexonmi.botapi.service.MessageService;
import ru.nexonmi.DiaryBotNexonmi.botapi.updates.service.GetUserInterface;
import ru.nexonmi.DiaryBotNexonmi.data.repository.DataRepository;
import ru.nexonmi.DiaryBotNexonmi.domain.entity.LessonEntity;
import ru.nexonmi.DiaryBotNexonmi.domain.entity.UserEntity;

public class HomeworkAllHandler extends InputMessageHandler implements GetUserInterface {

    public HomeworkAllHandler(MessageService messageService, DataRepository repository) {
        super(messageService, repository);
    }

    @Override
    protected BotApiMethod<?> handleMessage(Message message) {
        try {
            UserEntity user = get(message.getChatId());
            StringBuilder ansStr = new StringBuilder();

            ansStr.append(messageService.getSourceText(MessageEnum.HOMEWORK_ALL.replayCode));
            for (LessonEntity lesson : user.getDiary().getUserLessonsArray()) {
                ansStr.append(lesson.getName())
                        .append("\n-")
                        .append(lesson.getHomework())
                        .append("\n");
            }
            return messageService.getReplyMessage(
                    message.getChatId(),
                    ansStr.toString()
            );
        } catch (Exception e) {
            return messageService.getReplyMessage(message.getChatId(),
                    messageService.getSourceText("replay.some_error"));
        }
    }

    @Override
    public UserEntity get(long chat_id) {
        return repository.get(chat_id);
    }
}
