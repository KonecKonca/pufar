package com.kozitski.pufar.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class LoadImageNameGenerator {
    private static Logger LOGGER = LoggerFactory.getLogger(LoadImageNameGenerator.class);

    private static  ReentrantLock lock = new ReentrantLock();
    private static final String FILE_NAME_PREFIX = "loadImage";
    private static final String FILE_NAME_POSTFIX = ".jpg";

    private static final int TOTAL_CAPACITY = 100;
    private static AtomicInteger nameCounter = new AtomicInteger(0);

    private LoadImageNameGenerator() {}

    public static String generateName(){

        try {
            lock.lock();
            if(nameCounter.get() >= TOTAL_CAPACITY){
                nameCounter = new AtomicInteger(0);
            }
            return FILE_NAME_PREFIX + nameCounter.incrementAndGet() + FILE_NAME_POSTFIX;
        }
        finally {
            LOGGER.info("File name generated");
            lock.unlock();
        }

    }
    public static String getLastName(){

        try {
            lock.lock();
            return FILE_NAME_PREFIX + nameCounter.get() + FILE_NAME_POSTFIX;
        }
        finally {
            lock.unlock();
        }

    }

}
