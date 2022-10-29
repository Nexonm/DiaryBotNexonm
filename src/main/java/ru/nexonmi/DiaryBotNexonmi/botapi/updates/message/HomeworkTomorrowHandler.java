package ru.nexonmi.DiaryBotNexonmi.botapi.updates.message;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.nexonmi.DiaryBotNexonmi.botapi.botservice.MessageService;
import ru.nexonmi.DiaryBotNexonmi.botapi.updates.service.GetUserInterface;
import ru.nexonmi.DiaryBotNexonmi.data.repository.DataRepository;
import ru.nexonmi.DiaryBotNexonmi.domain.entity.LessonEntity;
import ru.nexonmi.DiaryBotNexonmi.domain.entity.UserEntity;

import java.util.Date;

public class HomeworkTomorrowHandler extends InputMessageHandler implements GetUserInterface {

    public HomeworkTomorrowHandler(MessageService messageService, DataRepository repository) {
        super(messageService, repository);
    }

    @Override
    protected BotApiMethod<?> handleMessage(Message message) {
        int day = new Date((long) message.getDate() * 1000).getDay();
        try {
            UserEntity user = get(message.getChatId());
            StringBuilder ansStr = new StringBuilder();

            ansStr.append(messageService.getSourceText(MessageEnum.HOMEWORK_TOMORROW.replayCode))
                    .append(messageService.getRussianStringDay(day+1))
                    .append(":\n\n");
            for (int lesID : user.getDiary().getDays()[day -1 + 1].getLessonIDs()) {
                LessonEntity lesson = user.getDiary().getUserLessons().get(lesID);
                ansStr.append(lesson.getName())
                        .append("\n-")
                        .append(lesson.getHomework());
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
