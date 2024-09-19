package com.nkm.mypracticespring.exceptions;

import com.nkm.mypracticespring.dto.common.ErrorResponse;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommonException extends RuntimeException {

    private ErrorResponse errorResponse;

    public CommonException(ErrorResponse errorResponse) {
        super(errorResponse.getMessage());
        this.errorResponse = errorResponse;
    }

}
