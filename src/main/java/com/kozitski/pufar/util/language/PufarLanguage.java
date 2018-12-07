package com.kozitski.pufar.util.language;

import java.nio.charset.StandardCharsets;
import java.util.*;

public class PufarLanguage {
    private static final Locale RU_LOCALE = new Locale("ru", "RU");
    private static final ResourceBundle RU_BUNDLE = ResourceBundle.getBundle("resource", RU_LOCALE);
    private static final Locale EN_LOCALE = new Locale("en", "GB");
    private static final ResourceBundle EN_BUNDLE = ResourceBundle.getBundle("resource", EN_LOCALE);

    private PufarLanguageType currentLanguage = PufarLanguageType.RU;


    public String getValue(String key){
        String value = "";

        if(currentLanguage != null && currentLanguage.equals(PufarLanguageType.EN)){
            value = EN_BUNDLE.getString(key);
        }
        else if(currentLanguage != null && currentLanguage.equals(PufarLanguageType.RU)){
            value = new String(RU_BUNDLE.getString(key).getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
        }

        return value;
    }
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

    public PufarLanguageType getCurrentLanguage() {
        return currentLanguage;
    }
    public void setCurrentLanguage(PufarLanguageType currentLanguage) {
        this.currentLanguage = currentLanguage;
    }



}
