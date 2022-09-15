package ru.nexonmi.DiaryBotNexonmi.domain.entity;

public class UserEntity {

    ////////////////////////////////////////////
    /////////// Fields ////////////////////////

    //constants
    public static final long DEFAULT_ID = -1;

    //entity fields
    private final long chatID_tg;
    private final DiaryEntity diary;

    ////////////////////////////////////////////
    /////////// Constructors //////////////////

    public UserEntity(long chatID_tg) {
        this.chatID_tg = chatID_tg;
        diary = new DiaryEntity();
    }

    public UserEntity() {
        this(DEFAULT_ID);
    }

    ////////////////////////////////////////////
    ////////////// Getters ////////////////////

    public long getChatID_tg() {
        return chatID_tg;
    }

    public DiaryEntity getDiary() {
        return diary;
    }

}
