package ru.nexonmi.DiaryBotNexonmi.botapi.updates.callback;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.nexonmi.DiaryBotNexonmi.botapi.updates.service.GetUserInterface;
import ru.nexonmi.DiaryBotNexonmi.botapi.service.MessageService;
import ru.nexonmi.DiaryBotNexonmi.data.repository.DataRepository;
import ru.nexonmi.DiaryBotNexonmi.domain.entity.DayEntity;
import ru.nexonmi.DiaryBotNexonmi.domain.entity.UserEntity;

public class ShowAllTimetableHandler extends InputCallbackHandler implements GetUserInterface {

    public ShowAllTimetableHandler(MessageService messageService, DataRepository repository) {
        super(messageService, repository);
    }

    @Override
    protected BotApiMethod<?> handleCallback(CallbackQuery callback) {
        try {
            UserEntity user = get(callback.getMessage().getChatId());
            StringBuilder ansStrBuilder = new StringBuilder();
            ansStrBuilder.append(messageService.getSourceText(CallbackEnum.SHOW_ALL_TIMETABLE.replayCode));
            for (DayEntity day : user.getDiary().getDays()) {
                ansStrBuilder.append(messageService.getRussianStringDay(day.getDayOfWeek().getDayNum()))
                        .append(":\n");
                for (int lessonId : day.getLessonIDs())
                    ansStrBuilder.append(user.getDiary().getUserLessons().get(lessonId).getName()).append("\n");

                ansStrBuilder.append("\n");
            }

            return messageService.getEditMessage(
                    callback.getMessage().getChatId(),
                    callback.getMessage().getMessageId(),
                    ansStrBuilder.toString(),
                    null
            );
        } catch (Exception e) {
            e.printStackTrace();
            return messageService.getReplyMessage(callback.getMessage().getChatId(), "replay.some_error");
        }
    }

    @Override
    public UserEntity get(long chat_id) {
        return repository.get(chat_id);
    }
}
