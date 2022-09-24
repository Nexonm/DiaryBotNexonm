package ru.nexonmi.DiaryBotNexonmi.domain.entity;

public enum StateEnum {

    DEFAULT_STATE,
    ADD_HOMEWORK;

    private String details;

    StateEnum() {
        this.details = "";
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getDetails() {
        return details;
    }
}
