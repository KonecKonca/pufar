package com.kozitski.pufar.util.encoder;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class PasswordEncoderTest {

    @Test(dataProvider = "testEncodeMD5Data")
    public void testEncodeMD5(String actual, String beforeEncode) {
        String expected = PasswordEncoder.encode(beforeEncode);
        assertEquals(actual, expected);
    }
    @DataProvider(name = "testEncodeMD5Data")
    public Object[][] testEncodeMD5Data(){
        return new Object[][]{
                {"A1 4D D5 09 22 4F 63 59 CA EE D0 E0 01 7B 3A FA", "123krgfr"},
                {"09 05 D2 1E AB 94 F3 D6 D1 81 40 07 B8 9B 7F E8", "superPassword34"},
                {"EE 95 A1 6D 76 3A B0 D2 6E E6 2C 53 05 6D F9 28", "parol"},
                {"C4 49 C3 AE BA 62 97 03 B7 F6 82 D6 27 6F 6F E2", "паукуапуа34"},
                {"89 8E 53 CD 1C 04 58 22 E0 42 EE 46 73 BB D9 FD", "_2а3_"},
                {"1C CE BB 24 9F D7 60 2D 85 46 00 3C DE F6 44 EE", "1723уваду"},
                {"67 2D 37 E9 38 C9 22 B6 F8 C3 C6 F5 25 84 9F E2", "парльпарль"},
        };
    }

    @Test(dataProvider = "comparePasswordsWithEncodingDataTrue")
    public void testTrueComparePasswordsWithEncoding(String password1, String password2) {
        boolean actual = true;
        boolean expected = PasswordEncoder.comparePasswordsWithoutEncoding(password1, password2);

        assertEquals(actual, expected);
    }
    @DataProvider(name = "comparePasswordsWithEncodingDataTrue")
    public Object[][] comparePasswordsWithEncodingDataTrue(){
        return new Object[][]{
                {"123krgfr", "123krgfr"},
                {"superPassword34", "superPassword34"},
                {"parol", "parol"},
                {"паукуапуа34", "паукуапуа34"},
                {"_2а3_", "_2а3_"},
                {"1723уваду", "1723уваду"},
                {"парльпарль", "парльпарль"},
        };
    }

    @Test(dataProvider = "comparePasswordsWithEncodingDataFalse")
    public void testFalseComparePasswordsWithEncoding(String password1, String password2) {
        boolean actual = false;
        boolean expected = PasswordEncoder.comparePasswordsWithoutEncoding(password1, password2);

        assertEquals(actual, expected);
    }
    @DataProvider(name = "comparePasswordsWithEncodingDataFalse")
    public Object[][] comparePasswordsWithEncodingDataFalse(){
        return new Object[][]{
                {"123krgf3r", "123krgfr"},
                {"superPaSSword34", "superPassword34"},
                {"parol", "par0l"},
                {"паукуапуаЗ4", "паукуапуа34"},
                {"_2а3_", "-2а3_"},
                {"1723уваду", "1723Уваду"},
                {"парльпарль", "пaрльпарль"},
        };
    }

}
