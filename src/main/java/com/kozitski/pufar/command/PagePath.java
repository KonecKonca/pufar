package com.kozitski.pufar.command;

/**
 * The enum which contains all present
 * paths in application
 */
public enum PagePath {

    /**
     * Pufar controller page path.
     */
    PUFAR_CONTROLLER("/pufar"),
    /**
     * Pufar index page path.
     */
    PUFAR_INDEX("/"),

    /**
     * Index page page path.
     */
    INDEX_PAGE("/index.jsp"),
    /**
     * Chat page page path.
     */
    CHAT_PAGE("/jsp/chat/chat.jsp"),
    /**
     * User error page page path.
     */
    USER_ERROR_PAGE("/jsp/error/userErrorPage.jsp"),
    /**
     * Admin error page page path.
     */
    ADMIN_ERROR_PAGE("/jsp/error/errorPage.jsp"),
    /**
     * Login page page path.
     */
    LOGIN_PAGE("/jsp/login/login.jsp"),
    /**
     * Template page page path.
     */
    TEMPLATE_PAGE("/jsp/template/template.jsp"),
    /**
     * Create notification page path.
     */
    CREATE_NOTIFICATION("/jsp/create/createNotification.jsp"),
    /**
     * Notification additional page path.
     */
    NOTIFICATION_ADDITIONAL("/jsp/notificationAdditional.jsp"),
    /**
     * Profile page page path.
     */
    PROFILE_PAGE("/jsp/profile/profile.jsp"),

    /**
     * Admin control panel page path.
     */
    ADMIN_CONTROL_PANEL("/jsp/admin/adminControlPanel.jsp"),
    /**
     * Admin search notification page path.
     */
    ADMIN_SEARCH_NOTIFICATION("/jsp/admin/search/notificationSearch.jsp"),
    /**
     * Admin search user page path.
     */
    ADMIN_SEARCH_USER("/jsp/admin/search/userSearch.jsp");

    /**
     * The Jsp path.
     */
    String jspPath;

    PagePath(String jspPath) {
        this.jspPath = jspPath;
    }

    /**
     * Gets jsp path.
     *
     * @return the jsp path
     */
    public String getJspPath() {
        return jspPath;
    }

}
