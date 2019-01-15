package com.kozitski.pufar.dao.number;

import com.kozitski.pufar.dao.PufarDao;
import com.kozitski.pufar.entity.number.MobilPhoneNumber;
import com.kozitski.pufar.exception.PufarDAOException;

import java.util.List;
import java.util.Optional;

/**
 * The Interface NumberDao.
 */
public interface NumberDao extends PufarDao<MobilPhoneNumber, Object> {

    /**
     * Search by id.
     *
     * @param id the id
     * @return the optional
     */
    @Override
    Optional<MobilPhoneNumber> searchById(long id);

    /**
     * Search by parameters.
     *
     * @param parameters the parameters
     * @return the list
     */
    @Override
    List<MobilPhoneNumber> searchByParameters(Object parameters);

    /**
     * Update mobile phone by id.
     *
     * @param userId the user id
     * @param mobilPhoneNumber the mobil phone number
     * @throws PufarDAOException the pufar DAO exception
     */
    void updateMobilePhoneById(long userId, MobilPhoneNumber mobilPhoneNumber) throws PufarDAOException;

    /**
     * Insert mobile phone by id.
     *
     * @param userId the user id
     * @param mobilPhoneNumber the mobil phone number
     * @throws PufarDAOException the pufar DAO exception
     */
    void insertMobilePhoneById(long userId, MobilPhoneNumber mobilPhoneNumber) throws PufarDAOException;

}

