package ru.nexonmi.DiaryBotNexonmi.data.answer;

import com.google.gson.Gson;
import lombok.Getter;
import lombok.Setter;

public class DeleteAllDataAnswer {

    @Getter @Setter private int numOfFoundFiles;
    @Getter @Setter private int numOfDeletedFiles;
    @Getter @Setter private int numOfGroupFiles;
    @Getter @Setter private int numOfUserFiles;
    @Getter @Setter private int numOfExceptionOccurred;
    private final boolean deleteKeyAccepted;

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
