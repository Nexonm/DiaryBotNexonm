package ru.nexonmi.DiaryBotNexonmi.botapi.updates.callback;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
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
    protected BotApiMethod<?> handleCallback(CallbackQuery callback) {
        EditMessageText edMes = new EditMessageText();
        edMes.setChatId(callback.getMessage().getChatId());
        edMes.setMessageId(callback.getMessage().getMessageId());
        edMes.setText(messageService.getSourceText("replay.callback.delete_lesson_chose_day"));
        edMes.setReplyMarkup(messageService.getReplayKeyboardInMessage(makeKeyboard()));

        return edMes;
    }

    private MyInlineKeyboardButton[][] makeKeyboard() {
        return new MyInlineKeyboardButton[][]{
                {
                        new MyInlineKeyboardButton(messageService.getRussianStringDay(DayOfWeek.MONDAY.getValue()),
                                messageService.getSourceText(CallbackButtonEnum.DELETE_LESSON_CHOOSE_LESSON.callbackAction.commandCode)
                                        + DeleteLessonChooseLesson.packDayData(DayOfWeek.MONDAY.getValue())
                        ),
                        new MyInlineKeyboardButton(messageService.getRussianStringDay(DayOfWeek.TUESDAY.getValue()),
                                messageService.getSourceText(CallbackButtonEnum.DELETE_LESSON_CHOOSE_LESSON.callbackAction.commandCode)
                                        + DeleteLessonChooseLesson.packDayData(DayOfWeek.TUESDAY.getValue())
                        ),
                        new MyInlineKeyboardButton(messageService.getRussianStringDay(DayOfWeek.WEDNESDAY.getValue()),
                                messageService.getSourceText(CallbackButtonEnum.DELETE_LESSON_CHOOSE_LESSON.callbackAction.commandCode)
                                        + DeleteLessonChooseLesson.packDayData(DayOfWeek.WEDNESDAY.getValue())
                        )
                },
                {
                        new MyInlineKeyboardButton(messageService.getRussianStringDay(DayOfWeek.THURSDAY.getValue()),
                                messageService.getSourceText(CallbackButtonEnum.DELETE_LESSON_CHOOSE_LESSON.callbackAction.commandCode)
                                        + DeleteLessonChooseLesson.packDayData(DayOfWeek.THURSDAY.getValue())
                        ),
                        new MyInlineKeyboardButton(messageService.getRussianStringDay(DayOfWeek.FRIDAY.getValue()),
                                messageService.getSourceText(CallbackButtonEnum.DELETE_LESSON_CHOOSE_LESSON.callbackAction.commandCode)
                                        + DeleteLessonChooseLesson.packDayData(DayOfWeek.FRIDAY.getValue())
                        ),
                        new MyInlineKeyboardButton(messageService.getRussianStringDay(DayOfWeek.SATURDAY.getValue()),
                                messageService.getSourceText(CallbackButtonEnum.DELETE_LESSON_CHOOSE_LESSON.callbackAction.commandCode)
                                        + DeleteLessonChooseLesson.packDayData(DayOfWeek.SATURDAY.getValue())
                        ),
                        new MyInlineKeyboardButton(messageService.getRussianStringDay(DayOfWeek.SUNDAY.getValue()),
                                messageService.getSourceText(CallbackButtonEnum.DELETE_LESSON_CHOOSE_LESSON.callbackAction.commandCode)
                                        + DeleteLessonChooseLesson.packDayData(DayOfWeek.SUNDAY.getValue())
                        ),
                }
        };
    }


    @Override
    public UserEntity get(long chat_id) {
        return repository.get(chat_id);
    }
}
