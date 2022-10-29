package ru.nexonmi.DiaryBotNexonmi.botapi.updates.message.state;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.nexonmi.DiaryBotNexonmi.botapi.service.MessageService;
import ru.nexonmi.DiaryBotNexonmi.data.repository.DataRepository;

abstract class InputStateHandler {

    MessageService messageService;
    DataRepository repository;

    public InputStateHandler(MessageService messageService, DataRepository repository) {
        this.messageService = messageService;
        this.repository = repository;
    }

    protected abstract BotApiMethod<?> handleState(Message message);
}
