package com.kozitski.pufar.service.number;

import com.kozitski.pufar.dao.number.NumberDao;
import com.kozitski.pufar.entity.number.MobilPhoneNumber;
import com.kozitski.pufar.exception.PufarDAOException;
import com.kozitski.pufar.exception.PufarServiceException;
import com.kozitski.pufar.exception.PufarValidationException;
import com.kozitski.pufar.service.AbstractService;
import com.kozitski.pufar.service.InjectDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NumberServiceImpl extends AbstractService implements NumberService {
    private static final Logger LOGGER = LoggerFactory.getLogger(NumberServiceImpl.class);

    @InjectDao
    private NumberDao numberDao;

    @Override
    public void changeMobilePhoneById(long userId, MobilPhoneNumber mobilPhoneNumber, boolean isNumberExist) throws PufarServiceException, PufarValidationException {

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
