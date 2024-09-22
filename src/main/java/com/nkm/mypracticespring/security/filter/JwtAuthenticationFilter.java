package com.nkm.mypracticespring.security.filter;

import com.nkm.mypracticespring.common.Constant;
import com.nkm.mypracticespring.exceptions.AppException;
import com.nkm.mypracticespring.utils.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;

    public JwtAuthenticationFilter(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String token = this.getJwtFromRequest(request);
            if (StringUtils.hasText(token) && JwtUtils.validateToken(token)) {
                String email = JwtUtils.getFromJwt(token, Constant.PAYLOAD_EMAIL);
                UserDetails userDetails = userDetailsService.loadUserByUsername(email);
                Authentication authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, userDetails.getUsername(), userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        filterChain.doFilter(request, response);
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
