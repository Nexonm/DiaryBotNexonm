package ru.nexonmi.DiaryBotNexonmi.botapi.updates.message;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.nexonmi.DiaryBotNexonmi.botapi.service.MessageService;
import ru.nexonmi.DiaryBotNexonmi.botapi.updates.message.state.StateHandler;
import ru.nexonmi.DiaryBotNexonmi.botapi.updates.service.GetUserInterface;
import ru.nexonmi.DiaryBotNexonmi.data.repository.DataRepository;
import ru.nexonmi.DiaryBotNexonmi.domain.entity.LessonEntity;
import ru.nexonmi.DiaryBotNexonmi.domain.entity.UserEntity;

import java.util.Date;

public class TimetableTodayHandler extends InputMessageHandler implements GetUserInterface {

    public TimetableTodayHandler(MessageService messageService, DataRepository repository) {
        super(messageService, repository);
    }

    @Override
    protected BotApiMethod<?> handleMessage(Message message) {
        Date date = new Date((long) message.getDate() * 1000);
        try {
            UserEntity user = get(message.getChatId());
            StringBuilder ansStr = new StringBuilder();

            ansStr.append(messageService.getSourceText(MessageEnum.TIMETABLE_TODAY.replayCode))
                    .append(messageService.getRussianStringDay(date.getDay()))
                    .append(", ")
                    .append(date.toString().split(" ")[2].startsWith("0") ?
                            date.toString().split(" ")[2].substring(1): date.toString().split(" ")[2])//get day number in month
                    .append(" ")
                    .append(messageService.getRussianStringMonth(date.getMonth()+1))
                    .append(":\n\n");
            if (user.getDiary().getDays()[date.getDay()-1].getLessonIDs().size() == 0)
                ansStr.append(messageService.getSourceText("replay.message.timetable_today.no_lessons"))
                        .append(user.getDiary().getDays()[date.getDate()-1].getLessonIDs().toString());
            else
                for (int lesID : user.getDiary().getDays()[date.getDay() - 1].getLessonIDs()) {
                    ansStr.append(user.getDiary().getUserLessons().get(lesID).getName());
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
