package ru.nexonmi.DiaryBotNexonmi.data.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.nexonmi.DiaryBotNexonmi.data.repository.DataRepository;
import ru.nexonmi.DiaryBotNexonmi.data.service.DataService;

@RestController
public class DataController {

    private final DataService dataService;

    public DataController(DataService dataService) {
        this.dataService = dataService;
    }

    @PostMapping("/deletealldata/bykey/*****")
    public ResponseEntity deleteAllData(
            @RequestParam(name = "deleteKey") String key
    ) {
        try {
            return ResponseEntity.ok(
                    dataService.deleteAllData(key)
            );
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/getlistoffiles/bykey/*****")
    public ResponseEntity getListOfFiles(
            @RequestParam(name = "accessKey") String key
    ) {
        try {
            return ResponseEntity.ok(
                    dataService.getListOfFiles(key)
            );
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
