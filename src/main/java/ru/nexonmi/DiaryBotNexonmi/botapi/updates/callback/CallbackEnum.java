package ru.nexonmi.DiaryBotNexonmi.botapi.updates.callback;

public enum CallbackEnum {

    //TODO make all callback buttons use this variables not straight str
    ADD_LESSON_TO_TIMETABLE_CHOOSE_DAY("command.callback.add_lesson_to_timetable_choose_day",
            "replay.callback.add_lesson_to_timetable_chose_day"),
    ADD_LESSON_TO_DAY_CHOOSE_LESSON("command.callback.add_lesson_to_day_choose_lesson", "oops"),
    ADD_LESSON_TO_DAY_ADD_LESSON("command.callback.add_lesson_to_day_add_lesson", "oops"),
    SHOW_ALL_TIMETABLE("command.callback.show_all_timetable", "replay.callback.show_all_message_title"),
    TIMETABLE_EDIT("command.callback.timetable_edit", "replay.callback.timetable_edit"),
    DELETE_LESSON_CHOOSE_DAY("command.callback.delete_lesson_choose_day", "" +
            "replay.callback.delete_lesson_chose_day"),
    DELETE_LESSON_CHOOSE_LESSON("command.callback.delete_lesson_choose_lesson", "nul"),
    DELETE_LESSON_FROM_DAY("command.callback.delete_lesson_from_day", "null"),
    HOMEWORK_ADD_SET_STATE("command.callback.homework_add_set_state",
            "replay.callback.homework_add_set_state");

    public String commandCode;
    String replayCode;

    CallbackEnum(String commandCode, String replayCode) {
        this.commandCode = commandCode;
        this.replayCode = replayCode;
    }

}
