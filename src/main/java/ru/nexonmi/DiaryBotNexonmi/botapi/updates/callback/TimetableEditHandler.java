package ru.nexonmi.DiaryBotNexonmi.botapi.updates.callback;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.nexonmi.DiaryBotNexonmi.botapi.service.MessageService;
import ru.nexonmi.DiaryBotNexonmi.botapi.service.MyInlineKeyboardButton;
import ru.nexonmi.DiaryBotNexonmi.data.repository.DataRepository;

public class TimetableEditHandler extends InputCallbackHandler {

    public TimetableEditHandler(MessageService messageService, DataRepository repository) {
        super(messageService, repository);
    }

    @Override
    protected BotApiMethod<?> handleCallback(Update update) {
        try {

            //main idea is just to set new buttons in message
            return messageService.getEditMessage(
                    update.getCallbackQuery().getMessage().getChatId(),
                    update.getCallbackQuery().getMessage().getMessageId(),
                    update.getCallbackQuery().getMessage().getText(),
                    messageService.getReplayKeyboardInMessage(makeKeyboard())
            );

        } catch (Exception e) {
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

}
