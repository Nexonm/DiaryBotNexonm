package ru.nexonmi.DiaryBotNexonmi.data.usecases;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.nexonmi.DiaryBotNexonmi.data.answer.GetListOfFilesAnswer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
class GetListOfFilesUC {

    @Value("${storage.location}")
    private String ROOT_LOCATION;
    @Value("${storage.accessKeyToListOfFiles}")
    private String ACCESS_KEY;


    protected GetListOfFilesAnswer getListOfFiles(String accessKey) {
        if (!ACCESS_KEY.equals(accessKey)) return new GetListOfFilesAnswer(false);
        GetListOfFilesAnswer answer = new GetListOfFilesAnswer(true);
        LinkedList<File> files = new LinkedList<>(getAllFilesInStorage());
        answer.setNumOfFoundFiles(files.size());
        String[] names = new String[files.size()];
        int numOfGroupFiles = 0;
        int numOfUserFiles = 0;
        for (File file : files) {
            if (file.getName().contains("-"))
                numOfGroupFiles++;
            else
                numOfUserFiles++;
            names[numOfGroupFiles + numOfUserFiles - 1] = file.getName();
        }
        answer.setNumOfUserFiles(numOfUserFiles);
        answer.setNumOfGroupFiles(numOfGroupFiles);
        answer.setFileNames(names);
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
