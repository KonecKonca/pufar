package com.kozitski.pufar.entity.user;

import java.util.ArrayList;
import java.util.List;

public class Users {

    public static User createDefaultUser(){
        return new User( -666, "", "****", null, false, null);
    }
    public static List<User> createUserArrayList(List<User> list){

        return new ArrayList<User>(list){
            @Override
            public User get(int index) {
                if(index == 0 && size() == 0){
                    return null;
                }
                else {
                    return super.get(index);
                }
            }

        };

    }
    public static int statusPriority(UserStatus userStatus){
        int result;

        switch (userStatus){
            case SIMPLE_USER:
                result = 1;
                break;
            case ADMIN:
                result = 2;
                break;
            case SUPER_ADMIN:
                result = 3;
                break;
            default:
                result = 1;
        }

        return result;
    }

    public static boolean checkAccessRight(User currentUser, User foundUser){
        return ((foundUser.getStatus().equals(UserStatus.SIMPLE_USER) && currentUser.getStatus().equals(UserStatus.ADMIN))
                    || ((currentUser.getStatus().equals(UserStatus.SUPER_ADMIN)) &&
                (foundUser.getStatus().equals(UserStatus.SIMPLE_USER) || foundUser.getStatus().equals(UserStatus.ADMIN))));
    }

}
