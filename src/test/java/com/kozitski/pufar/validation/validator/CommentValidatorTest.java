package com.kozitski.pufar.validation.validator;

import com.kozitski.pufar.entity.comment.NotificationComment;
import com.kozitski.pufar.exception.PufarValidationException;
import com.kozitski.pufar.validation.annotation.AspectValid;
import com.kozitski.pufar.validation.annotation.comment.CommentValid;
import com.kozitski.pufar.validation.util.ValidatorRegister;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Date;

public class CommentValidatorTest {

    @BeforeClass
    public void initialize(){
        ValidatorRegister.setFullPath("src/main/resources/validation/validator.properties");
    }

    @Test(dataProvider = "falseTestData",expectedExceptions = PufarValidationException.class)
    public void commentValidatorTestFalse(NotificationComment comment){
        commentTestMethod(comment);
    }
    @DataProvider(name = "falseTestData")
    public Object[][] falseTestData(){
        return new Object[][]{
                {new NotificationComment(1, null, null, null)},
                {new NotificationComment(1, "11111", null, null)},
                {new NotificationComment(1, "11111", null, new Date())},

                {new NotificationComment(1, "11111", "11", null)},
                {new NotificationComment(1, "1111", "11erregrgweweweweweweg111", null)},
                {new NotificationComment(1, "1111", "11errweg111", null)},

                {new NotificationComment(1, "<script>", "11errweg111", null)},
                {new NotificationComment(1, "1111", "<script>alert();</script>", null)},

        };
    }

    @Test(dataProvider = "trueTestData")
    public void commentValidatorTestTrue(NotificationComment comment){
        commentTestMethod(comment);
    }
    @DataProvider(name = "trueTestData")
    public Object[][] trueTestData(){
        return new Object[][]{
                {new NotificationComment(1, "petia", "xxxxx xx", null)},
                {new NotificationComment(34435, "vasia", "xxxxxxx", null)},
                {new NotificationComment(2343, "kolia", "1111ef", null)},
                {new NotificationComment(43334, "kostus", "wdwdew", null)},
                {new NotificationComment(3431, "igor", "grgww", null)},
        };
    }

    @AspectValid
    private void commentTestMethod(@CommentValid(minMessageSize = 5, maxMessageSize = 10) NotificationComment comment){ }

}