package com.nkm.mypracticespring.common.context;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Locale;

@Log4j2
public class RestfulCtx {

    private static HttpServletRequest getCurrentHttpRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes instanceof ServletRequestAttributes) {
            return ((ServletRequestAttributes) requestAttributes).getRequest();
        }
        return null;
    }

    public static String getRequestId() {
        HttpServletRequest request = getCurrentHttpRequest();
        if (request != null) {
            return (String) request.getAttribute("requestId");
        }
        return null;
    }

    public static Locale getLocale() {
        try {
            HttpServletRequest request = getCurrentHttpRequest();
            if (request != null) {
                String lang = request.getHeader("Accept-Language");
                if (lang != null) {
                    return Locale.forLanguageTag(lang);
                }
            }
        } catch (Exception e) {
            log.warn(e);
        }
        return Locale.US;
    }

}