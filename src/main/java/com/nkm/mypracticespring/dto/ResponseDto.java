package com.nkm.mypracticespring.dto;

import com.nkm.mypracticespring.enums.MessageEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResponseDto<T> {

    private String status; // error || success

    private String message; // message error || message success

    private String code;

    private T data;

    private long currentTimestamp = System.currentTimeMillis();

    public ResponseDto(T data, MessageEnum msg) {
        this.status = "success";
        this.message = msg.getMessage();
        this.code = msg.getCode();
        this.data = data;
    }

}
