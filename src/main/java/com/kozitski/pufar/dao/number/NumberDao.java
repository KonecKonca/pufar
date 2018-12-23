package com.kozitski.pufar.dao.number;

import com.kozitski.pufar.dao.PufarDao;
import com.kozitski.pufar.entity.number.MobilPhoneNumber;

import java.util.ArrayList;
import java.util.Optional;

public interface NumberDao extends PufarDao<MobilPhoneNumber, Object> {

    @Override
    Optional<MobilPhoneNumber> searchById(long id);
    @Override
    ArrayList<MobilPhoneNumber> searchByParameters(Object parameters);

}
