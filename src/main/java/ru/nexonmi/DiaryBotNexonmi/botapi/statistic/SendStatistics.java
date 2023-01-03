package ru.nexonmi.DiaryBotNexonmi.botapi.statistic;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.nexonmi.DiaryBotNexonmi.botapi.DiaryTelegramBot;
import ru.nexonmi.DiaryBotNexonmi.botapi.botconfig.ApplicationContextProvider;

class SendStatistics {

    private final StatisticPOJO statisticPOJO;
    private final long sendId;

    protected SendStatistics(StatisticPOJO statisticPOJO, long sendId) {
        this.statisticPOJO = statisticPOJO;
        this.sendId = sendId;
    }

    protected void sendStatistics() {
        DiaryTelegramBot telegramBot = ApplicationContextProvider.getApplicationContext().getBean(DiaryTelegramBot.class);
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(sendId);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Запросов: " + statisticPOJO.requestsCount + "\n");
        stringBuilder.append("Уникальных ID: " + statisticPOJO.uniqueIdsCount + "\n");
        stringBuilder.append("Пользователей: " + statisticPOJO.uniqueUsersIdCount + "\n");
        stringBuilder.append("Групп: " + statisticPOJO.uniqueGroupIdCount);
        sendMessage.setText(stringBuilder.toString());
        try {
            System.out.println(sendId);
            telegramBot.execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
