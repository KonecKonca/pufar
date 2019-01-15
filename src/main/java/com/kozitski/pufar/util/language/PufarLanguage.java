package com.kozitski.pufar.util.language;

import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * The Class PufarLanguage.
 * Custom Wrapper under ResourceBundle.
 */
public class PufarLanguage {
    
    /** The Constant RU_LOCALE. */
    private static final Locale RU_LOCALE = new Locale("ru", "RU");
    
    /** The Constant RU_BUNDLE. */
    private static final ResourceBundle RU_BUNDLE = ResourceBundle.getBundle("resource", RU_LOCALE);
    
    /** The Constant EN_LOCALE. */
    private static final Locale EN_LOCALE = new Locale("en", "GB");
    
    /** The Constant EN_BUNDLE. */
    private static final ResourceBundle EN_BUNDLE = ResourceBundle.getBundle("resource", EN_LOCALE);

    /** The current language. */
    private PufarLanguageType currentLanguage = PufarLanguageType.RU;


    /**
     * Gets the value.
     *
     * @param key the key
     * @return the value
     */
    public String getValue(String key){
        return getValue(key, currentLanguage);
    }
    
    /**
     * Gets the value.
     *
     * @param key the key
     * @param languageType the language type
     * @return the value
     */
    public String getValue(String key, PufarLanguageType languageType){
        String value = "";

        if(languageType != null && languageType.equals(PufarLanguageType.EN)){
            value = EN_BUNDLE.getString(key);
        }
        else if(languageType != null && languageType.equals(PufarLanguageType.RU)){
            value = new String(RU_BUNDLE.getString(key).getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
        }

        return value;
    }

    /**
     * Gets the current language.
     *
     * @return the current language
     */
    public PufarLanguageType getCurrentLanguage() {
        return currentLanguage;
    }
    
    /**
     * Sets the current language.
     *
     * @param currentLanguage the new current language
     */
    public void setCurrentLanguage(PufarLanguageType currentLanguage) {
        this.currentLanguage = currentLanguage;
    }



}
