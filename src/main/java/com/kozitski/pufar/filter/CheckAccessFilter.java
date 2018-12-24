package com.kozitski.pufar.filter;

import com.kozitski.pufar.command.PagePath;
import com.kozitski.pufar.entity.user.User;
import com.kozitski.pufar.entity.user.UserStatus;
import com.kozitski.pufar.util.CommonConstant;
import com.kozitski.pufar.util.language.PufarLanguage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@WebFilter(urlPatterns = "*.jsp")
public class CheckAccessFilter implements Filter {
    private static Logger LOGGER = LoggerFactory.getLogger(CheckAccessFilter.class);

    private static Set<String> adminPages = new HashSet<>(Arrays.asList(PagePath.ADMIN_CONTROL_PANEL.getJspPath(), PagePath.ADMIN_ERROR_PAGE.getJspPath(),
            PagePath.ADMIN_SEARCH_NOTIFICATION.getJspPath(), PagePath.ADMIN_SEARCH_USER.getJspPath()));
    private static Set<String> notLoginPages = new HashSet<>(Arrays.asList(PagePath.PUFAR_INDEX.getJspPath(), PagePath.PUFAR_CONTROLLER.getJspPath(),
            PagePath.INDEX_PAGE.getJspPath(), PagePath.LOGIN_PAGE.getJspPath(), PagePath.TEMPLATE_PAGE.getJspPath(),
            PagePath.NOTIFICATION_ADDITIONAL.getJspPath()));
    private static Set<String> allExistPages;
    static {
        PagePath[] paths = PagePath.values();
        allExistPages = new HashSet<>(paths.length);
        for(PagePath path : paths){
            allExistPages.add(path.getJspPath());
        }
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        String path = request.getRequestURI();

        User currentUser = (User) request.getSession().getAttribute(CommonConstant.CURRENT_USER);

        if(notLoginPages.contains(path)){
            filterChain.doFilter(req, resp);
        }
        else if(adminPages.contains(path) && currentUser!=null &&
                (currentUser.getStatus().equals(UserStatus.ADMIN) || currentUser.getStatus().equals(UserStatus.SUPER_ADMIN))){
            filterChain.doFilter(req, resp);
        }
        else if(allExistPages.contains(path) && !adminPages.contains(path)
                && currentUser!= null && currentUser.getStatus()!=null){
            filterChain.doFilter(req, resp);
        }
        else {
            LOGGER.warn("User has not access");
            ((HttpServletResponse)resp).sendRedirect(PagePath.INDEX_PAGE.getJspPath());
        }

    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }
    @Override
    public void destroy() {
    }

}
