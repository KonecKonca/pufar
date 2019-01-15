package com.kozitski.pufar.dao;

import java.util.List;
import java.util.Optional;

/**
 * The Interface PufarDao.
 *
 * @param <T> the generic type
 * @param <P> the generic type
 */
public interface PufarDao<T, P> {

    /**
     * Search by id.
     *
     * @param id the id
     * @return the optional
     */
    Optional<T> searchById(long id);

    /**
     * Search by parameters.
     *
     * @param parameters the parameters
     * @return the list
     */
    List<T> searchByParameters(P parameters);

}
