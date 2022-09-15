package ru.nexonmi.DiaryBotNexonmi.botapi.updates.callback;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.nexonmi.DiaryBotNexonmi.botapi.service.MessageService;
import ru.nexonmi.DiaryBotNexonmi.botapi.service.MyInlineKeyboardButton;
import ru.nexonmi.DiaryBotNexonmi.data.repository.DataRepository;

abstract class InputCallbackHandler {
    protected MessageService messageService;
    protected DataRepository repository;

    public InputCallbackHandler(MessageService messageService, DataRepository repository) {
        this.messageService = messageService;
        this.repository = repository;
    }

    protected abstract BotApiMethod<?> handleCallback(Update update);
}
