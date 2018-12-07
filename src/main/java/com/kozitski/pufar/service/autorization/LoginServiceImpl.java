package com.kozitski.pufar.service.autorization;


import com.kozitski.pufar.dao.user.MySQLUserDao;
import com.kozitski.pufar.dao.user.UserDao;
import com.kozitski.pufar.entity.user.User;
import com.kozitski.pufar.entity.user.UserStatus;
import com.kozitski.pufar.util.encoder.PasswordEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

public class LoginServiceImpl implements LoginService {
    private static Logger LOGGER = LoggerFactory.getLogger(LoginServiceImpl.class);

    private UserDao userDao = new MySQLUserDao();

    @Override
    public Optional<User> searchUserByLoginPassword(String login, String password){

        String utf8Login = new String(login.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
        String utf8Password = new String(password.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);

        Optional<User> result = Optional.empty();

        Optional<User> user = userDao.searchUserByLogin(utf8Login);
        if(user.isPresent()){
            User currentUser = user.get();

            if(PasswordEncoder.comparePasswordsWithoutEncoding(PasswordEncoder.encode(utf8Password), currentUser.getPassword())){
                result = Optional.of(currentUser);
            }
        }

        return result;
    }

}
