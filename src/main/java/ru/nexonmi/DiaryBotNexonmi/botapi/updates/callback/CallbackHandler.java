package ru.nexonmi.DiaryBotNexonmi.botapi.updates.callback;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.nexonmi.DiaryBotNexonmi.botapi.service.MessageService;
import ru.nexonmi.DiaryBotNexonmi.data.repository.DataRepository;

import java.util.HashMap;

@Service
public class CallbackHandler {
    protected MessageService messageService;
    protected DataRepository repository;
    private final HashMap<String, InputCallbackHandler> callbackMap;

    public CallbackHandler(MessageService messageService, DataRepository repository) {
        this.messageService = messageService;
        this.repository = repository;
        callbackMap = new HashMap<>();
        initMap();
    }

    private void initMap(){
        callbackMap.put(messageService.getSourceText(CallbackEnum.ADD_LESSON_TO_TIMETABLE_CHOSE_DAY.commandCode),
                new AddLessonToTimetableChoseDayHandler(messageService, repository));
        callbackMap.put(messageService.getSourceText(CallbackEnum.ADD_LESSON_TO_DAY_CHOOSE_LESSON.commandCode),
                new AddLessonToDayChoseLessonHandler(messageService, repository));
        callbackMap.put(messageService.getSourceText(CallbackEnum.ADD_LESSON_TO_DAY.commandCode),
                new AddLessonToDayAddLessonHandler(messageService, repository));
        callbackMap.put(messageService.getSourceText(CallbackEnum.SHOW_ALL_TIMETABLE.commandCode),
                new ShowAllTimetableHandler(messageService, repository));
        callbackMap.put(messageService.getSourceText(CallbackEnum.TIMETABLE_EDIT.commandCode),
                new TimetableEditHandler(messageService, repository));
        callbackMap.put(messageService.getSourceText(CallbackEnum.DELETE_LESSON_CHOOSE_DAY.commandCode),
                new DeleteLessonChooseDayHandler(messageService, repository));
    }

    public BotApiMethod<?> handleUpdate(Update update){
        for (String command : callbackMap.keySet()){
            System.out.printf("The command: %s, the message.text: %s, the result: %b\n",
                    command, update.getCallbackQuery().getData(),
                    update.getCallbackQuery().getData().startsWith(command));
            if (update.getCallbackQuery().getData().startsWith(command))
                return callbackMap.get(command).handleCallback(update);
        }
        System.out.println("Finish checking the message code in for-loop}");
        return new SendMessage(String.valueOf(update.getCallbackQuery().getMessage().getChatId()),
                "HI FROM CALLBACK HANDLER " + update.getCallbackQuery().getData());
    }
}
