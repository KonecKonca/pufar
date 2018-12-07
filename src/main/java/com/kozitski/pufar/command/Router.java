package com.kozitski.pufar.command;

public class Router {

    public enum RouteType{
        FORWARD, REDIRECT
    }

    private String pagePath;
    private RouteType routeType;

    public Router() {
        pagePath = PagePath.INDEX_PAGE.getJspPath();
        routeType = RouteType.FORWARD;
    }

    public String getPagePath() {
        return pagePath;
    }
    public void setPagePath(String pagePath) {
        this.pagePath = pagePath;
    }
    public RouteType getRouteType() {
        return routeType;
    }
    public void setRouteType(RouteType routeType) {
        if(routeType != null){
            this.routeType = routeType;
        }
    }

    public boolean isForward(){
        return routeType.getClass() == RouteType.FORWARD.getClass();
    }

}
