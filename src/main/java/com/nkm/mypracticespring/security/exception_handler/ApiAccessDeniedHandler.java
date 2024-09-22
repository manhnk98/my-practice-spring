package com.nkm.mypracticespring.security.exception_handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nkm.mypracticespring.common.Constant;
import com.nkm.mypracticespring.dto.common.ResponseDto;
import com.nkm.mypracticespring.enums.MessageEnum;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;
import java.util.Collections;

@Log4j2
public class ApiAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {
        log.error("Access Denied error: {}", accessDeniedException.getMessage());

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.FORBIDDEN.value());

        ResponseDto<?> responseError = new ResponseDto<>();
        responseError.setStatus(Constant.ERROR);
        responseError.setMessage(HttpStatus.FORBIDDEN.getReasonPhrase());
        responseError.setCode(MessageEnum.ERR_APPLICATION.getCode());

        new ObjectMapper().writeValue(response.getOutputStream(), responseError);
    }

}
