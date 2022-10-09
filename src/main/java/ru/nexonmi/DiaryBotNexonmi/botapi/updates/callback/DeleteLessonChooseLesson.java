package ru.nexonmi.DiaryBotNexonmi.botapi.updates.callback;

import com.google.gson.Gson;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.nexonmi.DiaryBotNexonmi.botapi.service.MessageService;
import ru.nexonmi.DiaryBotNexonmi.botapi.updates.service.GetUserInterface;
import ru.nexonmi.DiaryBotNexonmi.botapi.updates.service.MyInlineKeyboardButton;
import ru.nexonmi.DiaryBotNexonmi.data.mapper.UserToJSONMapper;
import ru.nexonmi.DiaryBotNexonmi.data.repository.DataRepository;
import ru.nexonmi.DiaryBotNexonmi.domain.entity.DiaryEntity;
import ru.nexonmi.DiaryBotNexonmi.domain.entity.LessonEntity;
import ru.nexonmi.DiaryBotNexonmi.domain.entity.UserEntity;

import java.util.LinkedList;

public class DeleteLessonChooseLesson extends InputCallbackHandler implements GetUserInterface {

    private final static String DAY_STARTS = "Day:";

    public DeleteLessonChooseLesson(MessageService messageService, DataRepository repository) {
        super(messageService, repository);
    }

    @Override
    protected BotApiMethod<?> handleCallback(CallbackQuery callback) {
        long chat_id = callback.getMessage().getChatId();
        int day = unpackDayData(callback.getData());
        try {
            UserEntity user = get(chat_id);

            StringBuilder ansStrBuilder = new StringBuilder();
            ansStrBuilder.append("Расписание на ")
                    .append(messageService.getRussianStringDay(day))
                    .append(":\n");
            boolean hasLessons = user.getDiary().getDays()[day - 1].getLessonIDs().size() > 0;
            if (hasLessons) {
                for (int lessonId : user.getDiary().getDays()[day - 1].getLessonIDs())
                    ansStrBuilder.append(user.getDiary().getUserLessons().get(lessonId).getName()).append("\n");
                ansStrBuilder.append("\n\n");
                ansStrBuilder.append("Выберите урок, который хотите удалить");
            } else {
                ansStrBuilder.append("\n\n");
                ansStrBuilder.append("Ой, в этот день видимо отдыхаем, уроков нет");
            }

            return messageService.getEditMessage(
                    chat_id,
                    callback.getMessage().getMessageId(),
                    ansStrBuilder.toString(),
                    hasLessons ?
                            messageService.getReplayKeyboardInMessage(makeKeyboard(
                                    user.getDiary(), day))
                            : null
            );
//            return messageService.getReplyMessage(chat_id, "replay.some_error");
        } catch (Exception e) {
            e.printStackTrace();
            return messageService.getReplyMessage(chat_id, "replay.some_error");
        }

    }

    private MyInlineKeyboardButton[][] makeKeyboard(DiaryEntity diary, int day) {
        MyInlineKeyboardButton[][] arr = new MyInlineKeyboardButton[diary.getDays()[day - 1].getLessonIDs().size()][1];
        for (int i = 0; i < arr.length; i++) {
            arr[i][0] = new MyInlineKeyboardButton(
                    diary.getUserLessons().get(
                            diary.getDays()[day - 1].getLessonIDs().get(i)
                    ).getName(),
                    messageService.getSourceText(CallbackButtonEnum.DELETE_LESSON_FROM_DAY.callbackAction.commandCode) +
                            DeleteLessonFromDay.packLessonToDeleteData(day, i + 1));
        }
        //TODO add "cancel" button (create new handler class that will edit message
        return arr;
    }

    @Override
    public UserEntity get(long chat_id) {
        return repository.get(chat_id);
    }

    private int unpackDayData(String text) {
        //check if there is no data -> return 1
        if (!text.contains(DAY_STARTS)) return 1;

        int index = text.lastIndexOf(DAY_STARTS) + DAY_STARTS.length();
        try {
            return Integer.parseInt(text.substring(index, index + 1));
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return 1;
        }
    }

    static String packDayData(int day) {
        return DAY_STARTS + day;
    }
}
