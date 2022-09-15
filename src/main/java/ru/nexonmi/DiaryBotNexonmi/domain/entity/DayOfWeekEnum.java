package ru.nexonmi.DiaryBotNexonmi.domain.entity;

public enum DayOfWeekEnum {

    MONDAY ("MONDAY", 1),
    TUESDAY ("TUESDAY", 2),
    WEDNESDAY ("WEDNESDAY", 3),
    THURSDAY ("THURSDAY", 4),
    FRIDAY ("FRIDAY", 5),
    SATURDAY ("SATURDAY", 6),
    SUNDAY ("SUNDAY", 7);

    private final String dayName;
    private final int dayNum;

    DayOfWeekEnum(String dayName, int num){
        this.dayName = dayName;
        this.dayNum = num;
    }

    public String getDayName() {
        return dayName;
    }

    public int getDayNum() {
        return dayNum;
    }

    public static DayOfWeekEnum getDayByNum(int num){
        for (DayOfWeekEnum day : DayOfWeekEnum.values()){
            if (day.getDayNum() == num) return day;
        }
        return MONDAY;
    }

    @Override
    public String toString() {
        return String.format("nexonmi.ru.DiaryBotNexomni.domain.entity.DayOfWeekEnum{ title = \"%s\"}", this.getDayName());
    }
}
