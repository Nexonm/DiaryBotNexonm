package ru.nexonmi.DiaryBotNexonmi.data.usecases;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.nexonmi.DiaryBotNexonmi.data.answer.UploadFileAnswer;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

@Service
public class UploadFileUC {

    @Value("${storage.location}")
    private String ROOT_LOCATION;
    @Value("${storage.accessKeyToUploadFile}")
    private String ACCESS_KEY;

    protected UploadFileAnswer uploadFile(String accessKey, String fileName, String fileData) {
        System.out.println("sent key: "+ accessKey + ", the original key: "+ ACCESS_KEY);
        if (!ACCESS_KEY.equals(accessKey)) return new UploadFileAnswer(false, false, false);
        if (fileName.length()==0) return new UploadFileAnswer(true, true, false,
                "File name cannot be equal to 0! Impossible to create new file.");
        File file = createFileObject(fileName);
        if (file.exists()) return new UploadFileAnswer(true, true, false,
                "File already exists! Impossible to create new file.");

        try (PrintWriter writer = new PrintWriter(file.getPath(), StandardCharsets.UTF_8)) {
            writer.println(fileData);
            return new UploadFileAnswer(true, false, true);
        } catch (IOException e) {
            e.printStackTrace();
            return new UploadFileAnswer(true, false, false, e.getMessage());
        }
    }

    private File createFileObject(String fileName) {
        return new File(String.format("%s%s",
                ROOT_LOCATION, fileName));
    }
}
