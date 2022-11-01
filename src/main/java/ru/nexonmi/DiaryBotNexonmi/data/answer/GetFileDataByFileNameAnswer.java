package ru.nexonmi.DiaryBotNexonmi.data.answer;

import com.google.gson.Gson;
import lombok.Setter;

public class GetFileDataByFileNameAnswer {

    private final boolean accessKeyAccepted;
    private final boolean fileExists;
    @Setter private long lengthOfFile;
    @Setter private String data;

    public GetFileDataByFileNameAnswer(boolean accessKeyAccepted, boolean fileExists) {
        this.accessKeyAccepted = accessKeyAccepted;
        this.fileExists = fileExists;
        this.lengthOfFile = 0;
        this.data = "";
    }

    @Override
    public String toString() {
        return (new Gson()).toJson(this, this.getClass());
    }
}
