package ru.nexonmi.DiaryBotNexonmi.data.dao;

public class LessonDAO {

    public String name;
    public String homeWork;
    public int cabinet;

    public LessonDAO(String name, String homeWork, int cabinet) {
        this.name = name;
        this.homeWork = homeWork;
        this.cabinet = cabinet;
    }
}
