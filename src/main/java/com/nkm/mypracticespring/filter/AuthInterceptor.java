package com.nkm.mypracticespring.filter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.List;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("Authorization");

        if (token != null && validateToken(token)) {
            UserDetails userDetails = getUserFromToken(token);
            UsernamePasswordAuthenticationToken auth =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(auth);

            return true;
        }

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        return false;
    }

    private boolean validateToken(String token) {
        return token.equals("valid-token");
    }

    private UserDetails getUserFromToken(String token) {
        return new User("username", "", List.of(new SimpleGrantedAuthority("ROLE_USER")));
    }
}
