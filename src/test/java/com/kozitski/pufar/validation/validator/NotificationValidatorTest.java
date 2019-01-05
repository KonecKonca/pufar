package com.kozitski.pufar.validation.validator;

import com.kozitski.pufar.entity.comment.NotificationComment;
import com.kozitski.pufar.entity.message.UserMessage;
import com.kozitski.pufar.entity.notification.Notification;
import com.kozitski.pufar.entity.notification.UnitType;
import com.kozitski.pufar.exception.PufarValidationException;
import com.kozitski.pufar.validation.annotation.AspectValid;
import com.kozitski.pufar.validation.annotation.message.MessageValid;
import com.kozitski.pufar.validation.annotation.notification.NotificationValid;
import com.kozitski.pufar.validation.util.ValidatorRegister;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import static org.testng.Assert.*;

public class NotificationValidatorTest {


    @BeforeClass
    public void initialize(){
        ValidatorRegister.setFullPath("src/main/resources/validation/validator.properties");
    }

    @Test(dataProvider = "falseTestData",expectedExceptions = PufarValidationException.class)
    public void notificationValidatorTestFalse(Notification notification){
        notificationTestMethod(notification);
    }
    @DataProvider(name = "falseTestData")
    public Object[][] falseTestData(){
        return new Object[][]{
            {new Notification()},
            {new Notification(23, "", UnitType.OTHER, 34.4f, 334, null, null,
                    new ArrayList<>(Arrays.asList(new NotificationComment(234, "34343", "erere", null))))},
            {new Notification(253, "привет как делишкиefefefefgefe efe" +
                    "eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeefeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeefeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeefee" +
                    "eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeefeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeefeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeefe" +
                    "eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeefeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeefeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeefeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeefee" +
                    " fefe ffe", UnitType.ELECTRONICS, 34.4d, 34, new Date(), null, new ArrayList<>())},

            {new Notification(3423, "efefef", null, -4.4d, 34, new Date(), null, new ArrayList<>())},
            {new Notification(3423, "efefef", null, 455555.4d, 34, new Date(), null, new ArrayList<>())},

        };
    }

    @Test(dataProvider = "trueTestData")
    public void messageValidatorTestTrue(Notification notification){
        notificationTestMethod(notification);
    }
    @DataProvider(name = "trueTestData")
    public Object[][] trueTestData(){
        return new Object[][]{
            {new Notification(23, "efefe", UnitType.ELECTRONICS, 34.4f, 334, null, null,
                    new ArrayList<>(Arrays.asList(new NotificationComment(234, "34343", "erere", null))))},
            {new Notification(253, "привет как делишки", UnitType.AUTO, 34.4d, 34, new Date(), null, new ArrayList<>())},
            {new Notification(3423, "hi", UnitType.BEAUTY, 4.4d, 34, new Date(), null, new ArrayList<>())},
            {new Notification(63, "all is ok", UnitType.OTHER, 1.4d, 34, null, null, new ArrayList<>())},
            {new Notification(234, "nice to see u", UnitType.CHILD, 34.4f, 34, new Date(), null, new ArrayList<>())},
        };
    }


    @AspectValid
    private void notificationTestMethod(@NotificationValid(minPrice = 1, maxPrice = 10_000) Notification notification){ }

}