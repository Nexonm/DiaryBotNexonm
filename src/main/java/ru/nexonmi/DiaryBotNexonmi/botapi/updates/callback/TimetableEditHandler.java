package ru.nexonmi.DiaryBotNexonmi.botapi.updates.callback;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.nexonmi.DiaryBotNexonmi.botapi.service.GetUserInterface;
import ru.nexonmi.DiaryBotNexonmi.botapi.service.MessageService;
import ru.nexonmi.DiaryBotNexonmi.botapi.service.MyInlineKeyboardButton;
import ru.nexonmi.DiaryBotNexonmi.data.repository.DataRepository;
import ru.nexonmi.DiaryBotNexonmi.domain.entity.DayEntity;
import ru.nexonmi.DiaryBotNexonmi.domain.entity.UserEntity;

public class TimetableEditHandler extends InputCallbackHandler implements GetUserInterface {

    public TimetableEditHandler(MessageService messageService, DataRepository repository) {
        super(messageService, repository);
    }

    @Override
    protected BotApiMethod<?> handleCallback(Update update) {
        try{
            UserEntity user = get(update.getCallbackQuery().getMessage().getChatId());
            StringBuilder ansStrBuilder = new StringBuilder();
            ansStrBuilder.append(messageService.getSourceText(CallbackEnum.TIMETABLE_EDIT.replayCode));
            for (DayEntity day : user.getDiary().getDays()) {
                ansStrBuilder.append(messageService.getRussianStringDay(day.getDayOfWeek().getDayNum()))
                        .append(":\n");
                for (int lessonId : day.getLessonIDs())
                    ansStrBuilder.append(user.getDiary().getUserLessons().get(lessonId).getName()).append("\n");

                ansStrBuilder.append("\n");
            }

            return messageService.getEditMessage(
                    update.getCallbackQuery().getMessage().getChatId(),
                    update.getCallbackQuery().getMessage().getMessageId(),
                    ansStrBuilder.toString(),
                    messageService.getReplayKeyboardInMessage(makeKeyboard())
            );

        }catch (Exception e){
            return messageService.getReplyMessage(update.getCallbackQuery().getMessage().getChatId(), "replay.some_error");
        }
    }

    private MyInlineKeyboardButton[][] makeKeyboard() {
        return new MyInlineKeyboardButton[][]{
                {
                        new MyInlineKeyboardButton(messageService.getSourceText("btn.add_lesson_to_timetable.title"),
                                messageService.getSourceText("command.callback.add_lesson_to_timetable_chose_day"))
                },
                {
                        new MyInlineKeyboardButton("Удалить урок из расписания", "/delete_lesson_from_timetable")
                }
        };
    }


    @Override
    public UserEntity get(long chat_id) {
        return repository.get(chat_id);
    }
}
