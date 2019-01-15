package com.kozitski.pufar.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

/**
 * The Class LoadImageNameGenerator.
 * Save loaded image in temporal directory
 */
public class LoadImageNameGenerator {
    
    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(LoadImageNameGenerator.class);

    /** The lock. */
    private static  ReentrantLock lock = new ReentrantLock();
    
    /** The Constant FILE_NAME_PREFIX. */
    private static final String FILE_NAME_PREFIX = "loadImage";
    
    /** The Constant FILE_NAME_POSTFIX. */
    private static final String FILE_NAME_POSTFIX = ".jpg";

    /** The Constant TOTAL_CAPACITY. */
    private static final int TOTAL_CAPACITY = 100;
    
    /** The name counter. */
    private static AtomicInteger nameCounter = new AtomicInteger(0);

    /**
     * Instantiates a new load image name generator.
     */
    private LoadImageNameGenerator() {}

    /**
     * Generate name.
     *
     * @return the string
     */
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
    
    /**
     * Gets the last name.
     *
     * @return the last name
     */
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
