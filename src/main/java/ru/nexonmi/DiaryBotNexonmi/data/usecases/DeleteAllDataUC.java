package ru.nexonmi.DiaryBotNexonmi.data.usecases;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.nexonmi.DiaryBotNexonmi.data.answer.DeleteAllDataAnswer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
class DeleteAllDataUC {

    @Value("${storage.location}")
    private String ROOT_LOCATION;
    @Value("${storage.prefix}")
    private String STORE_PREFIX;
    @Value("${storage.deleteKey}")
    private String DELETE_KEY;


    protected DeleteAllDataAnswer deleteAllFiles(String deleteKey) {
        if(!DELETE_KEY.equals(deleteKey)) return new DeleteAllDataAnswer(false);
        DeleteAllDataAnswer answer = new DeleteAllDataAnswer(true);
        LinkedList<File> files = new LinkedList<>(getAllFilesInStorage());
//        answer.setNumOfFoundFiles(files.size());
        int numOfGroupFiles = 0;
        int numOfUserFiles = 0;
        int numOfDeletedFiles = 0;
        int numOfExceptionOccurred = 0;

        for (File file : files) {
            try {
                Files.delete(Paths.get(file.getPath()));
                if (file.getName().contains("-"))
                    numOfGroupFiles++;
                else
                    numOfUserFiles++;
                numOfDeletedFiles++;
            } catch (IOException e) {
                numOfExceptionOccurred++;
            }
        }

        answer.setNumOfGroupFiles(numOfGroupFiles);
        answer.setNumOfUserFiles(numOfUserFiles);
        answer.setNumOfDeletedFiles(numOfDeletedFiles);
        answer.setNumOfExceptionOccurred(numOfExceptionOccurred);

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
