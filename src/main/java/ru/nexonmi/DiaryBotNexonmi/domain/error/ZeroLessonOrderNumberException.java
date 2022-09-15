package ru.nexonmi.DiaryBotNexonmi.domain.error;

public class ZeroLessonOrderNumberException extends Exception{

    private static final String MESSAGE = "Lesson order cannot be equals to zero";

    public ZeroLessonOrderNumberException() {
        super(MESSAGE);
    }
}
