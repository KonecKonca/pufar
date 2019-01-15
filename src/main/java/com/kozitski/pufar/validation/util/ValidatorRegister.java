package com.kozitski.pufar.validation.util;

import com.kozitski.pufar.exception.PufarValidationException;
import com.kozitski.pufar.util.path.WebPathReturner;
import com.kozitski.pufar.validation.validator.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

/**
 * The Class ValidatorRegister.
 * Activate validators.
 */
public class ValidatorRegister {
    
    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(ValidatorRegister.class);
    
    /** The Constant VALIDATOR_CONFIG_WEB_PATH. */
    private static final String VALIDATOR_CONFIG_WEB_PATH = "/WEB-INF/classes/validation/validator.properties";

    /** The full path. */
    private static String fullPath = WebPathReturner.webPath + VALIDATOR_CONFIG_WEB_PATH;

    /**
     * Instantiates a new validator register.
     */
    private ValidatorRegister(){}

    /**
     * Inits the validators.
     *
     * @return the list
     * @throws PufarValidationException the pufar validation exception
     */
    public static List<Validator> initValidators() throws PufarValidationException {

        Properties properties = new Properties();

        try(FileInputStream fileInputStream = new FileInputStream(fullPath)) {
            properties.load(fileInputStream);
        }
        catch (IOException e) {
            LOGGER.error("Properties file with validation config was not founded", e);
            throw new PufarValidationException("Properties file with validation config was not founded", e);
        }

        ArrayList<Object> keys = new ArrayList<>(properties.keySet());
        ArrayList<Object> values = new ArrayList<>(properties.values());

        return new ArrayList<>(ValidatorFactory.getActiveValidators(keys, values));
    }

    /**
     * Sets the full path.
     *
     * @param fullPath the new full path
     */
    public static void setFullPath(String fullPath) {
        ValidatorRegister.fullPath = fullPath;
    }

}
