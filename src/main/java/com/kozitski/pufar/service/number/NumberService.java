package com.kozitski.pufar.service.number;

import com.kozitski.pufar.entity.number.MobilPhoneNumber;
import com.kozitski.pufar.exception.PufarServiceException;
import com.kozitski.pufar.validation.annotation.AspectValid;

public interface NumberService {

//    @AspectValid
    void changeMobilePhoneById(long userId, /* ... */ MobilPhoneNumber mobilPhoneNumber, boolean isnumberExist) throws PufarServiceException;


}
