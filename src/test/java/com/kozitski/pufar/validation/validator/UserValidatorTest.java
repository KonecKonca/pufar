package com.kozitski.pufar.validation.validator;

import com.kozitski.pufar.entity.user.User;
import com.kozitski.pufar.entity.user.UserStatus;
import com.kozitski.pufar.exception.PufarValidationException;
import com.kozitski.pufar.validation.annotation.AspectValid;
import com.kozitski.pufar.validation.annotation.user.UserValid;
import com.kozitski.pufar.validation.util.ValidatorRegister;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class UserValidatorTest {


    @BeforeClass
    public void initialize(){
        ValidatorRegister.setFullPath("src/main/resources/validation/validator.properties");
    }

    @Test(dataProvider = "falseTestData",expectedExceptions = PufarValidationException.class)
    public void userValidatorTestFalse(User user){
        userTestMethod(user);
    }
    @DataProvider(name = "falseTestData")
    public Object[][] falseTestData(){
        return new Object[][]{
            {new User(23 ,"andsrei уауа ", "22", UserStatus.SUPER_ADMIN, true, null)},
            {new User(2354 ,"supervasieeeeeeeeeeeeeeeeeffefefefefefefefefeeeea", "33ef45", UserStatus.SIMPLE_USER, true, null)},
            {new User(523 ,"kolianchik", "33feefefefergegfrgrgrgrgrgrefefweeefeefe45", UserStatus.SIMPLE_USER, true, null)},
            {new User(2653 ,"alax", "", UserStatus.ADMIN, true, null)},
            {new User(3 ," _ ", "33efe45", UserStatus.SIMPLE_USER, true, null)},
            {new User(2 ,"энисинг Он Рашен", "fefewdff", UserStatus.SIMPLE_USER, true, null)},
        };
    }

    @Test(dataProvider = "trueTestData")
    public void userValidatorTestTrue(User user){
        userTestMethod(user);
    }
    @DataProvider(name = "trueTestData")
    public Object[][] trueTestData(){
        return new Object[][]{
                {new User(23 ,"andsrei", "334efe5", UserStatus.ADMIN, true, null)},
                {new User(2354 ,"supervasia", "33ef45", UserStatus.SUPER_ADMIN, true, null)},
                {new User(523 ,"kolianchik", "33fefe45", UserStatus.SIMPLE_USER, true, null)},
                {new User(2653 ,"alax1", "33erer45", UserStatus.SIMPLE_USER, true, null)},
                {new User(3 ,"akvar", "33efe45", UserStatus.SIMPLE_USER, true, null)},
                {new User(2 ,"энисингОнРашен", "fefewdff", UserStatus.SIMPLE_USER, true, null)},
        };
    }

    @AspectValid
    private void userTestMethod(@UserValid User user){ }


}