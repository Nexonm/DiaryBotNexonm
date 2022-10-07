package ru.nexonmi.DiaryBotNexonmi.botapi.updates.message;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import ru.nexonmi.DiaryBotNexonmi.botapi.service.MessageService;
import org.springframework.stereotype.Service;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.nexonmi.DiaryBotNexonmi.botapi.updates.message.state.StateHandler;
import ru.nexonmi.DiaryBotNexonmi.botapi.updates.service.GetUserInterface;
import ru.nexonmi.DiaryBotNexonmi.botapi.updates.service.SaveUserInterface;
import ru.nexonmi.DiaryBotNexonmi.data.repository.DataRepository;
import ru.nexonmi.DiaryBotNexonmi.domain.entity.StateEnum;
import ru.nexonmi.DiaryBotNexonmi.domain.entity.UserEntity;


import java.util.HashMap;


@Service
public class MessageHandler implements GetUserInterface, SaveUserInterface {

    private MessageService messageService;
    private DataRepository repository;
    private StateHandler stateHandler;
    private final HashMap<String, InputMessageHandler> messageMap;

    public MessageHandler(MessageService messageService, DataRepository repository, StateHandler stateHandler) {
        this.messageService = messageService;
        this.repository = repository;
        this.stateHandler = stateHandler;
        messageMap = new HashMap<>();
        initMap();
    }

    private void initMap() {
        messageMap.put(messageService.getSourceText(MessageEnum.START.commandCode),
                new StartHandler(messageService, repository));
        messageMap.put(messageService.getSourceText(MessageEnum.REGISTER_NEW_LESSON.commandCode),
                new RegisterNewLessonHandler(messageService, repository));
        messageMap.put(messageService.getSourceText(MessageEnum.TIMETABLE_EDIT.commandCode),
                new TimetableEditHandler(messageService, repository));
        messageMap.put(messageService.getSourceText(MessageEnum.TIMETABLE_ALL.commandCode),
                new TimetableAllHandler(messageService, repository));
        messageMap.put(messageService.getSourceText(MessageEnum.HOMEWORK_ADD_CHOOSE_LESSON.commandCode),
                new HomeworkAddChooseLessonHandler(messageService, repository));
        messageMap.put(messageService.getSourceText(MessageEnum.HOMEWORK_TOMORROW.commandCode),
                new HomeworkTomorrowHandler(messageService, repository));
    }


    public BotApiMethod<?> handleMessage(Message message) {
        for (String command : messageMap.keySet()) {
            System.out.printf("The command: %s, the message.text: %s, the result: %b\n",
                    command, message.getText(), message.getText().startsWith(command));
            if (message.getText().startsWith(command)) {
                resetUserState(message.getChatId());//make user state null so after new commands previous state in no mo valid
                return messageMap.get(command).handleMessage(message);
            }
        }
        StateEnum state = checkIfUserHasState(message.getChatId());
        if (state != StateEnum.DEFAULT_STATE){
            return stateHandler.handleState(message, state);
        }

        return new SendMessage(String.valueOf(message.getChatId()), "HI HERE: " + message.getText());
    }

    private void resetUserState(long chat_id){
        try {
            UserEntity user = get(chat_id);
            user.resetState();
            save(user);
        }catch (Exception e){
            System.out.println("Ignored Exception:");
            e.printStackTrace();
        }
    }

    private StateEnum checkIfUserHasState(long chat_id){
        try {
            UserEntity user = get(chat_id);
            return user.getState();
        }catch (Exception e){
            System.out.println("Ignored Exception:");
            e.printStackTrace();
            return StateEnum.DEFAULT_STATE;
        }
    }

    @Override
    public UserEntity get(long chat_id) {
        return repository.get(chat_id);
    }

    @Override
    public void save(UserEntity user) {
        repository.save(user);
    }
}
