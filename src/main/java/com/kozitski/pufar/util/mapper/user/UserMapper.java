package com.kozitski.pufar.util.mapper.user;

import com.kozitski.pufar.entity.user.User;
import com.kozitski.pufar.entity.user.UserStatus;
import com.kozitski.pufar.exception.PufarDAOException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


// TODO: Auto-generated Javadoc
/**
 * The Class UserMapper.
 */
public class UserMapper {
    
    /** The Constant USER_ID. */
    private static final String USER_ID = "user_id";
    
    /** The Constant USER_LOGIN. */
    private static final String USER_LOGIN = "login";
    
    /** The Constant USER_PASSWORD. */
    private static final String USER_PASSWORD = "password";
    
    /** The Constant USER_STATUS. */
    private static final String USER_STATUS = "status";
    
    /** The Constant BAN. */
    private static final String BAN = "isBanned";

    /**
     * Instantiates a new user mapper.
     */
    private UserMapper() { }

    /**
     * Creates the user.
     *
     * @param resultSet the result set
     * @return the user
     * @throws PufarDAOException the pufar DAO exception
     */
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
    
    /**
     * Creates the users.
     *
     * @param resultSet the result set
     * @return the list
     * @throws PufarDAOException the pufar DAO exception
     */
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

    /**
     * Define user status.
     *
     * @param status the status
     * @return the user status
     */
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
