package ru.nexonmi.DiaryBotNexonmi.domain.error;

public class LessonAlreadyExistsException extends Exception{

    private static final String MESSAGE = "Can't add this lesson. Such lesson already exists!";

    public LessonAlreadyExistsException() {
        super(MESSAGE);
    }
}
