package com.kozitski.pufar.entity.user;

import java.util.ArrayList;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Class Users.
 */
public class Users {

    /**
     * Instantiates a new users.
     */
    private Users() { }

    /**
     * Creates the default user.
     *
     * @return the user
     */
    public static User createDefaultUser(){
        return new User( -666, "", "****", null, false, null);
    }
    
    /**
     * Creates the user array list.
     *
     * @param list the list
     * @return the list
     */
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
    
    /**
     * Status priority.
     *
     * @param userStatus the user status
     * @return the int
     */
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

    /**
     * Check access right.
     *
     * @param currentUser the current user
     * @param foundUser the found user
     * @return true, if successful
     */
    public static boolean checkAccessRight(User currentUser, User foundUser){
        return ((foundUser.getStatus().equals(UserStatus.SIMPLE_USER) && currentUser.getStatus().equals(UserStatus.ADMIN))
                    || ((currentUser.getStatus().equals(UserStatus.SUPER_ADMIN)) &&
                (foundUser.getStatus().equals(UserStatus.SIMPLE_USER) || foundUser.getStatus().equals(UserStatus.ADMIN))));
    }

}
