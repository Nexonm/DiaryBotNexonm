package ru.nexonmi.DiaryBotNexonmi.botapi.updates.message;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.nexonmi.DiaryBotNexonmi.botapi.service.MessageService;
import ru.nexonmi.DiaryBotNexonmi.data.repository.DataRepository;

abstract class InputMessageHandler {
    protected MessageService messageService;
    protected DataRepository repository;

    public InputMessageHandler(MessageService messageService, DataRepository repository) {
        this.messageService = messageService;
        this.repository = repository;
    }

    protected abstract BotApiMethod<?> handleMessage(Message message);
}
