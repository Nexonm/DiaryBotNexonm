package ru.nexonmi.DiaryBotNexonmi.botapi.updates;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.nexonmi.DiaryBotNexonmi.botapi.updates.callback.CallbackHandler;
import ru.nexonmi.DiaryBotNexonmi.botapi.updates.message.MessageHandler;

import java.util.HashSet;

@Service
public class UpdatesFacade {

    private final MessageHandler messageHandler;
    private final CallbackHandler callbackHandler;

    private HashSet<Long> uniqueUsers;
    private long numOfRequests;


    public UpdatesFacade(MessageHandler messageHandler, CallbackHandler callbackHandler) {
        this.messageHandler = messageHandler;
        this.callbackHandler = callbackHandler;
        uniqueUsers = new HashSet<>();
        numOfRequests = 0;
    }

    public BotApiMethod<?> handleUpdate(Update update) {
        try {
            numOfRequests++;
            if (update.hasCallbackQuery()) {
                uniqueUsers.add(update.getCallbackQuery().getMessage().getChatId());
                return callbackHandler.handleUpdate(update.getCallbackQuery());
            } else if (update.hasMessage()) {
                uniqueUsers.add(update.getMessage().getChatId());
                return messageHandler.handleMessage(update.getMessage());
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }

    public HashSet<Long> getUniqueUsers() {
        return uniqueUsers;
    }

    public void setUniqueUsers() {
        this.uniqueUsers = new HashSet<>();
    }

    public long getNumOfRequests() {
        return numOfRequests;
    }

    public void setNumOfRequests(long numOfRequests) {
        if (numOfRequests == 0)
            this.numOfRequests = numOfRequests;
    }
}
