package com.kozitski.pufar.service.number;

import com.kozitski.pufar.entity.number.MobilPhoneNumber;
import com.kozitski.pufar.exception.PufarServiceException;
import com.kozitski.pufar.exception.PufarValidationException;
import com.kozitski.pufar.validation.annotation.AspectValid;
import com.kozitski.pufar.validation.annotation.number.NumberValid;

/**
 * The Interface NumberService.
 */
public interface NumberService {

    /**
     * Change mobile phone by id.
     *
     * @param userId the user id
     * @param mobilPhoneNumber the mobil phone number
     * @param isnumberExist the isnumber exist
     * @throws PufarServiceException the pufar service exception
     * @throws PufarValidationException the pufar validation exception
     */
    @AspectValid
    void changeMobilePhoneById(long userId, @NumberValid MobilPhoneNumber mobilPhoneNumber, boolean isnumberExist) throws PufarServiceException, PufarValidationException;


}
