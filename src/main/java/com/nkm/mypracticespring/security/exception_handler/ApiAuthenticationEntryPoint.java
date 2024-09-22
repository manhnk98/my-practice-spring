package com.nkm.mypracticespring.security.exception_handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nkm.mypracticespring.common.Constant;
import com.nkm.mypracticespring.dto.common.ResponseDto;
import com.nkm.mypracticespring.enums.MessageEnum;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;
import java.util.Collections;
import java.util.Objects;

@Log4j2
public class ApiAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        log.error("Unauthorized error: {}", authException.getMessage());

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());

        ResponseDto<?> responseError = new ResponseDto<>();
        responseError.setStatus(Constant.ERROR);
        responseError.setMessage(HttpStatus.UNAUTHORIZED.getReasonPhrase());
        responseError.setCode(MessageEnum.ERR_APPLICATION.getCode());

        new ObjectMapper().writeValue(response.getOutputStream(), responseError);
    }
}
