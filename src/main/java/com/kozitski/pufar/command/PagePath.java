package com.kozitski.pufar.command;

public enum PagePath {

    PUFAR_CONTROLLER("/pufar"),
    PUFAR_INDEX("/"),

    INDEX_PAGE("/index.jsp"),
    CHAT_PAGE("/jsp/chat/chat.jsp"),
    USER_ERROR_PAGE("/jsp/error/userErrorPage.jsp"),
    ADMIN_ERROR_PAGE("/jsp/error/errorPage.jsp"),
    LOGIN_PAGE("/jsp/login/login.jsp"),
    TEMPLATE_PAGE("/jsp/template/template.jsp"),
    CREATE_NOTIFICATION("/jsp/create/createNotification.jsp"),
    NOTIFICATION_ADDITIONAL("/jsp/notificationAdditional.jsp"),
    PROFILE_PAGE("/jsp/profile/profile.jsp"),

    // admin
    ADMIN_CONTROL_PANEL("/jsp/admin/adminControlPanel.jsp"),
    ADMIN_SEARCH_NOTIFICATION("/jsp/admin/search/notificationSearch.jsp"),
    ADMIN_SEARCH_USER("/jsp/admin/search/userSearch.jsp");

    String jspPath;

    PagePath(String jspPath) {
        this.jspPath = jspPath;
    }
    public String getJspPath() {
        return jspPath;
    }

}
