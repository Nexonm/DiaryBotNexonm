package ru.nexonmi.DiaryBotNexonmi.botapi.updates.message.state;

public enum StateEnum {

    HOMEWORK_ADD(ru.nexonmi.DiaryBotNexonmi.domain.entity.StateEnum.ADD_HOMEWORK, "replay.state.homework_add");

    ru.nexonmi.DiaryBotNexonmi.domain.entity.StateEnum state;
    String replayCode;

    StateEnum(ru.nexonmi.DiaryBotNexonmi.domain.entity.StateEnum state, String replayCode) {
        this.state = state;
        this.replayCode = replayCode;
    }
}
