package ru.nexonmi.DiaryBotNexonmi.data.repository;

import com.google.gson.Gson;

public class AnswerDeleteAllData {

    protected int numOfFoundFiles;
    protected int numOfDeletedFiles;
    protected int numOfGroupFiles;
    protected int numOfUserFiles;
    protected int numOfExceptionFound;
    protected boolean deleteKeyAccepted;

    public AnswerDeleteAllData(boolean deleteKeyAccepted){
        this.deleteKeyAccepted = deleteKeyAccepted;
        this.numOfFoundFiles = 0;
        this.numOfDeletedFiles = 0;
        this.numOfGroupFiles = 0;
        this.numOfUserFiles = 0;
        this.numOfExceptionFound = 0;
    }

    @Override
    public String toString() {
        return (new Gson()).toJson(this, AnswerDeleteAllData.class);
    }

    public int getNumOfFoundFiles() {
        return numOfFoundFiles;
    }

    public int getNumOfDeletedFiles() {
        return numOfDeletedFiles;
    }

    public int getNumOfGroupFiles() {
        return numOfGroupFiles;
    }

    public int getNumOfUserFiles() {
        return numOfUserFiles;
    }

    public int getNumOfExceptionFound() {
        return numOfExceptionFound;
    }

    public boolean isDeleteKeyAccepted() {
        return deleteKeyAccepted;
    }
}
