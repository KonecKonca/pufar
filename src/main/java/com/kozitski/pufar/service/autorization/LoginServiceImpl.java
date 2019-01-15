package com.kozitski.pufar.service.autorization;


import com.kozitski.pufar.dao.number.NumberDao;
import com.kozitski.pufar.dao.user.UserDao;
import com.kozitski.pufar.entity.number.MobilPhoneNumber;
import com.kozitski.pufar.entity.user.User;
import com.kozitski.pufar.exception.PufarValidationException;
import com.kozitski.pufar.service.AbstractService;
import com.kozitski.pufar.service.InjectDao;
import com.kozitski.pufar.util.encoder.PasswordEncoder;

import java.util.Optional;

// TODO: Auto-generated Javadoc
/**
 * The Class LoginServiceImpl.
 */
public class LoginServiceImpl extends AbstractService implements LoginService {

    /** The user dao. */
    @InjectDao
    private UserDao userDao;
    
    /** The number dao. */
    @InjectDao
    private NumberDao numberDao;

    /**
     * Search user by login password.
     *
     * @param login the login
     * @param password the password
     * @return the optional
     * @throws PufarValidationException the pufar validation exception
     */
    @Override
    public Optional<User> searchUserByLoginPassword(String login, String password) throws PufarValidationException {
        Optional<User> result = Optional.empty();

        Optional<User> user = userDao.searchUserByLogin(login);
        if (user.isPresent()) {
            User currentUser = user.get();

            if (PasswordEncoder.comparePasswordsWithoutEncoding(PasswordEncoder.encode(password), currentUser.getPassword())) {
                result = Optional.of(currentUser);
            }

            Optional<MobilPhoneNumber> mobilPhoneNumber = numberDao.searchById(currentUser.getUserId());
            if (mobilPhoneNumber.isPresent()) {
                MobilPhoneNumber findNumber = mobilPhoneNumber.get();
                currentUser.setNumber(findNumber);
            }
        }

        return result;
    }

}
