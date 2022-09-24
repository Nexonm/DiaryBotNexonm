package ru.nexonmi.DiaryBotNexonmi.appconfig;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import ru.nexonmi.DiaryBotNexonmi.botapi.DiaryTelegramBot;
import ru.nexonmi.DiaryBotNexonmi.botapi.botconfig.BotConfig;
import ru.nexonmi.DiaryBotNexonmi.botapi.updates.UpdatesFacade;
import ru.nexonmi.DiaryBotNexonmi.botapi.updates.callback.CallbackHandler;
import ru.nexonmi.DiaryBotNexonmi.botapi.updates.message.MessageHandler;

import java.util.logging.Level;
import java.util.logging.Logger;


@Configuration
public class AppConfig {
    private static final Logger LOGGER = Logger.getLogger(AppConfig.class.getName());

    private final BotConfig botConfig;


    public AppConfig(BotConfig botConfig) {
        this.botConfig = botConfig;
    }


    @Bean
    public SetWebhook setWebhookInstance() {
        return SetWebhook.builder().url(botConfig.getWebHookPath()).build();
    }

    @Bean
    public DiaryTelegramBot diaryTelegramBot(SetWebhook setWebhook, UpdatesFacade updatesFacade) {

        LOGGER.log(Level.INFO, "BotUserName = " + botConfig.getBotUserName());
        LOGGER.log(Level.INFO, "BotToken = " + botConfig.getBotToken());
        LOGGER.log(Level.INFO, "BotWebhookPath = " + botConfig.getWebHookPath());

        DiaryTelegramBot bot = new DiaryTelegramBot(setWebhook, updatesFacade);
        bot.setBotUserName(botConfig.getBotUserName());
        bot.setBotToken(botConfig.getBotToken());
        bot.setWebhookPath(botConfig.getWebHookPath());

        return bot;
    }

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource
                = new ReloadableResourceBundleMessageSource();

        messageSource.setBasename("classpath:messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }
}
