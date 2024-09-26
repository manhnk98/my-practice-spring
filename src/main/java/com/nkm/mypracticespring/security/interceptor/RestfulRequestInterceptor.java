package com.nkm.mypracticespring.security.interceptor;

import com.nkm.mypracticespring.common.Constant;
import com.nkm.mypracticespring.dto.access.CustomUserDetails;
import com.nkm.mypracticespring.dto.access.ShopInfo;
import com.nkm.mypracticespring.models.ShopModel;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.UUID;

@Component
public class RestfulRequestInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails shopDetails = (CustomUserDetails) authentication.getPrincipal();
        ShopModel shopModel = shopDetails.getShopModel();
        ShopInfo shopInfo = new ShopInfo(shopModel.getId(), shopModel.getName(), shopModel.getEmail());

        request.setAttribute(Constant.SHOP_CONTEXT, shopInfo);

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
