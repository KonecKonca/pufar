package com.kozitski.pufar.filter;

import com.kozitski.pufar.command.PagePath;
import com.kozitski.pufar.entity.user.User;
import com.kozitski.pufar.entity.user.UserStatus;
import com.kozitski.pufar.util.CommonConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * The Class CheckAccessFilter.
 * Filter for checking user rights and
 * rights which necessary for go on any page
 */
@WebFilter(urlPatterns = "*.jsp")
public class CheckAccessFilter implements Filter {
    
    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(CheckAccessFilter.class);

    /** The admin pages. */
    private static Set<String> adminPages = new HashSet<>(Arrays.asList(PagePath.ADMIN_CONTROL_PANEL.getJspPath(), PagePath.ADMIN_ERROR_PAGE.getJspPath(),
            PagePath.ADMIN_SEARCH_NOTIFICATION.getJspPath(), PagePath.ADMIN_SEARCH_USER.getJspPath()));
    
    /** The not login pages. */
    private static Set<String> notLoginPages = new HashSet<>(Arrays.asList(PagePath.PUFAR_INDEX.getJspPath(), PagePath.PUFAR_CONTROLLER.getJspPath(),
            PagePath.INDEX_PAGE.getJspPath(), PagePath.LOGIN_PAGE.getJspPath(), PagePath.TEMPLATE_PAGE.getJspPath(),
            PagePath.NOTIFICATION_ADDITIONAL.getJspPath()));
    
    /** The all exist pages. */
    private static Set<String> allExistPages;
    static {
        PagePath[] paths = PagePath.values();
        allExistPages = new HashSet<>(paths.length);
        for(PagePath path : paths){
            allExistPages.add(path.getJspPath());
        }
    }

    /**
     * Do filter.
     *
     * @param req the req
     * @param resp the resp
     * @param filterChain the filter chain
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws ServletException the servlet exception
     */
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

    /**
     * Inits the.
     *
     * @param filterConfig the filter config
     * @throws ServletException the servlet exception
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException { }
    
    /**
     * Destroy.
     */
    @Override
    public void destroy() { }

}
