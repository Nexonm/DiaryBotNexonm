package ru.nexonmi.DiaryBotNexonmi.botapi.controller;

import ru.nexonmi.DiaryBotNexonmi.botapi.DiaryTelegramBot;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.nexonmi.DiaryBotNexonmi.data.repository.AnswerDeleteAllData;
import ru.nexonmi.DiaryBotNexonmi.data.repository.DataRepository;


@RestController
public class WebHookController {

    private final DiaryTelegramBot diaryTelegramBot;
    private final DataRepository repository;

    public WebHookController(DiaryTelegramBot diaryTelegramBot, DataRepository repository) {
        this.diaryTelegramBot = diaryTelegramBot;
        this.repository = repository;
    }

    @PostMapping("/")
    public BotApiMethod<?> onUpdateReceived(@RequestBody Update update) {
        return diaryTelegramBot.onWebhookUpdateReceived(update);
    }


    @GetMapping
    public ResponseEntity get() {
        return ResponseEntity.ok("The bot is working");
    }

    @PostMapping("/deletealldata/bykey/*****")
    public ResponseEntity deleteAllData(
            @RequestParam(name = "deleteKey") String key
    ) {
        try {
            return ResponseEntity.ok(
                    repository.deleteAllData(key).toString()
            );
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }
}
