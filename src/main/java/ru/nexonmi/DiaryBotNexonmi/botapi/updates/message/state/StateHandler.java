package ru.nexonmi.DiaryBotNexonmi.botapi.updates.message.state;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.nexonmi.DiaryBotNexonmi.botapi.service.MessageService;
import ru.nexonmi.DiaryBotNexonmi.data.repository.DataRepository;

import java.util.HashMap;
import java.util.Map;

@Service
public class StateHandler {

    private MessageService messageService;
    private DataRepository repository;
    private final Map<StateEnum, InputStateHandler> map;

    public StateHandler(MessageService messageService, DataRepository repository) {
        this.messageService = messageService;
        this.repository = repository;
        this.map = new HashMap<>();
        initMap();
    }

    private void initMap() {
        map.put(StateEnum.HOMEWORK_ADD, new HomeworkAddHandler(messageService, repository));
    }


    public BotApiMethod<?> handleState(Message message, ru.nexonmi.DiaryBotNexonmi.domain.entity.StateEnum state) {
        for (StateEnum ist : map.keySet()) {
            if (ist.state == state)
                return map.get(ist).handleState(message);
        }
        return messageService.getReplyMessage(message.getChatId(),
                "HI from state -|:8" + message.getText());
    }
}
