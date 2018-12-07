package com.kozitski.pufar.dao;

import java.util.Optional;

public interface PufarDao<T> {

    Optional<T> searchById(long id);

}
