package ru.nexonmi.DiaryBotNexonmi.botapi.controller;

import ru.nexonmi.DiaryBotNexonmi.botapi.DiaryTelegramBot;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.nexonmi.DiaryBotNexonmi.data.repository.DataRepository;


@RestController
public class WebHookBotController {

    private final DiaryTelegramBot diaryTelegramBot;

    public WebHookBotController(DiaryTelegramBot diaryTelegramBot, DataRepository repository) {
        this.diaryTelegramBot = diaryTelegramBot;
    }

    @PostMapping("/")
    public BotApiMethod<?> onUpdateReceived(@RequestBody Update update) {
        return diaryTelegramBot.onWebhookUpdateReceived(update);
    }


    @GetMapping
    public ResponseEntity get() {
        return ResponseEntity.ok("The bot is working");
    }

}
