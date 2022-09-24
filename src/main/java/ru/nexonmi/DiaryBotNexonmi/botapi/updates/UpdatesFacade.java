package ru.nexonmi.DiaryBotNexonmi.botapi.updates;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.nexonmi.DiaryBotNexonmi.botapi.service.MessageService;
import ru.nexonmi.DiaryBotNexonmi.botapi.updates.callback.CallbackHandler;
import ru.nexonmi.DiaryBotNexonmi.botapi.updates.message.MessageHandler;
import ru.nexonmi.DiaryBotNexonmi.data.repository.DataRepository;

@Service
public class UpdatesFacade {

    private MessageHandler messageHandler;
    private CallbackHandler callbackHandler;

    public UpdatesFacade(MessageHandler messageHandler, CallbackHandler callbackHandler) {
        this.messageHandler = messageHandler;
        this.callbackHandler = callbackHandler;
    }

    public BotApiMethod<?> handleUpdate(Update update) {
        try {
            if (update.hasCallbackQuery())
                return callbackHandler.handleUpdate(update.getCallbackQuery());
            else if(update.hasMessage()){
                return messageHandler.handleMessage(update.getMessage());
            }
        }catch (Exception e){
            return null;
        }
        return null;
    }

}
