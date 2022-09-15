package ru.nexonmi.DiaryBotNexonmi.domain.error;

public class LessonDoesNotExistException extends Exception{

    private static final String MESSAGE = "Brand new lesson must not have name that already exists, this lesson name = %s";

    /**
     *
     * @param lessonWrongName wrong name of lesson that doesn't exist
     */
    public LessonDoesNotExistException(String lessonWrongName) {
        super(String.format(MESSAGE, lessonWrongName));
    }
}
