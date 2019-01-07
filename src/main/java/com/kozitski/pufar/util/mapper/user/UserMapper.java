package com.kozitski.pufar.util.mapper.user;

import com.kozitski.pufar.entity.user.User;
import com.kozitski.pufar.entity.user.UserStatus;
import com.kozitski.pufar.exception.PufarDAOException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class UserMapper {
    private static final String USER_ID = "user_id";
    private static final String USER_LOGIN = "login";
    private static final String USER_PASSWORD = "password";
    private static final String USER_STATUS = "status";
    private static final String BAN = "isBanned";

    private UserMapper() { }

    public static User createUser(ResultSet resultSet) throws PufarDAOException {

        try {

            long userId = resultSet.getLong(USER_ID);
            String login = resultSet.getString(USER_LOGIN);
            String password = resultSet.getString(USER_PASSWORD);
            UserStatus status = defineUserStatus(resultSet.getString(USER_STATUS));
            boolean isBanned = resultSet.getBoolean(BAN);

            return new User(userId, login, password, status, isBanned, null);
        }
        catch (SQLException e){
            throw new PufarDAOException("User is not created", e);
        }



    }
    public static List<User> createUsers(ResultSet resultSet) throws PufarDAOException {

        try {
            ArrayList<User> result = new ArrayList<>();

            while (resultSet.next()){

                long userId = resultSet.getLong(USER_ID);

                String login = resultSet.getString(USER_LOGIN);
                String password = resultSet.getString(USER_PASSWORD);
                UserStatus status = defineUserStatus(resultSet.getString(USER_STATUS));
                boolean isBanned = resultSet.getBoolean(BAN);

                result.add(new User(userId, login, password, status, isBanned, null));
            }

            return result;
        }
        catch (SQLException e){
            throw new PufarDAOException("Users are not created", e);
        }

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
