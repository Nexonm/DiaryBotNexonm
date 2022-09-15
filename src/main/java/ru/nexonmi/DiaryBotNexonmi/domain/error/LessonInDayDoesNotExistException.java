package ru.nexonmi.DiaryBotNexonmi.domain.error;

public class LessonInDayDoesNotExistException extends Exception {

    private static final String MESSAGE = "Can't delete lesson from day cause there is no lesson called %s";

    /**
     *
     * @param lessonWrongName name of lesson that doesn't exist in day
     */
    public LessonInDayDoesNotExistException(String lessonWrongName) {
        super(String.format(MESSAGE, lessonWrongName));
    }
}
