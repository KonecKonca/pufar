package com.kozitski.pufar.service.number;

import com.kozitski.pufar.entity.number.MobilPhoneNumber;
import com.kozitski.pufar.exception.PufarServiceException;
import com.kozitski.pufar.exception.PufarValidationException;
import com.kozitski.pufar.validation.annotation.AspectValid;
import com.kozitski.pufar.validation.annotation.number.NumberValid;

public interface NumberService {

    @AspectValid
    void changeMobilePhoneById(long userId, @NumberValid MobilPhoneNumber mobilPhoneNumber, boolean isnumberExist) throws PufarServiceException, PufarValidationException;


}
