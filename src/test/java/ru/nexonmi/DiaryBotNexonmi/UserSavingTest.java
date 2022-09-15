package ru.nexonmi.DiaryBotNexonmi;

import org.junit.jupiter.api.Test;
import ru.nexonmi.DiaryBotNexonmi.data.mapper.UserToJSONMapper;
import ru.nexonmi.DiaryBotNexonmi.domain.entity.DayOfWeekEnum;
import ru.nexonmi.DiaryBotNexonmi.domain.entity.UserEntity;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class UserSavingTest {

    @Test
    public void testUserSaving() {
        long id = 4;
        UserEntity user = new UserEntity(id);
//        UserGettingTest get = new UserGettingTest();
//        user = get.get(id);
//        try {
//            user.getDiary().registerLesson("Литература");
//            user.getDiary().registerLesson("Физика");
//            user.getDiary().registerLesson("Physics");
//            user.getDiary().registerLesson("Химия");
//            user.getDiary().registerLesson("Русский");
//            user.getDiary().addLessonToEndOfTimetable(DayOfWeekEnum.FRIDAY,"Русский");
//            user.getDiary().addLessonToEndOfTimetable(DayOfWeekEnum.FRIDAY,"Physics");
//            user.getDiary().addLessonToEndOfTimetable(DayOfWeekEnum.TUESDAY,"Физика");
//            user.getDiary().addLessonToEndOfTimetable(DayOfWeekEnum.FRIDAY,"Химия");
//            user.getDiary().addLessonToEndOfTimetable(DayOfWeekEnum.TUESDAY,"Химия");
//            user.getDiary().addLessonToEndOfTimetable(DayOfWeekEnum.SATURDAY,"Русский");
//            user.getDiary().addLessonToEndOfTimetable(DayOfWeekEnum.SATURDAY,"Литература");
//        }catch (Exception e){
//            e.printStackTrace();
//        }
        save(user);
    }

    protected void save(UserEntity user) {
//        new Thread(() -> {
        File file = createFileObject(user.getChatID_tg());
        while (!checkFile(file)) ;

        try (PrintWriter writer = new PrintWriter(file.getPath(), StandardCharsets.UTF_8)) {
            writer.println(getStringFromUser(user));
        } catch (IOException ignored) {
            ignored.printStackTrace();
        }
//        }).start();
    }

    private File createFileObject(long id) {
        return new File(String.format("%s\\%s%s",
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

    private String getStringFromUser(UserEntity user) {
        return UserToJSONMapper.userToJSON(user);
    }
}
