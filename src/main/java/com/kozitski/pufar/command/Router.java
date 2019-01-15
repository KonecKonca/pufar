package com.kozitski.pufar.command;

// TODO: Auto-generated Javadoc
/**
 * The Class Router.
 */
public class Router {

    /** The Route type. */
    public enum RouteType{
        FORWARD, REDIRECT, RESPONSE_WRITE
    }

    /** The page path. */
    private String pagePath;
    
    /** The route type. */
    private RouteType routeType;

    /**
     * Instantiates a new router.
     */
    public Router() {
        pagePath = PagePath.INDEX_PAGE.getJspPath();
        routeType = RouteType.FORWARD;
    }

    /**
     * Gets the page path.
     *
     * @return the page path
     */
    public String getPagePath() {
        return pagePath;
    }
    
    /**
     * Sets the page path.
     *
     * @param pagePath the new page path
     */
    public void setPagePath(String pagePath) {
        this.pagePath = pagePath;
    }
    
    /**
     * Gets the route type.
     *
     * @return the route type
     */
    public RouteType getRouteType() {
        return routeType;
    }
    
    /**
     * Sets the route type.
     *
     * @param routeType the new route type
     */
    public void setRouteType(RouteType routeType) {
        if(routeType != null){
            this.routeType = routeType;
        }
    }

    /**
     * Checks if is forward.
     *
     * @return true, if is forward
     */
    public boolean isForward(){
        return routeType.equals(RouteType.FORWARD);
    }
    
    /**
     * Checks if is redirect.
     *
     * @return true, if is redirect
     */
    public boolean isRedirect(){ return routeType.equals(RouteType.REDIRECT); }

}
