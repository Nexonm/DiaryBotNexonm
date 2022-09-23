package ru.nexonmi.DiaryBotNexonmi.botapi.updates.callback;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.nexonmi.DiaryBotNexonmi.botapi.service.MessageService;
import ru.nexonmi.DiaryBotNexonmi.botapi.updates.service.GetUserInterface;
import ru.nexonmi.DiaryBotNexonmi.botapi.updates.service.MyInlineKeyboardButton;
import ru.nexonmi.DiaryBotNexonmi.data.repository.DataRepository;
import ru.nexonmi.DiaryBotNexonmi.domain.entity.UserEntity;

import java.time.DayOfWeek;

public class DeleteLessonChooseDayHandler extends InputCallbackHandler implements GetUserInterface {

    public DeleteLessonChooseDayHandler(MessageService messageService, DataRepository repository) {
        super(messageService, repository);
    }

    @Override
    protected BotApiMethod<?> handleCallback(Update update) {
        EditMessageText edMes = new EditMessageText();
        edMes.setChatId(update.getCallbackQuery().getMessage().getChatId());
        edMes.setMessageId(update.getCallbackQuery().getMessage().getMessageId());
        edMes.setText(messageService.getSourceText("replay.callback.delete_lesson_chose_day"));
        edMes.setReplyMarkup(messageService.getReplayKeyboardInMessage(makeKeyboard()));

        return edMes;
    }

    private MyInlineKeyboardButton[][] makeKeyboard() {
        return new MyInlineKeyboardButton[][]{
                {
                        new MyInlineKeyboardButton(messageService.getRussianStringDay(DayOfWeek.MONDAY.getValue()),
                                messageService.getSourceText("command.callback.delete_lesson_choose_lesson")
//                                        + AddLessonToDayChoseLessonHandler.packDayData(1)
                        ),
                        new MyInlineKeyboardButton(messageService.getRussianStringDay(DayOfWeek.TUESDAY.getValue()),
                                messageService.getSourceText("command.callback.delete_lesson_choose_lesson")
//                                        + AddLessonToDayChoseLessonHandler.packDayData(2)
                        ),
                        new MyInlineKeyboardButton(messageService.getRussianStringDay(DayOfWeek.WEDNESDAY.getValue()),
                                messageService.getSourceText("command.callback.delete_lesson_choose_lesson")
//                                        + AddLessonToDayChoseLessonHandler.packDayData(3)
                        )
                },
                {
                        new MyInlineKeyboardButton(messageService.getRussianStringDay(DayOfWeek.THURSDAY.getValue()),
                                messageService.getSourceText("command.callback.delete_lesson_choose_lesson")
//                                        + AddLessonToDayChoseLessonHandler.packDayData(4)
                        ),
                        new MyInlineKeyboardButton(messageService.getRussianStringDay(DayOfWeek.FRIDAY.getValue()),
                                messageService.getSourceText("command.callback.delete_lesson_choose_lesson")
//                                        + AddLessonToDayChoseLessonHandler.packDayData(5)
                        ),
                        new MyInlineKeyboardButton(messageService.getRussianStringDay(DayOfWeek.SATURDAY.getValue()),
                                messageService.getSourceText("command.callback.delete_lesson_choose_lesson")
//                                        + AddLessonToDayChoseLessonHandler.packDayData(6)
                        ),
                        new MyInlineKeyboardButton(messageService.getRussianStringDay(DayOfWeek.FRIDAY.getValue()),
                                messageService.getSourceText("command.callback.delete_lesson_choose_lesson")
//                                        + AddLessonToDayChoseLessonHandler.packDayData(7)
                        ),
                }
        };
    }


    @Override
    public UserEntity get(long chat_id) {
        return repository.get(chat_id);
    }
}
