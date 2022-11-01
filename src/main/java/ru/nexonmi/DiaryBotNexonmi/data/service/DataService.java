package ru.nexonmi.DiaryBotNexonmi.data.service;

import com.google.gson.Gson;
import org.springframework.stereotype.Service;
import ru.nexonmi.DiaryBotNexonmi.data.answer.DeleteAllDataAnswer;
import ru.nexonmi.DiaryBotNexonmi.data.answer.GetFileDataByFileNameAnswer;
import ru.nexonmi.DiaryBotNexonmi.data.answer.GetListOfFilesAnswer;
import ru.nexonmi.DiaryBotNexonmi.data.repository.DataRepository;

@Service
public class DataService {

    private final DataRepository repository;

    public DataService(DataRepository repository) {
        this.repository = repository;
    }

    public String deleteAllData(String deleteKey) {
        return (new Gson()).toJson(repository.deleteAllData(deleteKey), DeleteAllDataAnswer.class);
    }

    public String getListOfFiles(String accessKey){
        return (new Gson()).toJson(repository.getListOfFiles(accessKey), GetListOfFilesAnswer.class);
    }

    public String getFileDataByFileName(String accessKey, String filename){
        return (new Gson()).toJson(repository.getFileDataByFileName(accessKey, filename), GetFileDataByFileNameAnswer.class);
    }
}
