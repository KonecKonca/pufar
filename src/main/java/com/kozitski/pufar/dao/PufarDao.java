package com.kozitski.pufar.dao;

import java.util.List;
import java.util.Optional;

public interface PufarDao<T, P> {

    Optional<T> searchById(long id);

    List<T> searchByParameters(P parameters);

}
