package ru.nexonmi.DiaryBotNexonmi.botapi.statistic;

class StatisticPOJO {
    protected long requestsCount;
    protected long uniqueIdsCount;
    protected long uniqueUsersIdCount;
    protected long uniqueGroupIdCount;

    public StatisticPOJO(long requestsCount, long uniqueIdsCount, long uniqueUsersIdCount, long uniqueGroupIdCount) {
        this.requestsCount = requestsCount;
        this.uniqueIdsCount = uniqueIdsCount;
        this.uniqueUsersIdCount = uniqueUsersIdCount;
        this.uniqueGroupIdCount = uniqueGroupIdCount;
    }
}
