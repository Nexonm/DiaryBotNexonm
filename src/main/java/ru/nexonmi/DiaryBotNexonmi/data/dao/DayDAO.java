package ru.nexonmi.DiaryBotNexonmi.data.dao;

public class DayDAO {

    public String dayOfWeek;
    public String lessonNamesList;

    public DayDAO(String dayOfWeek, String lessonNamesList) {
        this.dayOfWeek = dayOfWeek;
        this.lessonNamesList = lessonNamesList;
    }
}
