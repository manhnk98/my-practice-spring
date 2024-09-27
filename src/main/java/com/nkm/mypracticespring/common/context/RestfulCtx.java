package com.nkm.mypracticespring.common.context;

import com.nkm.mypracticespring.common.Constant;
import com.nkm.mypracticespring.dto.access.ShopInfo;
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

    public static ShopInfo shopContext() {
        HttpServletRequest request = getCurrentHttpRequest();
        if (request != null) {
            return (ShopInfo) request.getAttribute(Constant.SHOP_CONTEXT);
        }
        return null;
    }

    public static String getSessionRequest() {
        HttpServletRequest request = getCurrentHttpRequest();
        if (request != null) {
            return (String) request.getAttribute(Constant.SESSION_ID_REQUEST);
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
