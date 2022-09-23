package ru.nexonmi.DiaryBotNexonmi.botapi.updates.callback;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.nexonmi.DiaryBotNexonmi.botapi.updates.service.GetUserInterface;
import ru.nexonmi.DiaryBotNexonmi.botapi.service.MessageService;
import ru.nexonmi.DiaryBotNexonmi.botapi.updates.service.MyInlineKeyboardButton;
import ru.nexonmi.DiaryBotNexonmi.botapi.updates.service.SaveUserInterface;
import ru.nexonmi.DiaryBotNexonmi.data.repository.DataRepository;
import ru.nexonmi.DiaryBotNexonmi.domain.entity.DayOfWeekEnum;
import ru.nexonmi.DiaryBotNexonmi.domain.entity.UserEntity;

class AddLessonToDayAddLessonHandler extends InputCallbackHandler implements GetUserInterface, SaveUserInterface {

    private final static String DAY_STARTS = "Day:";
    private final static String LESSON_STARTS = "LesID:";

    public AddLessonToDayAddLessonHandler(MessageService messageService, DataRepository repository) {
        super(messageService, repository);
    }

    @Override
    protected BotApiMethod<?> handleCallback(Update update) {
        long chat_id = update.getCallbackQuery().getMessage().getChatId();
        int day = unpackDayData(update.getCallbackQuery().getData());
        int lessonId = unpackLessonData(update.getCallbackQuery().getData());
        try {
            UserEntity user = get(chat_id);
            user.getDiary().addLessonToEndOfTimetable(DayOfWeekEnum.getDayByNum(day), lessonId);

            StringBuilder ansStrBuilder = new StringBuilder();
            ansStrBuilder.append("Расписание на ")
                    .append(messageService.getRussianStringDay(day))
                    .append(":\n");
            for (int id : user.getDiary().getDays()[day - 1].getLessonIDs())
                ansStrBuilder.append(user.getDiary().getUserLessons().get(id).getName()).append("\n");

            save(user);
            return messageService.getEditMessage(
                    chat_id,
                    update.getCallbackQuery().getMessage().getMessageId(),
                    ansStrBuilder.toString(),
                    messageService.getReplayKeyboardInMessage(makeKeyboard(day))
            );
        } catch (Exception e) {
            return messageService.getReplyMessage(chat_id, "replay.some_error");
        }
    }

    public MyInlineKeyboardButton[][] makeKeyboard(int day){
        //if user press this button he just chooses another one lesson
        return new MyInlineKeyboardButton[][] {{
            new MyInlineKeyboardButton(messageService.getSourceText("replay.callback.btn.add_more_lessons"),
                    messageService.getSourceText("command.callback.add_lesson_to_day_choose_lesson")+
                    AddLessonToDayChooseLessonHandler.packDayData(day))
        },{
            new MyInlineKeyboardButton(messageService.getSourceText("replay.callback.btn.choose_another_day"),
                    messageService.getSourceText("command.callback.add_lesson_to_timetable_choose_day"))
        },{
            new MyInlineKeyboardButton(messageService.getSourceText("replay.callback.btn.show_all_timetable"),
                    messageService.getSourceText("command.callback.show_all_timetable"))
        }};
    }

    @Override
    public UserEntity get(long chat_id) {
        return repository.get(chat_id);
    }

    @Override
    public void save(UserEntity user) {
        repository.save(user);
    }

    private int unpackLessonData(String text) {
        if (!text.contains(LESSON_STARTS)) return -1;

        int index = text.lastIndexOf(LESSON_STARTS) + LESSON_STARTS.length();
        try {
            return Integer.parseInt(text.substring(index));
        } catch (NumberFormatException e) {
//            e.printStackTrace();
            return 1;
        }
    }

    private int unpackDayData(String text) {
        //check if there is no data -> return 1
        if (!text.contains(DAY_STARTS)) return 1;

        int index = text.lastIndexOf(DAY_STARTS) + DAY_STARTS.length();
        try {
            return Integer.parseInt(text.substring(index, index + 1));
        } catch (NumberFormatException e) {
//            e.printStackTrace();
            return 1;
        }
    }

    static String packLessonToAddData(int day, int lessonId) {
        return String.format("%s%d%s%d", DAY_STARTS, day, LESSON_STARTS, lessonId);
        //replace char " " to "_" cause that is restricted to place " " in button callback
    }
}
