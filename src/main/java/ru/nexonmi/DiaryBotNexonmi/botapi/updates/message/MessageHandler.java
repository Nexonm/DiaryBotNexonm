package ru.nexonmi.DiaryBotNexonmi.botapi.updates.message;

import ru.nexonmi.DiaryBotNexonmi.botapi.service.MessageService;
import org.springframework.stereotype.Service;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.nexonmi.DiaryBotNexonmi.data.repository.DataRepository;


import java.util.HashMap;


@Service
public class MessageHandler {

    protected MessageService messageService;
    protected DataRepository repository;
    private final HashMap<String, InputMessageHandler> messageMap;

    public MessageHandler(MessageService messageService, DataRepository repository) {
        this.messageService = messageService;
        this.repository = repository;
        messageMap = new HashMap<>();
        initMap();
    }

    private void initMap() {
        messageMap.put(messageService.getSourceText(MessageEnum.START.commandCode), new StartHandler(messageService, repository));
        messageMap.put(messageService.getSourceText(MessageEnum.REGISTER_NEW_LESSON.commandCode),
                new RegisterNewLessonHandler(messageService, repository));
        messageMap.put(messageService.getSourceText(MessageEnum.TIMETABLE_EDIT.commandCode),
                new TimetableEditHandler(messageService, repository));
        messageMap.put(messageService.getSourceText(MessageEnum.TIMETABLE_ALL.commandCode),
                new TimetableAllHandler(messageService, repository));
    }


    public SendMessage handleMessage(Message message) {
        System.out.println("Start checking the message code in for-loop{");
        for (String command : messageMap.keySet()) {
            System.out.printf("The command: %s, the message.text: %s, the result: %b\n",
                    command, message.getText(), message.getText().startsWith(command));
            if (message.getText().startsWith(command))
                return messageMap.get(command).handleMessage(message);
        }
        System.out.println("Finish checking the message code in for-loop}");

        return new SendMessage(String.valueOf(message.getChatId()), "HI HERE: " + message.getText());
    }

}
