package ru.nexonmi.DiaryBotNexonmi.data.usecases;

import org.springframework.stereotype.Service;
import ru.nexonmi.DiaryBotNexonmi.data.answer.*;

@Service
public class UseCaseFacade {

    private final DeleteAllDataUC deleteAllDataUC;
    private final GetListOfFilesUC getListOfAllFilesUC;
    private final GetFileDataByFileNameUC getFileDataByFileNameUC;
    private final UploadFileUC uploadFileUC;
    private final UploadDataToFileByFileNameUC uploadDataToFileByFileNameUC;

    public UseCaseFacade(
            DeleteAllDataUC deleteAllDataUC,
            GetListOfFilesUC getListOfAllFilesUC,
            GetFileDataByFileNameUC getFileDataByFileNameUC,
            UploadFileUC uploadFileUC,
            UploadDataToFileByFileNameUC uploadDataToFileByFileNameUC
    ) {
        this.deleteAllDataUC = deleteAllDataUC;
        this.getListOfAllFilesUC = getListOfAllFilesUC;
        this.getFileDataByFileNameUC = getFileDataByFileNameUC;
        this.uploadFileUC = uploadFileUC;
        this.uploadDataToFileByFileNameUC  = uploadDataToFileByFileNameUC;
    }

    public DeleteAllDataAnswer deleteAllData(String deleteKey){
        return deleteAllDataUC.deleteAllFiles(deleteKey);
    }

    public GetListOfFilesAnswer getListOfFiles(String accessKey){
        return getListOfAllFilesUC.getListOfFiles(accessKey);
    }

    public GetFileDataByFileNameAnswer getFileDataByFileName(String accessKey, String filename){
        return getFileDataByFileNameUC.getFileDataByFileName(accessKey, filename);
    }

    public UploadFileAnswer uploadFile(String accessKey, String fileName, String fileData){
        return uploadFileUC.uploadFile(accessKey, fileName, fileData);
    }

    public UploadDataToFileByFileNameAnswer uploadDataToFileByFileName(String accessKey, String fileName, String fileData){
        return uploadDataToFileByFileNameUC.uploadDataToFileByFileName(accessKey, fileName, fileData);
    }
}
