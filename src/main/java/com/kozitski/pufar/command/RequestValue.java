package com.kozitski.pufar.command;

import java.util.HashMap;
import java.util.Map;

// TODO: Auto-generated Javadoc
/**
 * The Class RequestValue.
 */
public class RequestValue {

    /** The servlet context. */
    private Map<String, Object> servletContext;
    
    /** The servlet session. */
    private Map<String, Object> servletSession;
    
    /** The request attribute. */
    private Map<String, Object> requestAttribute;
    
    /** The request parameter. */
    private Map<String, String> requestParameter;

    /**
     * Instantiates a new request value.
     */
    public RequestValue() {
    }

    /**
     * Instantiates a new request value.
     *
     * @param servletContext the servlet context
     * @param servletSession the servlet session
     * @param requestAttribute the request attribute
     * @param requestParameter the request parameter
     */
    public RequestValue(Map<String, Object> servletContext, Map<String, Object> servletSession,
                        Map<String, Object> requestAttribute, Map<String, String> requestParameter) {
        this.servletContext = servletContext;
        this.servletSession = servletSession;
        this.requestAttribute = requestAttribute;
        this.requestParameter = requestParameter;
    }

    /**
     * Servlet context put.
     *
     * @param key the key
     * @param value the value
     * @return the object
     */
    public Object servletContextPut(String key, Object value) {
        servletContext.remove(key);
        return servletContext.put(key, value);
    }

    /**
     * Servlet session put.
     *
     * @param key the key
     * @param value the value
     * @return the object
     */
    public Object servletSessionPut(String key, Object value) {
        servletSession.remove(key);
        return servletSession.put(key, value);
    }

    /**
     * Request attribute put.
     *
     * @param key the key
     * @param value the value
     * @return the object
     */
    public Object requestAttributePut(String key, Object value) {
        requestAttribute.remove(key);
        return requestAttribute.put(key, value);
    }

    /**
     * Request parameter put.
     *
     * @param key the key
     * @param value the value
     * @return the object
     */
    public Object requestParameterPut(String key, String value) {
        requestParameter.remove(key);
        return requestParameter.put(key, value);
    }

    /**
     * Servlet context get.
     *
     * @param key the key
     * @return the object
     */
    public Object servletContextGet(String key) {
        return servletContext.get(key);
    }

    /**
     * Servlet session get.
     *
     * @param key the key
     * @return the object
     */
    public Object servletSessionGet(String key) {
        return servletSession.get(key);
    }

    /**
     * Request attribute get.
     *
     * @param key the key
     * @return the object
     */
    public Object requestAttributeGet(String key) {
        return requestAttribute.get(key);
    }

    /**
     * Request parameter get.
     *
     * @param key the key
     * @return the string
     */
    public String requestParameterGet(String key) {
        return requestParameter.get(key);
    }


    /**
     * Gets the servlet context map.
     *
     * @return the servlet context map
     */
    public Map<String, Object> getServletContextMap() {
        return new HashMap<>(servletContext);
    }

    /**
     * Gets the servlet session map.
     *
     * @return the servlet session map
     */
    public Map<String, Object> getServletSessionMap() {
        return new HashMap<>(servletSession);
    }

    /**
     * Gets the request attribute map.
     *
     * @return the request attribute map
     */
    public Map<String, Object> getRequestAttributeMap() {
        return new HashMap<>(requestAttribute);
    }

    /**
     * Gets the request parameter map.
     *
     * @return the request parameter map
     */
    public Map<String, Object> getRequestParameterMap() {
        return new HashMap<>(requestParameter);
    }

    /**
     * Gets the attribute.
     *
     * @param key the key
     * @return the attribute
     */
    public Object getAttribute(String key) {
        Object result = servletContext.get(key);

        if (result == null) {
            result = servletSession.get(key);
        }
        if (result == null) {
            result = requestAttribute.get(key);
        }
        if (result == null) {
            result = requestParameter.get(key);
        }

        return result;
    }

}
