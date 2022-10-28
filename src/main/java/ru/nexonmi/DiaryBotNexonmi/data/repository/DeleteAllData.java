package ru.nexonmi.DiaryBotNexonmi.data.repository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
class DeleteAllData {

    @Value("${storage.location}")
    private String ROOT_LOCATION;
    @Value("${storage.prefix}")
    private String STORE_PREFIX;
    @Value("${storage.deleteKey}")
    private String DELETE_KEY;


    protected AnswerDeleteAllData deleteAllFiles(String deleteKey) {
        if(!DELETE_KEY.equals(deleteKey)) return new AnswerDeleteAllData(false);
        AnswerDeleteAllData answer = new AnswerDeleteAllData(true);
        LinkedList<File> files = new LinkedList<>(getAllFilesInStorage());
        answer.numOfFoundFiles = files.size();

        for (File file : files) {
            try {
                Files.delete(Paths.get(file.getPath()));
                if (file.getName().contains("-"))
                    answer.numOfGroupFiles++;
                else
                    answer.numOfUserFiles++;
                answer.numOfDeletedFiles++;
            } catch (IOException e) {
                answer.numOfExceptionFound++;
            }
        }

        return answer;
    }

    private List<File> getAllFilesInStorage() {
        try {
            List<File> filesInFolder = Files.walk(Paths.get(ROOT_LOCATION))
                    .filter(Files::isRegularFile)
                    .map(Path::toFile)
                    .collect(Collectors.toList());
            return filesInFolder;
        } catch (IOException e) {
            e.printStackTrace();
            return new LinkedList<>();
        }
    }

}
