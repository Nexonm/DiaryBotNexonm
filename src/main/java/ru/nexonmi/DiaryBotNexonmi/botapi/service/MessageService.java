package ru.nexonmi.DiaryBotNexonmi.botapi.service;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.nexonmi.DiaryBotNexonmi.botapi.updates.service.MyInlineKeyboardButton;


import javax.validation.constraints.NotNull;
import java.util.LinkedList;
import java.util.List;

@Service
public class MessageService {
    private LocaleMessageService localeMessageService;

    public MessageService(LocaleMessageService localeMessageService) {
        this.localeMessageService = localeMessageService;
    }

    public SendMessage getReplyMessage(long chatId, String replayText) {
        return new SendMessage(String.valueOf(chatId), replayText);
    }

    public SendMessage getReplyMessage(long chatId, String replyMessageCode, InlineKeyboardMarkup keyboardMarkup) {
        SendMessage sendMessage = new SendMessage(String.valueOf(chatId), localeMessageService.getMessage(replyMessageCode));
        sendMessage.setReplyMarkup(keyboardMarkup);
        return sendMessage;
    }
    public SendMessage getReplyMessage(long chatId, String replyMessageCode, MyInlineKeyboardButton[][] myButtons) {
        return  getReplyMessage(chatId, replyMessageCode, getReplayKeyboardInMessage(myButtons));
    }

    public EditMessageText getEditMessage(long chat_id, int message_id, String replayText, InlineKeyboardMarkup keyboard) {
        EditMessageText edMes = new EditMessageText();
        edMes.setChatId(chat_id);
        edMes.setMessageId(message_id);
        edMes.setText(replayText);
        if (keyboard != null)
            edMes.setReplyMarkup(keyboard);
        return edMes;
    }

    public EditMessageText getEditMessage(long chat_id, int message_id, String replayText, @NotNull MyInlineKeyboardButton[][] myButtons) {
        return getEditMessage(chat_id, message_id, replayText, getReplayKeyboardInMessage(myButtons));
    }
    public EditMessageText getEditMessage(long chat_id, int message_id, String replayText) {
        EditMessageText edMes = new EditMessageText();
        edMes.setChatId(chat_id);
        edMes.setMessageId(message_id);
        edMes.setText(replayText);
        return edMes;
    }

    /**
     * Creates inline keyboard.
     *
     * @param buttons array with MyInlineKeyboardButtons which must be in
     * @return keyboard for message
     */
    public InlineKeyboardMarkup getReplayKeyboardInMessage(MyInlineKeyboardButton[][] buttons) {
        //todo write checks if there is too much buttons
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new LinkedList<>();

        //start filling the keyboard with data
        for (MyInlineKeyboardButton[] line : buttons) {
            List<InlineKeyboardButton> lineBuf = new LinkedList<>();
            for (MyInlineKeyboardButton button : line) {
                if (button == null) continue;
                InlineKeyboardButton butBuf = new InlineKeyboardButton();
                butBuf.setText(button.title);
                butBuf.setCallbackData(button.callbackData);
                lineBuf.add(butBuf);
            }
            rows.add(lineBuf);
        }

        markup.setKeyboard(rows);
        return markup;
    }

    public String getSourceText(String replyMessageCode) {
        return localeMessageService.getMessage(replyMessageCode);
    }

    public String getRussianStringDay(int day) {
        return switch (day) {
            case 1 -> "Понедельник";
            case 2 -> "Вторник";
            case 3 -> "Среда";
            case 4 -> "Четверг";
            case 5 -> "Пятница";
            case 6 -> "Суббота";
            case 7 -> "Воскресенье";
            default -> "Неизвестный день";
        };
    }

    public String getRussianStringMonth(int monthNum){
        return switch (monthNum) {
            case 1 -> "января";
            case 2 -> "февраля";
            case 3 -> "марта";
            case 4 -> "апреля";
            case 5 -> "мая";
            case 6 -> "июня";
            case 7 -> "июля";
            case 8 -> "августа";
            case 9 -> "сентября";
            case 10 -> "октября";
            case 11 -> "ноября";
            case 12 -> "декабря";
            default -> "ошибка в получении месяца";
        };
    }


}
