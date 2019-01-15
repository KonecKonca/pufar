package com.kozitski.pufar.validation.util;

import com.kozitski.pufar.validation.validator.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * A factory for creating Validator objects.
 */
class ValidatorFactory {
    
    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(ValidatorFactory.class);

    /**
     * Instantiates a new validator factory.
     */
    private ValidatorFactory() { }

    /**
     * Gets the active validators.
     *
     * @param names the names
     * @param isActive the is active
     * @return the active validators
     */
    static ArrayList<Validator> getActiveValidators(List<Object> names, List<Object> isActive){

        List<String> stringKeys = new ArrayList<>();
        for(Object o : names){
            stringKeys.add(o.toString());
        }

        List<String> stringPaths = new ArrayList<>();
        for(Object o : isActive){
            stringPaths.add(o.toString());
        }


        ArrayList<Validator> activeValidators = new ArrayList<>();

        for(int i = 0; i < stringPaths.size(); i++){

            try {
                Validator currentValidator = (Validator) Class.forName(stringPaths.get(i)).newInstance();
                activeValidators.add(currentValidator);
            }
            catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
                LOGGER.warn("Validator is not founded (" +  stringKeys.get(i) + " : " + stringPaths.get(i) + ")");
            }

        }

        LOGGER.info("ARE ACTIVE next validators: " + activeValidators);

        return activeValidators;
    }

}
