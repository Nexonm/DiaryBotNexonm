package ru.nexonmi.DiaryBotNexonmi.data.repository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.nexonmi.DiaryBotNexonmi.data.mapper.UserToJSONMapper;
import ru.nexonmi.DiaryBotNexonmi.domain.entity.LessonEntity;
import ru.nexonmi.DiaryBotNexonmi.domain.entity.UserEntity;

import java.io.*;
import java.nio.charset.StandardCharsets;

@Service
public class UserDataGet {

    @Value("${storage.location}")
    private String ROOT_LOCATION;
    @Value("${storage.prefix}")
    private String STORE_PREFIX;

    public UserEntity get(long chat_id) {
        File file = createFileObject(chat_id);
        while (!checkFile(file)) ;
        String buf = "null";

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(file.getPath()), StandardCharsets.UTF_8))
        ) {
            buf = reader.readLine();
        } catch (IOException ignored) {
        }

        UserEntity user = null;
        if (!"null".equals(buf)) {
            user = UserToJSONMapper.JSONToUser(buf);
        }
        //in case after all it is still null
        if (user == null) user = new UserEntity(chat_id);

//        System.out.println("TEXT FROM FILE: " + buf);
        return user;
    }

    private File createFileObject(long id) {
        return new File(String.format("%s%s%s",
                ROOT_LOCATION, STORE_PREFIX, String.valueOf(id)));
    }

    private boolean checkFile(File file) {
        if (!file.exists()) {
            try {
                file.createNewFile();
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }
}
