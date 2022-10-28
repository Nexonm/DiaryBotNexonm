package ru.nexonmi.DiaryBotNexonmi.data.repository;


import org.springframework.stereotype.Service;
import ru.nexonmi.DiaryBotNexonmi.domain.entity.UserEntity;

@Service
public class DataRepository {

    private final UserDataGet udGet;
    private final UserDataSave udSave;
    private final DeleteAllData delAllData;

    public DataRepository(UserDataGet udGet, UserDataSave udSave, DeleteAllData delAllData) {
        this.udGet = udGet;
        this.udSave = udSave;
        this.delAllData = delAllData;
    }

    public AnswerDeleteAllData deleteAllData(String deleteKey){
        return delAllData.deleteAllFiles(deleteKey);
    }

    public void save(UserEntity user) {
        udSave.save(user);
    }

    public UserEntity get(long chatId) {
        return udGet.get(chatId);
    }
}
