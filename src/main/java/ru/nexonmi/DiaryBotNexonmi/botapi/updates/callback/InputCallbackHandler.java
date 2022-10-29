package ru.nexonmi.DiaryBotNexonmi.botapi.updates.callback;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import ru.nexonmi.DiaryBotNexonmi.botapi.botservice.MessageService;
import ru.nexonmi.DiaryBotNexonmi.data.repository.DataRepository;

abstract class InputCallbackHandler {
    protected MessageService messageService;
    protected DataRepository repository;

    public InputCallbackHandler(MessageService messageService, DataRepository repository) {
        this.messageService = messageService;
        this.repository = repository;
    }

    protected abstract BotApiMethod<?> handleCallback(CallbackQuery callback);
}
