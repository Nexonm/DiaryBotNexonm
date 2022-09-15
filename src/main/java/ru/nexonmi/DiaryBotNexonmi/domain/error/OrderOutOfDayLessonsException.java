package ru.nexonmi.DiaryBotNexonmi.domain.error;

public class OrderOutOfDayLessonsException extends Exception {
    private static final String MESSAGE = "Lesson order num cannot be more than lessons count in day";

    public OrderOutOfDayLessonsException() {
        super(MESSAGE);
    }
}
