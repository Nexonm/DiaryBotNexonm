package ru.nexonmi.DiaryBotNexonmi.domain.error;

public class NegativeLessonOrderNumberException extends Exception{

    private static final String MESSAGE = "Lesson order cannot be less than 0";

    public NegativeLessonOrderNumberException() {
        super(MESSAGE);
    }
}
