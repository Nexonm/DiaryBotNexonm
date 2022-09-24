package ru.nexonmi.DiaryBotNexonmi.botapi;

import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.starter.SpringWebhookBot;
import ru.nexonmi.DiaryBotNexonmi.botapi.updates.UpdatesFacade;
import ru.nexonmi.DiaryBotNexonmi.botapi.updates.callback.CallbackHandler;
import ru.nexonmi.DiaryBotNexonmi.botapi.updates.message.MessageHandler;

public class DiaryTelegramBot extends SpringWebhookBot {
    private String webhookPath;
    private String botUserName;
    private String botToken;

    private UpdatesFacade updatesFacade;


    public DiaryTelegramBot(SetWebhook setWebhook, UpdatesFacade updatesFacade) {
        super(setWebhook);
        this.updatesFacade = updatesFacade;
    }

    public DiaryTelegramBot(DefaultBotOptions options, SetWebhook setWebhook) {
        super(options, setWebhook);
    }


    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {

        return updatesFacade.handleUpdate(update);
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
