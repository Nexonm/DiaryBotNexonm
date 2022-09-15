package ru.nexonmi.DiaryBotNexonmi.data.mapper;

import com.google.gson.Gson;
import ru.nexonmi.DiaryBotNexonmi.domain.entity.UserEntity;

public class UserToJSONMapper {

    public static String userToJSON(UserEntity user){
        return (new Gson()).toJson(user);
    }

    public static UserEntity JSONToUser(String json){
        return (new Gson()).fromJson(json, UserEntity.class);
    }
}
