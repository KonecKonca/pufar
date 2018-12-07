package com.kozitski.pufar.util.encoder;

import com.kozitski.pufar.validation.annotation.AspectValid;
import com.kozitski.pufar.validation.annotation.primitive.string.StringValid;
import com.kozitski.pufar.validation.validator.CommentValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordEncoder {
    private static Logger LOGGER = LoggerFactory.getLogger(PasswordEncoder.class);
    private static final String ENCODING_TYPE = "MD5";

    @AspectValid
    public static String encode(String password){
        StringBuilder stringBuilder = new StringBuilder();

        try {

            MessageDigest md5 = MessageDigest.getInstance(ENCODING_TYPE);
            byte[] bytes = md5.digest(password.getBytes());

            for(byte b : bytes){
                stringBuilder.append(String.format("%02X ", b));
            }

        }
        catch (NoSuchAlgorithmException e) {
            LOGGER.error("Can not encode password");
        }

        return stringBuilder.toString();
    }

    public static boolean comparePasswordsWithEncoding(String pass1, String pass2){
        String encodedPass1 = encode(pass1);
        String encodedPass2 = encode(pass2);
        return encodedPass1.equals(encodedPass2);
    }
    public static boolean comparePasswordsWithoutEncoding(String pass1, String pass2){
        return pass1.trim().equals(pass2.trim());
    }


}
