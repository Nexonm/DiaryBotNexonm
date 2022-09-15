package ru.nexonmi.DiaryBotNexonmi.data.repository;


import org.springframework.stereotype.Service;
import ru.nexonmi.DiaryBotNexonmi.domain.entity.UserEntity;

@Service
public class DataRepository {

    private final UserDataGet udGet;
    private final UserDataSave udSave;

    public DataRepository(UserDataGet udGet, UserDataSave udSave) {
        this.udGet = udGet;
        this.udSave = udSave;
    }

    public void save(UserEntity user) {
        udSave.save(user);
    }

    public UserEntity get(long chatId) {
        return udGet.get(chatId);
    }
}
