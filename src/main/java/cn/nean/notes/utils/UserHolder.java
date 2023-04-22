package cn.nean.notes.utils;

import cn.nean.notes.model.pojo.User;

public class UserHolder {

    private static final ThreadLocal<User> tl = new ThreadLocal<>();

    public static void saveUser(User userDto){
        tl.set(userDto);
    }

    public static User getUser(){
        return tl.get();
    }

    public static void removeUser(){
        tl.remove();
    }
}