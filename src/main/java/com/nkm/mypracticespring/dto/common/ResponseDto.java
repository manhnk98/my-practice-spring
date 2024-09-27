package com.nkm.mypracticespring.dto.common;

import com.nkm.mypracticespring.common.Constant;
import com.nkm.mypracticespring.enums.MessageEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResponseDto<T> {

    private String status = Constant.SUCCESS;

    private String message;

    private String code;

    private T data;

    private long currentTimestamp = System.currentTimeMillis();

    public ResponseDto(T data, MessageEnum msg) {
        this.status = Constant.SUCCESS;
        this.message = msg.getMessage();
        this.code = msg.getCode();
        this.data = data;
    }

    public ResponseDto(T data) {
        MessageEnum msg = MessageEnum.SUCCESS;
        this.status = Constant.SUCCESS;
        this.message = msg.getMessage();
        this.code = msg.getCode();
        this.data = data;
    }

    public ResponseDto(MessageEnum msg) {
        this.status = Constant.SUCCESS;
        this.message = msg.getMessage();
        this.code = msg.getCode();
    }

}
