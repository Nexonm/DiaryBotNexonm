package ru.nexonmi.DiaryBotNexonmi.botapi.updates.message;


enum MessageEnum {
    //TODO make all callback buttons use callback.CallbackEnum variables not straight str
    START("command.message.start", "replay.message.start"),
    REGISTER_NEW_LESSON("command.message.register_new_lesson", "replay.message.register_new_lesson"),
    TIMETABLE_EDIT("command.message.timetable_edit", "replay.message.timetable_edit"),
    TIMETABLE_ALL("command.message.timetable_all", "replay.message.timetable_all"),
    HOMEWORK_ADD_CHOOSE_LESSON("command.message.homework_add_choose_lesson",
            "replay.message.homework_add_choose_lesson"),
    HOMEWORK_TOMORROW("command.message.homework_tomorrow",
            "replay.message.homework_tomorrow");

    String commandCode;
    String replayCode;

    MessageEnum(String commandCode, String replayCode) {
        this.commandCode = commandCode;
        this.replayCode = replayCode;
    }
}
