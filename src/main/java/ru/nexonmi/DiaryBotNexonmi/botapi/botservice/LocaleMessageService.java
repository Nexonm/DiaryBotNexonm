package ru.nexonmi.DiaryBotNexonmi.botapi.botservice;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class LocaleMessageService {
    private final Locale locale;
    private final MessageSource messageSource;

    public LocaleMessageService(@Value("${localeTag}") String locale, MessageSource messageSource) {
        this.messageSource = messageSource;
        this.locale = Locale.forLanguageTag(locale);

    }

    public String getMessage(String messageCode){
        return messageSource.getMessage(messageCode, null, locale);
    }

    public String getMessage(String messageCode, Object[] args){
        return messageSource.getMessage(messageCode, args, locale);
    }
}
