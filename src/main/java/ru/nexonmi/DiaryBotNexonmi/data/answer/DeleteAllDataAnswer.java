package ru.nexonmi.DiaryBotNexonmi.data.answer;

import com.google.gson.Gson;
import lombok.Setter;

public class DeleteAllDataAnswer {

    //main field, true -> files were deleted, false -> incorrect key, files cannot be deleted
    private final boolean deleteKeyAccepted;
    @Setter private int numOfFoundFiles;
    @Setter private int numOfDeletedFiles;
    @Setter private int numOfGroupFiles;
    @Setter private int numOfUserFiles;
    @Setter private int numOfExceptionOccurred;

    public DeleteAllDataAnswer(boolean deleteKeyAccepted){
        this.deleteKeyAccepted = deleteKeyAccepted;
        this.numOfFoundFiles = 0;
        this.numOfDeletedFiles = 0;
        this.numOfGroupFiles = 0;
        this.numOfUserFiles = 0;
        this.numOfExceptionOccurred = 0;
    }

    @Override
    public String toString() {
        return (new Gson()).toJson(this, DeleteAllDataAnswer.class);
    }

}
