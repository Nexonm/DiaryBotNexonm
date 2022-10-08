package ru.nexonmi.DiaryBotNexonmi.domain.error;

public class LessonAlreadyExistsException extends Exception{

    private static final String MESSAGE = "Can't add this lesson. Such lesson already exists!";
    private final String LESSON_NAME;

    public LessonAlreadyExistsException(String lessonName) {
        super(MESSAGE);
        this.LESSON_NAME = lessonName;
    }

    public String getLessonName() {
        return LESSON_NAME;
    }
}
