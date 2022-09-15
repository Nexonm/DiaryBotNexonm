package ru.nexonmi.DiaryBotNexonmi.domain.entity;

import ru.nexonmi.DiaryBotNexonmi.domain.error.*;

import java.util.*;

public class DiaryEntity {

    ////////////////////////////////////////////
    /////////// Fields ////////////////////////

    private final DayEntity[] days;
    private int lastLessonId;
    private HashMap<Integer, LessonEntity> userLessons;

    ////////////////////////////////////////////
    /////////// Constructors //////////////////

    public DiaryEntity() {
        this.days = new DayEntity[7];
        lastLessonId = 0;
        userLessons = new HashMap<>();
        initDays();
    }

    public DiaryEntity(DayEntity[] days) {
        if (days == null || days.length != 7) {
            this.days = new DayEntity[7];
            initDays();
        } else {
            boolean flag = true;
            for (int i = 0; i < days.length; i++) {
                if (!(i == days[i].getDayOfWeek().ordinal())) flag = false;
            }
            if (flag) {
                this.days = days;
            } else {
                this.days = new DayEntity[7];
                initDays();
            }
        }
    }


    ////////////////////////////////////////////
    /////////// Methods ///////////////////////

    private void initDays() {
        days[DayOfWeekEnum.MONDAY.getDayNum() - 1] = new DayEntity(DayOfWeekEnum.MONDAY);
        days[DayOfWeekEnum.TUESDAY.getDayNum() - 1] = new DayEntity(DayOfWeekEnum.TUESDAY);
        days[DayOfWeekEnum.WEDNESDAY.getDayNum() - 1] = new DayEntity(DayOfWeekEnum.WEDNESDAY);
        days[DayOfWeekEnum.THURSDAY.getDayNum() - 1] = new DayEntity(DayOfWeekEnum.THURSDAY);
        days[DayOfWeekEnum.FRIDAY.getDayNum() - 1] = new DayEntity(DayOfWeekEnum.FRIDAY);
        days[DayOfWeekEnum.SATURDAY.getDayNum() - 1] = new DayEntity(DayOfWeekEnum.SATURDAY);
        days[DayOfWeekEnum.SUNDAY.getDayNum() - 1] = new DayEntity(DayOfWeekEnum.SUNDAY);
    }

    /**
     * All user lessons are put in userLessons map, so every lesson can be accessed by its id.
     *
     * @param lessonName new lesson
     * @throws LessonAlreadyExistsException in case such lesson already exists
     */
    public void registerLesson(String lessonName)
            throws LessonAlreadyExistsException {
        boolean contains = false;
        for (LessonEntity lesson : userLessons.values())
            if (lesson.getName().equals(lessonName)) {
                contains = true;
                break;
            }

        if (contains) throw new LessonAlreadyExistsException();
        lastLessonId++;
        LessonEntity lesson = new LessonEntity(lastLessonId, lessonName);

        userLessons.put(lesson.getID(), lesson);
    }

    /////////// DayEntity methods access with protection ///////////

    /**
     * @param lessonId id of lesson
     * @return true if lesson registered in userLessons
     * @throws LessonDoesNotExistException in case there is no such lesson
     */
    private boolean checkIfLessonExists(int lessonId)
            throws LessonDoesNotExistException {
        if (!userLessons.containsKey(lessonId))
            //TODO make it return lesson name not id :(
            throw new LessonDoesNotExistException(String.valueOf(lessonId));
        return true;
    }

    /**
     * @param lessonName name of lesson
     * @return true if lesson registered in userLessons
     * @throws LessonDoesNotExistException in case there is no such lesson
     */
    private boolean checkIfLessonExists(String lessonName)
            throws LessonDoesNotExistException {
        for (LessonEntity lesson : userLessons.values())
            if (!lesson.getName().equals(lessonName))
                throw new LessonDoesNotExistException(lessonName);
        return true;
    }

    /**
     * @param day      day to add lesson
     * @param lessonId name of lesson to be added
     * @param order    order in which lesson needs to be added
     * @return ture if lesson added
     * @throws LessonDoesNotExistException        in case there is no such lesson registered
     * @throws NegativeLessonOrderNumberException
     * @throws ZeroLessonOrderNumberException
     */
    public boolean addLessonWithOrder(DayOfWeekEnum day, int lessonId, int order)
            throws LessonDoesNotExistException,
            NegativeLessonOrderNumberException,
            ZeroLessonOrderNumberException {
        if (checkIfLessonExists(lessonId))
            return days[day.getDayNum() - 1].addLessonWithOrder(lessonId, order);
        //unreachable
        return false;
    }

    /**
     * @param day      day to add lesson
     * @param lessonId name of lesson to be added
     * @return true if lesson was added
     * @throws LessonDoesNotExistException in case there is no such lesson registered
     */
    public boolean addLessonToEndOfTimetable(DayOfWeekEnum day, int lessonId)
            throws LessonDoesNotExistException {
        System.out.println("lesson to check ");
        if (checkIfLessonExists(lessonId))
            return days[day.getDayNum() - 1].addLessonToEndOfTimetable(lessonId);
        //unreachable
        return false;
    }

    /**
     * @param day   day to remove lesson
     * @param order num in day to delete lesson
     * @return true if lesson was deleted
     * @throws NegativeLessonOrderNumberException if order < 0
     * @throws ZeroLessonOrderNumberException     if order == 0
     * @throws OrderOutOfDayLessonsException      if there is number more than lessons in day
     */
    public boolean removeLessonByOrder(DayOfWeekEnum day, int order)
            throws NegativeLessonOrderNumberException,
            ZeroLessonOrderNumberException,
            OrderOutOfDayLessonsException {
        return days[day.getDayNum() - 1].removeLessonByOrder(order);
    }

    /**
     * @param day      day to remove lesson
     * @param lessonId id of lesson to be deleted
     * @return true if lesson was removed
     * @throws LessonInDayDoesNotExistException in case there is no such lesson in that day
     */
    public boolean removeLessonByObjectFirst(DayOfWeekEnum day, int lessonId)
            throws LessonInDayDoesNotExistException {
        return days[day.getDayNum() - 1].removeLessonByObjectFirst(lessonId);
    }

    /**
     * @param day      day to remove lesson
     * @param lessonId id of lesson to be deleted
     * @return true if lesson was removed
     */
    public boolean removeLessonByObjectAll(DayOfWeekEnum day, int lessonId) {
        return days[day.getDayNum() - 1].removeLessonByObjectAll(lessonId);
    }


    ////////////////////////////////////////////
    /////////// Getters and Setters ///////////

    public DayEntity[] getDays() {
        return days;
    }

    /**
     * @return sorted array of lessons
     */
    public LessonEntity[] getUserLessonsArray() {
        return new TreeSet<LessonEntity>(userLessons.values()).toArray(new LessonEntity[0]);
    }

    public HashMap<Integer, LessonEntity> getUserLessons() {
        return userLessons;
    }
}
