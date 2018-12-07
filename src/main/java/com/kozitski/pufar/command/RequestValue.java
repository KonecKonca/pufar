package com.kozitski.pufar.command;

import java.util.HashMap;
import java.util.Map;

public class RequestValue {

    private Map<String, Object> servletContext;
    private Map<String, Object> servletSession;
    private Map<String, Object> requestAttribute;
    private Map<String, String> requestParameter;

    public RequestValue() {
    }
    public RequestValue(Map<String, Object> servletContext, Map<String, Object> servletSession,
                        Map<String, Object> requestAttribute, Map<String, String> requestParameter) {
        this.servletContext = servletContext;
        this.servletSession = servletSession;
        this.requestAttribute = requestAttribute;
        this.requestParameter = requestParameter;
    }

    public Object servletContextPut(String key, Object value) {
        servletContext.remove(key);
        return servletContext.put(key, value);
    }
    public Object servletSessionPut(String key, Object value) {
        servletSession.remove(key);
        return servletSession.put(key, value);
    }
    public Object requestAttributePut(String key, Object value) {
        requestAttribute.remove(key);
        return requestAttribute.put(key, value);
    }
    public Object requestParameterPut(String key, String value) {
        requestParameter.remove(key);
        return requestParameter.put(key, value);
    }

    public Object servletContextGet(String key) {
        return servletContext.get(key);
    }
    public Object servletSessionGet(String key) {
        return servletSession.get(key);
    }
    public Object requestAttributeGet(String key) {
        return requestAttribute.get(key);
    }
    public String requestParameterGet(String key) {
        return requestParameter.get(key);
    }


    public Map<String, Object> getServletContextMap(){
        return new HashMap<>(servletContext);
    }
    public Map<String, Object> getServletSessionMap(){
        return new HashMap<>(servletSession);
    }
    public Map<String, Object> getRequestAttributeMap(){
        return new HashMap<>(requestAttribute);
    }
    public Map<String, Object> getRequestParameterMap(){
        return new HashMap<>(requestParameter);
    }

    public Object getAttribute(String key){
        Object result = servletContext.get(key);

        if(result == null){
            result = servletSession.get(key);
        }
        if(result == null){
            result = requestAttribute.get(key);
        }
        if(result == null){
            result = requestParameter.get(key);
        }

        return result;
    }

}
