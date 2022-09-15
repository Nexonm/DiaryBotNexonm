package ru.nexonmi.DiaryBotNexonmi;

import org.junit.jupiter.api.Test;
import ru.nexonmi.DiaryBotNexonmi.data.mapper.UserToJSONMapper;
import ru.nexonmi.DiaryBotNexonmi.domain.entity.UserEntity;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class UserGettingTest {

    @Test
    public void testGetting() {
        long chat_id = 1261167384;
        UserEntity user = get(chat_id);
        System.out.println(user.toString());
    }

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

        UserEntity user = new UserEntity(chat_id);
        if (!"null".equals(buf)) {
            user = UserToJSONMapper.JSONToUser(buf);
        }

        System.out.println("TEXT FROM FILE: " + buf);
        return user;
    }

    private File createFileObject(long id) {
        return new File(String.format("%s\\%s%s.txt",
                ".\\userdata", "tguser", String.valueOf(id)));
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
