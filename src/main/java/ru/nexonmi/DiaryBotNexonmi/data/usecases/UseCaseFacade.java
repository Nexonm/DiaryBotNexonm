package ru.nexonmi.DiaryBotNexonmi.data.usecases;

import ru.nexonmi.DiaryBotNexonmi.data.answer.DeleteAllDataAnswer;

public class UseCaseFacade {

    private DeleteAllDataUC deleteAllDataUC;

    public UseCaseFacade(DeleteAllDataUC deleteAllDataUC) {
        this.deleteAllDataUC = deleteAllDataUC;
    }

    public DeleteAllDataAnswer deleteAllData(String deleteKey){
        return deleteAllDataUC.deleteAllFiles(deleteKey);
    }
}
