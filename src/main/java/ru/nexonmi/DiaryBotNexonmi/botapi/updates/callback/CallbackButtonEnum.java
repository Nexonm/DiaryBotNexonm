package ru.nexonmi.DiaryBotNexonmi.botapi.updates.callback;

public enum CallbackButtonEnum {

    ADD_LESSON_TO_TIMETABLE_CHOOSE_DAY("btn.add_lesson_to_timetable.title", CallbackEnum.ADD_LESSON_TO_TIMETABLE_CHOOSE_DAY),
    ADD_LESSON_TO_TIMETABLE_CHOOSE_DAY_ANOTHER("replay.callback.btn.choose_another_day", CallbackEnum.ADD_LESSON_TO_TIMETABLE_CHOOSE_DAY),
    ADD_LESSON_TO_DAY_ADD_LESSON("null", CallbackEnum.ADD_LESSON_TO_DAY_ADD_LESSON),
    ADD_LESSON_TO_DAY_CHOOSE_LESSON("replay.callback.btn.add_more_lessons", CallbackEnum.ADD_LESSON_TO_DAY_CHOOSE_LESSON),
    SHOW_ALL_TIMETABLE("replay.callback.btn.show_all_timetable", CallbackEnum.SHOW_ALL_TIMETABLE),
    DELETE_LESSON_CHOOSE_DAY("btn.delete_lesson_from_timetable.title", CallbackEnum.DELETE_LESSON_CHOOSE_DAY),
    DELETE_LESSON_CHOOSE_DAY_ANOTHER("btn.delete_lesson_from_timetable.choose_another_day.title", CallbackEnum.DELETE_LESSON_CHOOSE_DAY),
    DELETE_LESSON_CHOOSE_LESSON("btn.delete_lesson_from_timetable.delete_another_lesson.title", CallbackEnum.DELETE_LESSON_CHOOSE_LESSON),
    DELETE_LESSON_FROM_DAY("null", CallbackEnum.DELETE_LESSON_FROM_DAY),
    HOMEWORK_ADD_SET_STATE("ignored", CallbackEnum.HOMEWORK_ADD_SET_STATE),
    TIMETABLE_EDIT("btn.timetable_edit.title", CallbackEnum.TIMETABLE_EDIT),
    SOME_ERROR("replay.some_error", null);

    public String title;
    public CallbackEnum callbackAction;

    CallbackButtonEnum(String title, CallbackEnum callbackAction) {
        this.title = title;
        this.callbackAction = callbackAction;
    }
}
