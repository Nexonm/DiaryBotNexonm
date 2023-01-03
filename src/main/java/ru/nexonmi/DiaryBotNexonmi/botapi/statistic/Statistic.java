package ru.nexonmi.DiaryBotNexonmi.botapi.statistic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.nexonmi.DiaryBotNexonmi.botapi.DiaryTelegramBot;
import ru.nexonmi.DiaryBotNexonmi.botapi.updates.UpdatesFacade;

import java.util.HashSet;

@Component
public class Statistic {

    private final UpdatesFacade facade;
    private final DiaryTelegramBot telegramBot;

    @Value("${statistics.adminId}")
    private long ADMIN_ID;

    @Autowired
    public Statistic(UpdatesFacade facade, DiaryTelegramBot telegramBot) {
        this.facade = facade;
        this.telegramBot = telegramBot;
    }

    @Scheduled(cron = "0 0 0 * * *")
//    @Scheduled(fixedRate = 1000)
    private void statisticService() {
        HashSet<Long> ids = facade.getUniqueUsers();
        long uniqueIds = ids.size();
        facade.setUniqueUsers();
        long userIds = 0;
        long groupIds = 0;
        for (long i : ids){
            if (i<0)
                groupIds++;
            else
                userIds++;
        }
        long requests = facade.getNumOfRequests();
        facade.setNumOfRequests(0);

        SendStatistics send = new SendStatistics(new StatisticPOJO(requests, uniqueIds, userIds, groupIds), ADMIN_ID);
        send.sendStatistics();
    }
}
