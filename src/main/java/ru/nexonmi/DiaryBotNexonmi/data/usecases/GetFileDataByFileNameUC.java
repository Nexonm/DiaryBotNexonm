package ru.nexonmi.DiaryBotNexonmi.data.usecases;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.nexonmi.DiaryBotNexonmi.data.answer.GetFileDataByFileNameAnswer;

import java.io.*;
import java.nio.charset.StandardCharsets;

@Service
class GetFileDataByFileNameUC {

    @Value("${storage.location}")
    private String ROOT_LOCATION;
    @Value("${storage.accessKeyToGetFileData}")
    private String ACCESS_KEY;

    protected GetFileDataByFileNameAnswer getFileDataByFileName(String accessKey, String fileName) {
        if (!ACCESS_KEY.equals(accessKey)) return new GetFileDataByFileNameAnswer(false, false);

        File file = createFileObject(fileName);
        if(!file.exists()) return new GetFileDataByFileNameAnswer(true, false);
        String fileData = getFileData(fileName);
        GetFileDataByFileNameAnswer answer = new GetFileDataByFileNameAnswer(true, true);
        answer.setLengthOfFile(fileData.length());
        answer.setData(fileData);

        return answer;
    }

    private String getFileData(String fileName) {
        File file = createFileObject(fileName);
        String buf = "";

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(file.getPath()), StandardCharsets.UTF_8))
        ) {
            buf = reader.readLine();
        } catch (IOException ignored) {}
        if(buf == null) buf = "";

        return buf;
    }

    private File createFileObject(String fileName) {
        return new File(String.format("%s%s",
                ROOT_LOCATION, fileName));
    }
}
