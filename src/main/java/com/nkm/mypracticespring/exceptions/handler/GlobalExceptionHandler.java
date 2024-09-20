package com.nkm.mypracticespring.exceptions.handler;

import com.nkm.mypracticespring.common.Constant;
import com.nkm.mypracticespring.dto.common.ResponseDto;
import com.nkm.mypracticespring.enums.MessageEnum;
import com.nkm.mypracticespring.exceptions.AppException;
import com.nkm.mypracticespring.exceptions.DataInvalidException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DataInvalidException.class)
    public ResponseEntity<ResponseDto<?>> handleValidationException(DataInvalidException ex) {
        MessageEnum messageEnum = MessageEnum.ERR_INVALID_PARAM;
        String message = Objects.requireNonNullElse(ex.getMessage(), messageEnum.getMessage());
        ResponseDto<?> responseError = this.buildResponseErr(message, messageEnum.getCode());

        return new ResponseEntity<>(responseError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AppException.class)
    public ResponseEntity<ResponseDto<?>> handleAppException(AppException ex) {
        MessageEnum messageEnum = MessageEnum.ERR_APPLICATION;
        String message = Objects.requireNonNullElse(ex.getMessage(), messageEnum.getMessage());
        ResponseDto<?> responseError = this.buildResponseErr(message, messageEnum.getCode());

        return new ResponseEntity<>(responseError, HttpStatus.INTERNAL_SERVER_ERROR);
    }

//    @ExceptionHandler(NoHandlerFoundException.class)
//    public ResponseEntity<ResponseDto<?>> handleResourceNotFoundException() {
//        ResponseDto<?> responseError = this.buildResponseErr("Resource not found", "x");
//        return new ResponseEntity<>(responseError, HttpStatus.BAD_REQUEST);
//    }

    private ResponseDto<?> buildResponseErr(String message, String code) {
        ResponseDto<?> responseError = new ResponseDto<>();
        responseError.setStatus(Constant.ERROR);
        responseError.setMessage(message);
        responseError.setCode(Objects.requireNonNullElse(code, Constant.ERROR));

        return responseError;
    }

}
