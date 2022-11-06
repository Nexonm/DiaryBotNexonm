package ru.nexonmi.DiaryBotNexonmi.data.answer;

import com.google.gson.Gson;
import lombok.Getter;

public class UploadDataToFileByFileNameAnswer {

    @Getter private final boolean accessKeyAccepted;
    @Getter private final boolean fileExists;
    @Getter private final boolean dataUploaded;
    @Getter private final String exceptionMessage;

    public UploadDataToFileByFileNameAnswer(boolean accessKeyAccepted, boolean fileExists, boolean dataUploaded) {
        this.accessKeyAccepted = accessKeyAccepted;
        this.fileExists = fileExists;
        this.dataUploaded = dataUploaded;
        this.exceptionMessage = "";
    }

    public UploadDataToFileByFileNameAnswer(boolean accessKeyAccepted, boolean fileExists, boolean dataUploaded, String exceptionMessage) {
        this.accessKeyAccepted = accessKeyAccepted;
        this.fileExists = fileExists;
        this.dataUploaded = dataUploaded;
        this.exceptionMessage = exceptionMessage;
    }

    @Override
    public String toString() {
        return (new Gson()).toJson(this, this.getClass());
    }
}
