package ru.nexonmi.DiaryBotNexonmi.data.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.nexonmi.DiaryBotNexonmi.data.repository.DataRepository;

@RestController
public class DataController {

    private final DataRepository repository;

    public DataController(DataRepository repository) {
        this.repository = repository;
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
