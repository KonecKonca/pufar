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

// TODO: Auto-generated Javadoc
/**
 * The Class NumberServiceImpl.
 */
public class NumberServiceImpl extends AbstractService implements NumberService {

    /** The number dao. */
    @InjectDao
    private NumberDao numberDao;

    /**
     * Change mobile phone by id.
     *
     * @param userId the user id
     * @param mobilPhoneNumber the mobil phone number
     * @param isNumberExist the is number exist
     * @throws PufarServiceException the pufar service exception
     * @throws PufarValidationException the pufar validation exception
     */
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
