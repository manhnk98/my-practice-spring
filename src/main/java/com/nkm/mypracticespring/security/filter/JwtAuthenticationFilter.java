package com.nkm.mypracticespring.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nkm.mypracticespring.common.Constant;
import com.nkm.mypracticespring.config.SecurityConfig;
import com.nkm.mypracticespring.dto.common.ResponseDto;
import com.nkm.mypracticespring.enums.MessageEnum;
import com.nkm.mypracticespring.enums.SessionStatus;
import com.nkm.mypracticespring.enums.TokenType;
import com.nkm.mypracticespring.exceptions.AppException;
import com.nkm.mypracticespring.services.CommonService;
import com.nkm.mypracticespring.utils.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

@Log4j2
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private CommonService commonService;

    private final AntPathMatcher pathMatcher = new AntPathMatcher();


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String token = this.getJwtFromRequest(request);
            if (StringUtils.isEmpty(token) || !JwtUtils.tokenIsValid(token)) {
                throw new AppException("Token invalid");
            }

            String subOfToken = JwtUtils.getFromJwt(token, Constant.SUB_JWT);
            if (!TokenType.ACCESS_TOKEN.name().equals(subOfToken)) {
                throw new AppException("This is not an accessToken");
            }

            String userId = JwtUtils.getFromJwt(token, Constant.PAYLOAD_USER_ID);
            String sessionIdJwt = JwtUtils.getFromJwt(token, Constant.JTI_JWT);
            String email = JwtUtils.getFromJwt(token, Constant.PAYLOAD_EMAIL);
            if (StringUtils.isEmpty(userId) || StringUtils.isEmpty(sessionIdJwt) || StringUtils.isEmpty(email)) {
                throw new AppException("Not found info from token");
            }

            String statusSession = commonService.getStatusSession(userId, sessionIdJwt);
            if (StringUtils.isEmpty(statusSession) || !SessionStatus.ACTIVE.name().equals(statusSession)) {
                throw new AppException("Session inactive");
            }

            UserDetails userDetails = userDetailsService.loadUserByUsername(email);
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    userDetails, userDetails.getUsername(), userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);

            request.setAttribute(Constant.SESSION_ID_REQUEST, sessionIdJwt);

        } catch (Exception e) {
            log.error("JwtAuthenticationFilter doFilterInternal ERROR : {}", e.getMessage());
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

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String requestPath = request.getRequestURI();
        // nếu requestPath khớp pattern với ít nhất 1 path trong WHITE_LIST_API thì không chạy vào doFilterInternal
        return Arrays.stream(SecurityConfig.WHITE_LIST_API).anyMatch(e -> pathMatcher.match(e, requestPath));
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.isNotBlank(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
