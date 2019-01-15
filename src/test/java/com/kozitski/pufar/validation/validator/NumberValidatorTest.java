package com.kozitski.pufar.validation.validator;

import com.kozitski.pufar.entity.number.MobilPhoneNumber;
import com.kozitski.pufar.exception.PufarValidationException;
import com.kozitski.pufar.validation.annotation.AspectValid;
import com.kozitski.pufar.validation.annotation.number.NumberValid;
import com.kozitski.pufar.validation.util.ValidatorRegister;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class NumberValidatorTest {

    @BeforeClass
    public void initialize(){
        ValidatorRegister.setFullPath("src/main/resources/validation/validator.properties");
    }

    @Test(dataProvider = "falseTestData",expectedExceptions = PufarValidationException.class)
    public void numberValidatorTestFalse(MobilPhoneNumber number){
        numberTestMethod(number);
    }
    @DataProvider(name = "falseTestData")
    public Object[][] falseTestData(){
        return new Object[][]{
            {new MobilPhoneNumber(23, "3762", "23", "150317")},
            {new MobilPhoneNumber(3433, "365", "5", "6550317")},
            {new MobilPhoneNumber(23, "32", "24", "2555317")},
            {new MobilPhoneNumber(2334, "55", "23435", "9440317")},
            {new MobilPhoneNumber(523, "5ee5", "gefre", "3450317")},
            {new MobilPhoneNumber(4523, "6053", "24", "9557")},

            {new MobilPhoneNumber(23, null, null, null)},
            {new MobilPhoneNumber(3433, null, null, "6550317")},
            {new MobilPhoneNumber(23, null, "24", "2555317")},
            {new MobilPhoneNumber(2334, "55", null, "9440317")},
            {new MobilPhoneNumber(523, "5ee5", null, null)},
            {new MobilPhoneNumber(4523, "6053", "24", null)},
                {new MobilPhoneNumber(23, null, "34", null)},

            {new MobilPhoneNumber(3433, "", "", "")},
            {new MobilPhoneNumber(23, "", "", "2555317")},
            {new MobilPhoneNumber(2334, "", null, "9440317")},
            {new MobilPhoneNumber(523, "5ee5", "", "")},
            {new MobilPhoneNumber(4523, "6053", "24", "")},
        };
    }

    @Test(dataProvider = "trueTestData")
    public void numberValidatorTestTrue(MobilPhoneNumber number){
        numberTestMethod(number);
    }
    @DataProvider(name = "trueTestData")
    public Object[][] trueTestData(){
        return new Object[][]{
            {new MobilPhoneNumber(23, "376", "23", "1550317")},
            {new MobilPhoneNumber(3433, "365", "55", "6550317")},
            {new MobilPhoneNumber(23, "372", "24", "2555317")},
            {new MobilPhoneNumber(2334, "575", "25", "9440317")},
            {new MobilPhoneNumber(523, "535", "26", "3450317")},
            {new MobilPhoneNumber(4523, "605", "24", "9555317")},
        };
    }

    @AspectValid
    private void numberTestMethod(@NumberValid MobilPhoneNumber number){ }

}