package ru.nexonmi.DiaryBotNexonmi.botapi.updates.callback;

import com.google.gson.Gson;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.nexonmi.DiaryBotNexonmi.botapi.updates.service.GetUserInterface;
import ru.nexonmi.DiaryBotNexonmi.botapi.service.MessageService;
import ru.nexonmi.DiaryBotNexonmi.botapi.updates.service.MyInlineKeyboardButton;
import ru.nexonmi.DiaryBotNexonmi.data.mapper.UserToJSONMapper;
import ru.nexonmi.DiaryBotNexonmi.data.repository.DataRepository;
import ru.nexonmi.DiaryBotNexonmi.domain.entity.LessonEntity;
import ru.nexonmi.DiaryBotNexonmi.domain.entity.UserEntity;

class AddLessonToDayChooseLessonHandler extends InputCallbackHandler implements GetUserInterface {

    private final static String DAY_STARTS = "Day:";

    public AddLessonToDayChooseLessonHandler(MessageService messageService, DataRepository repository) {
        super(messageService, repository);
    }

    @Override
    protected BotApiMethod<?> handleCallback(Update update) {
        long chat_id = update.getCallbackQuery().getMessage().getChatId();
        int day = unpackDayData(update.getCallbackQuery().getData());
        try {
            UserEntity user = get(chat_id);
            System.out.println("user is: "+ UserToJSONMapper.userToJSON(user));

            StringBuilder ansStrBuilder = new StringBuilder();
            ansStrBuilder.append("Расписание на ")
                    .append(messageService.getRussianStringDay(day))
                    .append(":\n");
            for (int lessonId : user.getDiary().getDays()[day - 1].getLessonIDs())
                ansStrBuilder.append(user.getDiary().getUserLessons().get(lessonId).getName()).append("\n");


            ansStrBuilder.append("\n\n");
            ansStrBuilder.append("Выберите урок, который хотите добавить");

//            for (LessonEntity lesson : user.getDiary().getUserLessons()){
//                System.out.println("Lesson is: " + lesson.getName());
//            }

            EditMessageText edMes =  messageService.getEditMessage(
                    chat_id,
                    update.getCallbackQuery().getMessage().getMessageId(),
                    ansStrBuilder.toString(),
                    messageService.getReplayKeyboardInMessage(makeKeyboard(
                            user.getDiary().getUserLessonsArray(), day
                    ))
            );
            System.out.println((new Gson()).toJson(edMes, EditMessageText.class));
            return  edMes;
//            return messageService.getReplyMessage(chat_id, "replay.some_error");
        } catch (Exception e) {
            e.printStackTrace();
            return messageService.getReplyMessage(chat_id, "replay.some_error");
        }
    }

    private MyInlineKeyboardButton[][] makeKeyboard(LessonEntity[] lessons, int day) {
        MyInlineKeyboardButton[][] arr = new MyInlineKeyboardButton[lessons.length][1];
        for (int i = 0; i < arr.length; i++) {
//            System.out.println("Add lesson: "+lessons[i].getName());
            arr[i][0] = new MyInlineKeyboardButton(lessons[i].getName(),
                    messageService.getSourceText(CallbackButtonEnum.ADD_LESSON_TO_DAY_ADD_LESSON.callbackAction.commandCode) +
                            AddLessonToDayAddLessonHandler.packLessonToAddData(day, lessons[i].getID()));
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
