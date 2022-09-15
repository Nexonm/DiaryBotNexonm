package ru.nexonmi.DiaryBotNexonmi.domain.entity;

public class LessonEntity implements Comparable<LessonEntity>{

    ////////////////////////////////////////////
    /////////// Fields ////////////////////////

    //Constants
    public static final int DEFAULT_CABINET = -1;
    public static final int DEFAULT_ID= -1;

    //Entity fields
    private int ID;
    private String name;
    private String homework;
    private int cabinet;



    ////////////////////////////////////////////
    /////////// Constructors //////////////////

    public LessonEntity(int ID, String name, String homework, int cabinet) {
        this.ID = ID;
        this.name = name;
        this.homework = homework;
        this.cabinet = cabinet;
    }

    public LessonEntity(int ID, String name) {
        this(ID, name, "", DEFAULT_CABINET);
    }

    ////////////////////////////////////////////
    /////////// Methods ///////////////////////

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj instanceof LessonEntity lesson) {
            return this.name.equals(((LessonEntity) obj).name);
        } else return false;

    }

    @Override
    public String toString() {
        return String.format("Lesson.class, Name : %s, Homework %s, cabinet : %d, hash : %s",
                this.name, this.homework, this.cabinet, this.hashCode());
    }

    /**
     * Compares two lessons by their names using standard String.compareTo method.
     * So lessons are sorted in alphabetical order. But nums are first.
     * @param otherObj obj to compare
     * @return result of comparing
     */
    @Override
    public int compareTo(LessonEntity otherObj) {
        return this.name.compareTo(otherObj.name);
    }

    ////////////////////////////////////////////
    /////////// Getters and Setters ///////////

    public int getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHomework() {
        return homework;
    }

    public void setHomework(String homework) {
        this.homework = homework;
    }

    public int getCabinet() {
        return cabinet;
    }

    public void setCabinet(int cabinet) {
        this.cabinet = cabinet;
    }
}
