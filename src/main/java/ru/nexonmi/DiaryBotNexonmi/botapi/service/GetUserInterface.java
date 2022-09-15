package ru.nexonmi.DiaryBotNexonmi.botapi.service;

import ru.nexonmi.DiaryBotNexonmi.domain.entity.UserEntity;

public interface GetUserInterface {
    UserEntity get(long chat_id);
}
