package ru.nexonmi.DiaryBotNexonmi.data.usecases;

import org.springframework.stereotype.Service;
import ru.nexonmi.DiaryBotNexonmi.data.answer.DeleteAllDataAnswer;
import ru.nexonmi.DiaryBotNexonmi.data.answer.GetListOfFilesAnswer;
@Service
public class UseCaseFacade {

    private final DeleteAllDataUC deleteAllDataUC;
    private final GetListOfFilesUC getListOfAllFilesUC;

    public UseCaseFacade(
            DeleteAllDataUC deleteAllDataUC,
            GetListOfFilesUC getListOfAllFilesUC
    ) {
        this.deleteAllDataUC = deleteAllDataUC;
        this.getListOfAllFilesUC = getListOfAllFilesUC;
    }

    public DeleteAllDataAnswer deleteAllData(String deleteKey){
        return deleteAllDataUC.deleteAllFiles(deleteKey);
    }

    public GetListOfFilesAnswer getListOfFiles(String accessKey){
        return getListOfAllFilesUC.getListOfFiles(accessKey);
    }
}
