package com.kozitski.pufar.service.number;

import com.kozitski.pufar.command.impl.profile.ChangeNumberCommand;
import com.kozitski.pufar.dao.number.MysqlNumberDao;
import com.kozitski.pufar.dao.number.NumberDao;
import com.kozitski.pufar.entity.number.MobilPhoneNumber;
import com.kozitski.pufar.exception.PufarDAOException;
import com.kozitski.pufar.exception.PufarServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NumberServiceImpl implements NumberService {
    private static Logger LOGGER = LoggerFactory.getLogger(NumberServiceImpl.class);

    private NumberDao numberDao = new MysqlNumberDao();

    @Override
    public void changeMobilePhoneById(long userId, MobilPhoneNumber mobilPhoneNumber, boolean isNumberExist) throws PufarServiceException {

        if(isNumberExist){
            try {
                numberDao.updateMobilePhoneById(userId, mobilPhoneNumber);
            }
            catch (PufarDAOException e) {
                throw new PufarServiceException(e);
            }
        }
        else {
            try {
                numberDao.insertMobilePhoneById(userId, mobilPhoneNumber);
            }
            catch (PufarDAOException e) {
                throw new PufarServiceException(e);
            }
        }

    }


}
