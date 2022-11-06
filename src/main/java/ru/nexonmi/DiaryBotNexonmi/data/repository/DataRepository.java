package ru.nexonmi.DiaryBotNexonmi.data.repository;


import org.springframework.stereotype.Service;
import ru.nexonmi.DiaryBotNexonmi.data.answer.*;
import ru.nexonmi.DiaryBotNexonmi.data.usecases.UploadFileUC;
import ru.nexonmi.DiaryBotNexonmi.data.usecases.UseCaseFacade;
import ru.nexonmi.DiaryBotNexonmi.domain.entity.UserEntity;

@Service
public class DataRepository {

    private final UserDataGet udGet;
    private final UserDataSave udSave;
    private final UseCaseFacade UCFacade;

    public DataRepository(UserDataGet udGet, UserDataSave udSave, UseCaseFacade UCFacade) {
        this.udGet = udGet;
        this.udSave = udSave;
        this.UCFacade = UCFacade;
    }

    public DeleteAllDataAnswer deleteAllData(String deleteKey){
        return UCFacade.deleteAllData(deleteKey);
    }

    public GetListOfFilesAnswer getListOfFiles(String accessKey){
        return UCFacade.getListOfFiles(accessKey);
    }

    public GetFileDataByFileNameAnswer getFileDataByFileName(String accessKey, String filename){
        return UCFacade.getFileDataByFileName(accessKey, filename);
    }

    public UploadFileAnswer uploadFile(String accessKey, String fileName, String fileData){
        return UCFacade.uploadFile(accessKey, fileName, fileData);
    }

    public UploadDataToFileByFileNameAnswer uploadDataToFileByFileName(String accessKey, String fileName, String fileData){
        return UCFacade.uploadDataToFileByFileName(accessKey, fileName, fileData);
    }



    public void save(UserEntity user) {
        udSave.save(user);
    }

    public UserEntity get(long chatId) {
        return udGet.get(chatId);
    }
}
