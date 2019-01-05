package com.kozitski.pufar.tag;

import com.kozitski.pufar.entity.user.User;
import com.kozitski.pufar.entity.user.UserStatus;
import com.kozitski.pufar.util.CommonConstant;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

@SuppressWarnings("serial")
public class AdminButtonTag extends TagSupport {
    private String value;

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public int doStartTag() throws JspException {

        if(checkAccess()){
            try {
                JspWriter out = pageContext.getOut();
                out.write("<form action=\"/pufar\" method=\"post\">");

                out.write("<input type=\"hidden\" name=\"command\" value=\"CONTROL_PANEL\"/>");
                out.write("<input type=\"submit\" value=" + value + " class=\"btn btn-outline-info\"/>");

                out.write("</form>");
            } catch (IOException e) {
                throw new JspException(e.getMessage());
            }
        }

        return SKIP_BODY;
    }

    @Override
    public int doEndTag() throws JspException {
        return EVAL_PAGE;
    }

    private boolean checkAccess(){
        boolean access = false;

        User user = (User) pageContext.getSession().getAttribute(CommonConstant.CURRENT_USER);
        if(user != null){
            UserStatus status = user.getStatus();
            if( status != null && (status.equals(UserStatus.ADMIN) || status.equals(UserStatus.SUPER_ADMIN))){
                access = true;
            }
        }

        return access;
    }

}
