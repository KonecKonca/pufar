package com.kozitski.pufar.validation.util;

import com.kozitski.pufar.exception.PufarValidationException;
import com.kozitski.pufar.util.path.WebPathReturner;
import com.kozitski.pufar.validation.validator.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;


public class ValidatorRegister {
    private static Logger LOGGER = LoggerFactory.getLogger(ValidatorRegister.class);
    private static final String VALIDATOR_CONFIG_PATH = "/WEB-INF/classes/validation/validator.properties";

    public static ArrayList<Validator> initValidators() throws PufarValidationException {

        String fullPath = (WebPathReturner.webPath + VALIDATOR_CONFIG_PATH);
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

}
