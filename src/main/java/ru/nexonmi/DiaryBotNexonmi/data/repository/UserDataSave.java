package ru.nexonmi.DiaryBotNexonmi.data.repository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.nexonmi.DiaryBotNexonmi.data.mapper.UserToJSONMapper;
import ru.nexonmi.DiaryBotNexonmi.domain.entity.UserEntity;

import java.io.*;
import java.nio.charset.StandardCharsets;

@Service
public class UserDataSave {

    @Value("${storage.location}")
    private String ROOT_LOCATION;
    @Value("${storage.prefix}")
    private String STORE_PREFIX;

    protected void save(UserEntity user) {
//        new Thread(() -> {
        File file = createFileObject(user.getChatID_tg());
        while (!checkFile(file)) ;

        try (PrintWriter writer = new PrintWriter(file.getPath(), StandardCharsets.UTF_8)) {
            writer.println(getStringFromUser(user));
        } catch (IOException ignored) {
        }
//        }).start();
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

    private String getStringFromUser(UserEntity user) {
        System.out.println("User to save in UDS: " + UserToJSONMapper.userToJSON(user));
        return UserToJSONMapper.userToJSON(user);
    }
}
