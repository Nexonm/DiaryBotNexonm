package ru.nexonmi.DiaryBotNexonmi.botapi.botconfig;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class BotConfig {
    @Value("${telegrambot.webhookPath}")
    private String webhookPath;
    @Value("${telegrambot.botUserName}")
    private String botUserName;
    @Value("${telegrambot.botToken}")
    private String botToken;


    public String getWebHookPath() {
        return webhookPath;
    }

    public void setWebHookPath(String webHookPath) {
        this.webhookPath = webHookPath;
    }

    public String getBotUserName() {
        return botUserName;
    }

    public void setBotUserName(String botUserName) {
        this.botUserName = botUserName;
    }

    public String getBotToken() {
        return botToken;
    }

    public void setBotToken(String botToken) {
        this.botToken = botToken;
    }
}
