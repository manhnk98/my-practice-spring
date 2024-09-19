package com.nkm.mypracticespring.dto.common;

import com.nkm.mypracticespring.enums.MessageEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@NoArgsConstructor
public class ErrorResponse {

    private String message;

    private String code;

    public ErrorResponse(String message) {
        this.message = message;
        this.code = HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase();
    }

    public ErrorResponse(String message, String code) {
        this.message = message;
        this.code = code;
    }

//    public ErrorResponse(String message, Object... objects) {
//        this.message = String.format(message, objects);
//        this.code = HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase();
//    }

    public ErrorResponse(MessageEnum messageEnum) {
        this.message = messageEnum.getMessage();
        this.code = messageEnum.getCode();
    }

//    public ErrorResponse(MessageEnum messageEnum, String message) {
//        this.message = message;
//        this.code = messageEnum.getCode();
//    }

}
