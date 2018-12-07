package com.kozitski.pufar.util.request;

import com.kozitski.pufar.command.RequestValue;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

public class RequestValueTransformer {

    public static RequestValue transformFrom(HttpServletRequest request){

        HashMap<String, Object> servletContext = new HashMap<>();
        Enumeration<String> servletContextAttributeNames = request.getServletContext().getAttributeNames();
        while (servletContextAttributeNames.hasMoreElements()){
            String attributeName = servletContextAttributeNames.nextElement();
            servletContext.put(attributeName, request.getServletContext().getAttribute(attributeName));
        }

        HashMap<String, Object> servletSession = new HashMap<>();
        Enumeration<String> sessionAttributeNames = request.getSession().getAttributeNames();
        while (sessionAttributeNames.hasMoreElements()){
            String attributeName = sessionAttributeNames.nextElement();
            servletSession.put(attributeName, request.getSession().getAttribute(attributeName));
        }

        HashMap<String, Object> requestAttribute = new HashMap<>();
        Enumeration<String> attributeNames = request.getAttributeNames();
        while (attributeNames.hasMoreElements()){
            String attributeName = attributeNames.nextElement();
            requestAttribute.put(attributeName, request.getAttribute(attributeName));
        }

        // means that parameter will not have duplicate names
        HashMap<String, String> requestParameter = new HashMap<>();
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()){
            String parameterName = parameterNames.nextElement();
            requestParameter.put(parameterName, request.getParameter(parameterName));
        }

        return new RequestValue(servletContext, servletSession, requestAttribute, requestParameter);
    }

    public static void transformTo(HttpServletRequest request, RequestValue requestValue){

        // context
        Enumeration<String> servletContextAttributeNames = request.getServletContext().getAttributeNames();
        while (servletContextAttributeNames.hasMoreElements()){
            String attributeName = servletContextAttributeNames.nextElement();
            request.getServletContext().removeAttribute(attributeName);
        }
        Map<String, Object> contextAttributes = requestValue.getServletContextMap();
        for(Map.Entry<String, Object> entry : contextAttributes.entrySet()){
            request.getServletContext().setAttribute(entry.getKey(),entry.getValue());
        }

        // session
        Enumeration<String> sessionAttributeNames = request.getSession().getAttributeNames();
        while (sessionAttributeNames.hasMoreElements()){
            String attributeName = sessionAttributeNames.nextElement();
            request.getSession().removeAttribute(attributeName);
        }
        Map<String, Object> sessionAttributes = requestValue.getServletSessionMap();
        for(Map.Entry<String, Object> entry : sessionAttributes.entrySet()){
            request.getSession().setAttribute(entry.getKey(),entry.getValue());
        }

        // request Attributes
        Enumeration<String> requestAttributeNames = request.getAttributeNames();
        while (requestAttributeNames.hasMoreElements()){
            String attributeName = requestAttributeNames.nextElement();
            request.removeAttribute(attributeName);
        }
        Map<String, Object> requestAttributes = requestValue.getRequestAttributeMap();
        for(Map.Entry<String, Object> entry : requestAttributes.entrySet()){
            request.setAttribute(entry.getKey(),entry.getValue());
        }

        // request Parameters
        Enumeration<String> requestParametersNames = request.getParameterNames();
        while (requestParametersNames.hasMoreElements()){
            String attributeName = requestParametersNames.nextElement();
            request.removeAttribute(attributeName);
        }
        Map<String, Object> requestParameters = requestValue.getRequestParameterMap();
        for(Map.Entry<String, Object> entry : requestParameters.entrySet()){
            request.getParameterValues(entry.getKey())[0] = entry.getValue().toString();
        }

    }

}
