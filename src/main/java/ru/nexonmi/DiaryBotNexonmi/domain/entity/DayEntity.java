package ru.nexonmi.DiaryBotNexonmi.domain.entity;

import ru.nexonmi.DiaryBotNexonmi.domain.error.LessonInDayDoesNotExistException;
import ru.nexonmi.DiaryBotNexonmi.domain.error.NegativeLessonOrderNumberException;
import ru.nexonmi.DiaryBotNexonmi.domain.error.OrderOutOfDayLessonsException;
import ru.nexonmi.DiaryBotNexonmi.domain.error.ZeroLessonOrderNumberException;

import java.util.LinkedList;

public class DayEntity {

    ////////////////////////////////////////////
    /////////// Fields ////////////////////////

    //Constants
    public static final DayOfWeekEnum DEFAULT_DAY_IN_WEEK = DayOfWeekEnum.MONDAY;

    //Entity fields

    private final DayOfWeekEnum dayOfWeek;
    private LinkedList<Integer> lessonIDs;

    ////////////////////////////////////////////
    /////////// Constructors //////////////////

    public DayEntity(DayOfWeekEnum dayOfWeek) {
        if (dayOfWeek == null) this.dayOfWeek = DEFAULT_DAY_IN_WEEK;
        else this.dayOfWeek = dayOfWeek;
        lessonIDs = new LinkedList<>();
    }

    public DayEntity(DayOfWeekEnum dayOfWeek, LinkedList<Integer> lessonIDs) {
        if (dayOfWeek == null) this.dayOfWeek = DEFAULT_DAY_IN_WEEK;
        else this.dayOfWeek = dayOfWeek;
        if (lessonIDs == null) this.lessonIDs = new LinkedList<>();
        else this.lessonIDs = lessonIDs;
    }

    ////////////////////////////////////////////
    /////////// Methods ///////////////////////


    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        else if (obj instanceof DayEntity day)
            return this.dayOfWeek == day.dayOfWeek;
        else return false;
    }

    @Override
    public String toString() {
        return String.format("Day.class, dayInWeek : %s, lessons.size : %d, lessons : %s",
                this.dayOfWeek.getDayName(), this.lessonIDs.size(), this.lessonIDs);
    }

    /**
     * Add lesson but on order position. Note that people start counting with 1,
     * but program start counting with 0. So if order is 3, lesson is placed to 2 in list,
     * but it is steel 3rd.
     *
     * @param lessonId id of lesson object
     * @param order      in timetable
     * @return ture is everything is ok and lesson was
     * @throws NegativeLessonOrderNumberException if order < 0
     * @throws ZeroLessonOrderNumberException     if order == 0
     */
    protected boolean addLessonWithOrder(int lessonId, int order)
            throws NegativeLessonOrderNumberException,
            ZeroLessonOrderNumberException {
        if (order < 0) throw new NegativeLessonOrderNumberException();
        else if (order == 0) throw new ZeroLessonOrderNumberException();
        else if (order - 1 == lessonIDs.size()) lessonIDs.add(lessonId);
        else lessonIDs.add(order - 1, lessonId);
        return true;
    }

    /**
     * Add lesson to the end of timetable.
     *
     * @param lessonId id of lesson object
     * @return true if lesson was added
     */
    protected boolean addLessonToEndOfTimetable(int lessonId) {
        lessonIDs.add(lessonId);
        return true;
    }

    /**
     * Deletes lesson in 'order' param place in timetable.
     *
     * @param order place (number) in timetable
     * @return true if lesson in 'order' place was deleted
     * @throws NegativeLessonOrderNumberException if order < 0
     * @throws ZeroLessonOrderNumberException     if order == 0
     * @throws OrderOutOfDayLessonsException      if there is number more than lessons in day
     */
    protected boolean removeLessonByOrder(int order)
            throws NegativeLessonOrderNumberException,
            ZeroLessonOrderNumberException,
            OrderOutOfDayLessonsException {
        if (order < 0) throw new NegativeLessonOrderNumberException();
        if (order == 0) throw new ZeroLessonOrderNumberException();
        if (order > lessonIDs.size()) throw new OrderOutOfDayLessonsException();
        lessonIDs.remove(order - 1);
        return true;
    }

    /**
     * Removes first lesson in timetable.
     *
     * @param lessonId id of obj to delete
     * @return true if first lesson obj was deleted from list
     */
    protected boolean removeLessonByObjectFirst(int lessonId)
            throws LessonInDayDoesNotExistException {
        if (!lessonIDs.contains(lessonId))
            throw new LessonInDayDoesNotExistException(String.valueOf(lessonId));
        return lessonIDs.removeFirstOccurrence(lessonId);
    }

    /**
     * Removes all 'lesson' objects from list.
     *
     * @param lessonId id of lesson object to delete
     * @return true if all 'lesson' objects were deleted
     */
    protected boolean removeLessonByObjectAll(int lessonId) {
        //in case there is no such lesson at all and no need to remove it
        if (!lessonIDs.contains(lessonId)) return true;

        while (lessonIDs.contains(lessonId))
            lessonIDs.removeFirstOccurrence(lessonId);

        return !lessonIDs.contains(lessonId);
    }


    ////////////////////////////////////////////
    /////////// Getters and Setters ///////////

    public DayOfWeekEnum getDayOfWeek() {
        return dayOfWeek;
    }

    public LinkedList<Integer> getLessonIDs() {
        return lessonIDs;
    }

    private void setLessonIDs(LinkedList<Integer> lessonIDs) {
        this.lessonIDs = lessonIDs;
    }
}
