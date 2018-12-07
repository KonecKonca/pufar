package com.kozitski.pufar.validation;

import com.kozitski.pufar.entity.comment.NotificationComment;
import com.kozitski.pufar.entity.message.UserMessage;
import com.kozitski.pufar.entity.notification.Notification;
import com.kozitski.pufar.entity.notification.UnitType;
import com.kozitski.pufar.entity.user.User;
import com.kozitski.pufar.validation.annotation.AspectValid;
import com.kozitski.pufar.validation.annotation.comment.CommentValid;
import com.kozitski.pufar.validation.annotation.message.MessageValid;
import com.kozitski.pufar.validation.annotation.notification.NotificationValid;
import com.kozitski.pufar.validation.annotation.primitive.doouble.DoubleValid;
import com.kozitski.pufar.validation.annotation.primitive.integer.IntValid;
import com.kozitski.pufar.validation.annotation.primitive.string.StringValid;
import com.kozitski.pufar.validation.annotation.user.UserValid;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/forName")
public class AspectMainTest extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        ValidationTest test = new ValidationTest();


        // @StringValid not success
//        test.string("Any input1", null);

        // @DoubleValid not success
//        test.doouble(3.0, 4343343.0);


        // @IntValid not success
//        test.integer(9, 8);

        // @CommnetValid not success
//        test.comment( new NotificationComment(1, 1, "eefefe efscript>fefe"),
//                new NotificationComment(1, 1, "eefefe ef <script>fefe"));

        //@MessageValid not success
//        test.message(new UserMessage("efefef@!*", new Time(43654242), "efefeef", "23453535"));

//        @NotificationValid not success
        test.notification(new Notification(2, "3r43", UnitType.OTHER, 23.5, 343, null));

        //@UserValid not success
        test.user(new User());

    }

}

class ValidationTest{

    @AspectValid
    public void string(@StringValid(forbiddenValue = "Any input", maxLength = 23) String string1, @StringValid() String string2){
        System.out.println("==> string: " + string1 + " " + string2);
    }
    @AspectValid
    public void doouble(@DoubleValid Double double1, @DoubleValid(minValue = -1, maxValue = 27) Double double2){
        System.out.println("==> doouble: " + double1 + " " + double1);
    }
    @AspectValid
    public void integer(@IntValid int a, @IntValid(minValue = 4, maxValue = 7) int b){
        System.out.println("integer==> " + a + " " + b);
    }
    @AspectValid
    public void comment(@CommentValid NotificationComment comment1, @CommentValid NotificationComment comment2){
        System.out.println("integer==> " + comment1 + " " + comment2);
    }
    @AspectValid
    public void message(@MessageValid UserMessage message){
        System.out.println("integer==> " + message);
    }
    @AspectValid
    public void notification(@NotificationValid Notification notification){
        System.out.println("integer==> " + notification);
    }

    @AspectValid
    public void user(@UserValid User user){
        System.out.println("integer==> " + user);
    }

}
