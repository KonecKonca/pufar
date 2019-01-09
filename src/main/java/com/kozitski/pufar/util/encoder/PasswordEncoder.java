package com.kozitski.pufar.util.encoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

// TODO: Auto-generated Javadoc
/**
 * The Class PasswordEncoder.
 */
public class PasswordEncoder {
    
    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(PasswordEncoder.class);
    
    /** The Constant ENCODING_TYPE. */
    private static final String ENCODING_TYPE = "MD5";
    
    /** The Constant STRING_FORMAT_CONSTANT. */
    private static final String STRING_FORMAT_CONSTANT = "%02X ";

    /**
     * Instantiates a new password encoder.
     */
    private PasswordEncoder() {
    }

    /**
     * Encode.
     *
     * @param password the password
     * @return the string
     */
    public static String encode(String password) {
        StringBuilder stringBuilder = new StringBuilder();

        try {

            MessageDigest md5 = MessageDigest.getInstance(ENCODING_TYPE);
            byte[] bytes = md5.digest(password.getBytes());

            for (byte b : bytes) {
                stringBuilder.append(String.format(STRING_FORMAT_CONSTANT, b));
            }

        } catch (NoSuchAlgorithmException e) {
            LOGGER.error("Can not encode password");
        }

        return stringBuilder.toString().trim();
    }

    /**
     * Compare passwords with encoding.
     *
     * @param pass1 the pass 1
     * @param pass2 the pass 2
     * @return true, if successful
     */
    public static boolean comparePasswordsWithEncoding(String pass1, String pass2) {
        String encodedPass1 = encode(pass1);
        String encodedPass2 = encode(pass2);
        return encodedPass1.equals(encodedPass2);
    }

    /**
     * Compare passwords without encoding.
     *
     * @param pass1 the pass 1
     * @param pass2 the pass 2
     * @return true, if successful
     */
    public static boolean comparePasswordsWithoutEncoding(String pass1, String pass2) {
        return pass1.trim().equals(pass2.trim());
    }


}
