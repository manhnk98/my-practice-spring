package com.nkm.mypracticespring.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nkm.mypracticespring.common.Constant;
import com.nkm.mypracticespring.dto.access.CustomUserDetails;
import com.nkm.mypracticespring.dto.common.ResponseDto;
import com.nkm.mypracticespring.enums.MessageEnum;
import com.nkm.mypracticespring.enums.TokenType;
import com.nkm.mypracticespring.services.impl.CustomUserDetailsService;
import com.nkm.mypracticespring.utils.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Log4j2
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
            if (StringUtils.isNoneBlank(token) && JwtUtils.tokenIsValid(token)) {
                String subOfToken = JwtUtils.getFromJwt(token, Constant.SUB_JWT);

                if (TokenType.ACCESS_TOKEN.name().equals(subOfToken)) {
                    String email = JwtUtils.getFromJwt(token, Constant.PAYLOAD_EMAIL);
                    UserDetails userDetails = userDetailsService.loadUserByUsername(email);
                    Authentication authentication = new UsernamePasswordAuthenticationToken(
                            userDetails, userDetails.getUsername(), userDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }

        } catch (Exception e) {
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setStatus(HttpStatus.UNAUTHORIZED.value());

            ResponseDto<?> responseError = new ResponseDto<>();
            responseError.setStatus(Constant.ERROR);
            responseError.setMessage(MessageEnum.ERR_INVALID_TOKEN.getMessage());
            responseError.setCode(MessageEnum.ERR_INVALID_TOKEN.getCode());

            new ObjectMapper().writeValue(response.getOutputStream(), responseError);
            return;
        }
        filterChain.doFilter(request, response);
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.isNotBlank(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
