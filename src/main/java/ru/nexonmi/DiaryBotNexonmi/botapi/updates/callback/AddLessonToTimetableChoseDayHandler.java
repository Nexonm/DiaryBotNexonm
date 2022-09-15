package ru.nexonmi.DiaryBotNexonmi.botapi.updates.callback;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.nexonmi.DiaryBotNexonmi.botapi.service.MessageService;
import ru.nexonmi.DiaryBotNexonmi.botapi.service.MyInlineKeyboardButton;
import ru.nexonmi.DiaryBotNexonmi.data.repository.DataRepository;

class AddLessonToTimetableChoseDayHandler extends InputCallbackHandler {

    public AddLessonToTimetableChoseDayHandler(MessageService messageService, DataRepository repository) {
        super(messageService, repository);
    }

    @Override
    protected BotApiMethod<?> handleCallback(Update update) {
        EditMessageText edMes = new EditMessageText();
        edMes.setChatId(update.getCallbackQuery().getMessage().getChatId());
        edMes.setMessageId(update.getCallbackQuery().getMessage().getMessageId());
        edMes.setText(messageService.getSourceText("replay.callback.add_lesson_to_timetable_chose_day"));
        edMes.setReplyMarkup(messageService.getReplayKeyboardInMessage(makeKeyboard()));

        return edMes;
    }

    private MyInlineKeyboardButton[][] makeKeyboard() {
        return new MyInlineKeyboardButton[][]{
                {
                        new MyInlineKeyboardButton(messageService.getRussianStringDay(1),
                                messageService.getSourceText("command.callback.add_lesson_to_day_chose_lesson")
                                + AddLessonToDayChoseLessonHandler.packDayData(1)),
                        new MyInlineKeyboardButton(messageService.getRussianStringDay(2),
                                messageService.getSourceText("command.callback.add_lesson_to_day_chose_lesson")
                                + AddLessonToDayChoseLessonHandler.packDayData(2)),
                        new MyInlineKeyboardButton(messageService.getRussianStringDay(3),
                                messageService.getSourceText("command.callback.add_lesson_to_day_chose_lesson")
                                + AddLessonToDayChoseLessonHandler.packDayData(3))
                },
                {
                        new MyInlineKeyboardButton(messageService.getRussianStringDay(4),
                                messageService.getSourceText("command.callback.add_lesson_to_day_chose_lesson")
                                + AddLessonToDayChoseLessonHandler.packDayData(4)),
                        new MyInlineKeyboardButton(messageService.getRussianStringDay(5),
                                messageService.getSourceText("command.callback.add_lesson_to_day_chose_lesson")
                                + AddLessonToDayChoseLessonHandler.packDayData(5)),
                        new MyInlineKeyboardButton(messageService.getRussianStringDay(6),
                                messageService.getSourceText("command.callback.add_lesson_to_day_chose_lesson")
                                + AddLessonToDayChoseLessonHandler.packDayData(6)),
                        new MyInlineKeyboardButton(messageService.getRussianStringDay(7),
                                messageService.getSourceText("command.callback.add_lesson_to_day_chose_lesson")
                                + AddLessonToDayChoseLessonHandler.packDayData(7)),
                }
        };
    }
}
