package ru.nexonmi.DiaryBotNexonmi.botapi.updates.callback;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
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
        callbackMap.put(messageService.getSourceText(CallbackEnum.ADD_LESSON_TO_TIMETABLE_CHOOSE_DAY.commandCode),
                new AddLessonToTimetableChooseDayHandler(messageService, repository));
        callbackMap.put(messageService.getSourceText(CallbackEnum.ADD_LESSON_TO_DAY_CHOOSE_LESSON.commandCode),
                new AddLessonToDayChooseLessonHandler(messageService, repository));
        callbackMap.put(messageService.getSourceText(CallbackEnum.ADD_LESSON_TO_DAY_ADD_LESSON.commandCode),
                new AddLessonToDayAddLessonHandler(messageService, repository));
        callbackMap.put(messageService.getSourceText(CallbackEnum.SHOW_ALL_TIMETABLE.commandCode),
                new ShowAllTimetableHandler(messageService, repository));
        callbackMap.put(messageService.getSourceText(CallbackEnum.TIMETABLE_EDIT.commandCode),
                new TimetableEditHandler(messageService, repository));
        callbackMap.put(messageService.getSourceText(CallbackEnum.DELETE_LESSON_CHOOSE_DAY.commandCode),
                new DeleteLessonChooseDayHandler(messageService, repository));
        callbackMap.put(messageService.getSourceText(CallbackEnum.DELETE_LESSON_CHOOSE_LESSON.commandCode),
                new DeleteLessonChooseLesson(messageService, repository));
        callbackMap.put(messageService.getSourceText(CallbackEnum.DELETE_LESSON_FROM_DAY.commandCode),
                new DeleteLessonFromDay(messageService, repository));
        callbackMap.put(messageService.getSourceText(CallbackEnum.HOMEWORK_ADD_SET_STATE.commandCode),
                new HomeworkAddSetStateHandler(messageService, repository));
    }

    public BotApiMethod<?> handleUpdate(CallbackQuery callback){
        for (String command : callbackMap.keySet()){
            System.out.printf("The command: %s, the message.text: %s, the result: %b\n",
                    command, callback.getData(),
                    callback.getData().startsWith(command));
            if (callback.getData().startsWith(command))
                return callbackMap.get(command).handleCallback(callback);
        }
        System.out.println("Finish checking the message code in for-loop}");
        return new SendMessage(String.valueOf(callback.getMessage().getChatId()),
                "HI FROM CALLBACK HANDLER " + callback.getData());
    }
}
