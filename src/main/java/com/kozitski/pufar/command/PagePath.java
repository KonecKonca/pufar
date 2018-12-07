package com.kozitski.pufar.command;

public enum PagePath {

    INDEX_PAGE("index.jsp"),
    CHAT_PAGE("jsp/chat/chat.jsp"),
    USER_ERROR_PAGE("jsp/error/userErrorPage.jsp"),
    ADMIN_ERROR_PAGE("jsp/error/errorPage.jsp"),
    LOGIN_PAGE("jsp/login/login.jsp"),
    TEMPLATE_PAGE("jsp/template/template.jsp"),


    INFO_PAGE("jsp/infoPage.jsp"),
    PROFILE_PAGE("jsp/profile/profile.jsp");

    String jspPath;

    PagePath(String jspPath) {
        this.jspPath = jspPath;
    }
    public String getJspPath() {
        return jspPath;
    }

}
