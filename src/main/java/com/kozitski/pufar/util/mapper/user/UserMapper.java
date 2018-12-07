package com.kozitski.pufar.util.mapper.user;

import com.kozitski.pufar.entity.user.User;
import com.kozitski.pufar.entity.user.UserStatus;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class UserMapper {
    private static final String USER_ID = "user_id";
    private static final String USER_LOGIN = "login";
    private static final String USER_PASSWORD = "password";
    private static final String USER_VALUE = "value";
    private static final String USER_STATUS = "status";

    public static User createUser(ResultSet resultSet) throws SQLException {

        long userId = resultSet.getLong(USER_ID);
        String login = resultSet.getString(USER_LOGIN);
        String password = resultSet.getString(USER_PASSWORD);
        UserStatus status = defineUserStatus(resultSet.getString(USER_VALUE));

        return new User(userId, login, password, status);

    }
    public static ArrayList<User> createUsers(ResultSet resultSet) throws SQLException {
        ArrayList<User> result = new ArrayList<>();

        while (resultSet.next()){

            long userId = resultSet.getLong(USER_ID);
            String login = resultSet.getString(USER_LOGIN);
            String password = resultSet.getString(USER_PASSWORD);

            UserStatus status = defineUserStatus(resultSet.getString(USER_STATUS));

            result.add(new User(userId, login, password, status));
        }

        return result;

    }

    private static UserStatus defineUserStatus(String status){

        switch (status.toUpperCase()){

            case ConstantUserStatus.SIMPLE_USER:
                return UserStatus.SIMPLE_USER;

            case ConstantUserStatus.ADMIN:
                return UserStatus.ADMIN;

            case ConstantUserStatus.SUPER_ADMIN:
                return UserStatus.SUPER_ADMIN;

            default:
                return UserStatus.SIMPLE_USER;
        }

    }

}
