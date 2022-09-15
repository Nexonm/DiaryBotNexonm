package ru.nexonmi.DiaryBotNexonmi.botapi.service;

public class MyInlineKeyboardButton {
    public String title;
    public String callbackData;

    public MyInlineKeyboardButton(String title, String callbackData) {
        this.title = title;
        this.callbackData = callbackData;
    }
}

