package com.kozitski.pufar.validation.validator;

import com.kozitski.pufar.entity.message.UserMessage;
import com.kozitski.pufar.exception.PufarValidationException;
import com.kozitski.pufar.validation.annotation.AspectValid;
import com.kozitski.pufar.validation.annotation.message.MessageValid;
import com.kozitski.pufar.validation.util.ValidatorRegister;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Date;

public class MessageValidatorTest {

    @BeforeClass
    public void initialize(){
        ValidatorRegister.setFullPath("src/main/resources/validation/validator.properties");
    }

    @Test(dataProvider = "falseTestData",expectedExceptions = PufarValidationException.class)
    public void messageValidatorTestFalse(UserMessage comment){
        messageTestMethod(comment);
    }
    @DataProvider(name = "falseTestData")
    public Object[][] falseTestData(){
        return new Object[][]{
                {new UserMessage(null, new Date(), "andrei12", "vasiania")},
                {new UserMessage("как дела ", null, "vasiania", "andrei1232")},
                {new UserMessage("how are u", new Date(), null, "rxxx")},
                {new UserMessage("ok", new Date(), "werere", null)},

                {new UserMessage("", new Date(), "andrei12", "vasiania")},
                {new UserMessage("как дела ", null, "vasiania", "andrei1232")},
                {new UserMessage("how are u", new Date(), "", "rxxx")},
                {new UserMessage("ok", new Date(), "werere", "")},

                {new UserMessage("efefe", new Date(), "andrei12eeeeeeeeeeeeeeeeeeeeeeeeeeeeeee", "vasiania")},
                {new UserMessage("как дела ", new Date(), "e", "andrei1232")},
                {new UserMessage("how are u", new Date(), "", "rxeeeeeeeeeeeeeeeeeeeeeeeeexx")},
                {new UserMessage("oefefefk", new Date(), "wereeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeere", "")},

        };
    }

    @Test(dataProvider = "trueTestData")
    public void messageValidatorTestTrue(UserMessage comment){
        messageTestMethod(comment);
    }
    @DataProvider(name = "trueTestData")
    public Object[][] trueTestData(){
        return new Object[][]{
                {new UserMessage("efe3443f ", new Date(), "andrei12", "vasiania")},
                {new UserMessage("как дела ", new Date(), "vasiania", "andrei1232")},
                {new UserMessage("how are u", new Date(), "werere", "rxxx")},
                {new UserMessage("ok", new Date(), "werere", "reere")},
        };
    }

    @AspectValid
    private void messageTestMethod(@MessageValid UserMessage message){ }


}