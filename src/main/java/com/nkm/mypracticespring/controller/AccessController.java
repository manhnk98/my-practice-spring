package com.nkm.mypracticespring.controller;

import com.nkm.mypracticespring.common.context.RestfulCtx;
import com.nkm.mypracticespring.dto.ResponseDto;
import com.nkm.mypracticespring.enums.MessageEnum;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/api")
public class AccessController {

    @PostMapping("/shop/signup")
    public ResponseDto<?> hihi() {
        System.out.println("ok");
        return new ResponseDto<>(RestfulCtx.getRequestId(), MessageEnum.SUCCESS);
    }

}
