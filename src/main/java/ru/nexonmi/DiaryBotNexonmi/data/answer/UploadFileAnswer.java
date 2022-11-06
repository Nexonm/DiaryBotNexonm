package ru.nexonmi.DiaryBotNexonmi.data.answer;

import com.google.gson.Gson;
import lombok.Getter;

public class UploadFileAnswer {

    @Getter private final boolean accessKeyAccepted;
    @Getter private final boolean fileAlreadyExists;
    @Getter private final boolean fileUploaded;
    @Getter private final String exceptionMessage;

    public UploadFileAnswer(boolean accessKeyAccepted, boolean fileAlreadyExists, boolean fileUploaded) {
        this.accessKeyAccepted = accessKeyAccepted;
        this.fileAlreadyExists = fileAlreadyExists;
        this.fileUploaded = fileUploaded;
        this.exceptionMessage = "";
    }

    public UploadFileAnswer(boolean accessKeyAccepted, boolean fileAlreadyExists, boolean fileUploaded, String exceptionMessage) {
        this.accessKeyAccepted = accessKeyAccepted;
        this.fileAlreadyExists = fileAlreadyExists;
        this.fileUploaded = fileUploaded;
        this.exceptionMessage = exceptionMessage;
    }

    @Override
    public String toString() {
        return (new Gson()).toJson(this, this.getClass());
    }
}
