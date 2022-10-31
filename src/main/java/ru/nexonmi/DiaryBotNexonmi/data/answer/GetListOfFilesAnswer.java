package ru.nexonmi.DiaryBotNexonmi.data.answer;

import com.google.gson.Gson;
import lombok.Setter;

public class GetListOfFilesAnswer {

    private final boolean accessKeyAccepted;
    @Setter private int numOfFoundFiles;
    @Setter private int numOfGroupFiles;
    @Setter private int numOfUserFiles;
    @Setter private String[] fileNames;

    public GetListOfFilesAnswer(boolean accessKeyAccepted) {
        this.accessKeyAccepted = accessKeyAccepted;
        this.numOfFoundFiles = 0;
        this.numOfGroupFiles = 0;
        this.numOfUserFiles = 0;
        this.fileNames = new String[0];
    }

    @Override
    public String toString() {
        return (new Gson()).toJson(this, this.getClass());
    }
}
