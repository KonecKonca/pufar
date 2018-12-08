package com.kozitski.pufar.entity.user;

import com.kozitski.pufar.util.CommonConstant;

import java.util.ArrayList;
import java.util.List;

public class Users {

    public static User createDefaultUser(){
        return new User( -666, "You don't login", "****", null);
    }
    public static ArrayList<User> createUserArrayList(List<User> list){

        ArrayList<User> users = new ArrayList<User>(list){

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

        return users;
    }

}
