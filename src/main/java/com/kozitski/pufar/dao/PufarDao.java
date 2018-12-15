package com.kozitski.pufar.dao;

import com.kozitski.pufar.entity.notification.Notification;
import com.kozitski.pufar.entity.notification.NotificationParameter;

import java.util.ArrayList;
import java.util.Optional;

public interface PufarDao<T, P> {

    Optional<T> searchById(long id);
    ArrayList<T> searchByParameters(P parameters);

}
