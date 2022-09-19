package ru.nexonmi.DiaryBotNexonmi.botapi.updates.callback;

public enum CallbackEnum {

    //TODO make all callback buttons use this variables not straight str
    ADD_LESSON_TO_TIMETABLE_CHOSE_DAY("command.callback.add_lesson_to_timetable_chose_day", "oops"),
    ADD_LESSON_TO_DAY_CHOOSE_LESSON("command.callback.add_lesson_to_day_chose_lesson", "oops"),
    ADD_LESSON_TO_DAY("command.callback.add_lesson_to_day_add_lesson", "oops"),
    SHOW_ALL_TIMETABLE("command.callback.show_all_timetable", "replay.callback.show_all_message_title");

    String commandCode;
    String replayCode;

    CallbackEnum(String commandCode, String replayCode) {
        this.commandCode = commandCode;
        this.replayCode = replayCode;
    }
}
