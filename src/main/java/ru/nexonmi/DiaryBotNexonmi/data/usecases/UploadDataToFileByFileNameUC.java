package ru.nexonmi.DiaryBotNexonmi.data.usecases;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.nexonmi.DiaryBotNexonmi.data.answer.UploadDataToFileByFileNameAnswer;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

@Service
public class UploadDataToFileByFileNameUC {

    @Value("${storage.location}")
    private String ROOT_LOCATION;
    @Value("${storage.accessKeyToUploadDataToFile}")
    private String ACCESS_KEY;

    protected UploadDataToFileByFileNameAnswer uploadDataToFileByFileName(String accessKey, String fileName, String fileData) {
        if (!ACCESS_KEY.equals(accessKey)) return new UploadDataToFileByFileNameAnswer(false, false, false);
        if (fileName.length()==0) return new UploadDataToFileByFileNameAnswer(true, false, false,
                "File name cannot be equal to 0! Impossible to find the file.");
        File file = createFileObject(fileName);
        if (!file.exists()) return new UploadDataToFileByFileNameAnswer(true, true, false,
                "File does not exist! Impossible to upload data to non existing file.");

        try (PrintWriter writer = new PrintWriter(file.getPath(), StandardCharsets.UTF_8)) {
            writer.println(fileData);
            return new UploadDataToFileByFileNameAnswer(true, false, true);
        } catch (IOException e) {
            e.printStackTrace();
            return new UploadDataToFileByFileNameAnswer(true, false, false, e.getMessage());
        }
    }

    private File createFileObject(String fileName) {
        return new File(String.format("%s%s",
                ROOT_LOCATION, fileName));
    }
}
