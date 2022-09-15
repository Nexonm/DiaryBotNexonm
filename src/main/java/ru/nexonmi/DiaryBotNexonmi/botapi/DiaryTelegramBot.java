package ru.nexonmi.DiaryBotNexonmi.botapi;

import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.starter.SpringWebhookBot;
import ru.nexonmi.DiaryBotNexonmi.botapi.updates.callback.CallbackHandler;
import ru.nexonmi.DiaryBotNexonmi.botapi.updates.message.MessageHandler;

public class DiaryTelegramBot extends SpringWebhookBot {
    private String webhookPath;
    private String botUserName;
    private String botToken;

    private MessageHandler messageHandler;
    private CallbackHandler callbackHandler;


    public DiaryTelegramBot(SetWebhook setWebhook, MessageHandler messageHandler, CallbackHandler callbackHandler) {
        super(setWebhook);
        this.messageHandler = messageHandler;
        this.callbackHandler = callbackHandler;
    }

    public DiaryTelegramBot(DefaultBotOptions options, SetWebhook setWebhook, MessageHandler messageHandler) {
        super(options, setWebhook);
        this.messageHandler = messageHandler;
    }


    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {

        if (update.getMessage() != null && update.getMessage().hasText()) {
            System.out.println("Some message was handled: " + update.getMessage().getText());
            return messageHandler.handleMessage(update.getMessage());
        } else if (update.hasCallbackQuery()) {
            System.out.println("Some callback was handled: " + update.getCallbackQuery().getData());
            return callbackHandler.handleUpdate(update);
        }
        //return SendMessage
        return null;
    }


    @Override
    public String getBotUsername() {
        return botUserName;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public String getBotPath() {
        return webhookPath;
    }


    public void setWebhookPath(String webhookPath) {
        this.webhookPath = webhookPath;
    }

    public void setBotUserName(String botUserName) {
        this.botUserName = botUserName;
    }

    public void setBotToken(String botToken) {
        this.botToken = botToken;
    }
}
