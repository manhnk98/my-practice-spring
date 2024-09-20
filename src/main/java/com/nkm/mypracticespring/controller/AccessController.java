package com.nkm.mypracticespring.controller;

import com.nkm.mypracticespring.common.context.RestfulCtx;
import com.nkm.mypracticespring.dto.access.LoginRequest;
import com.nkm.mypracticespring.dto.common.ResponseDto;
import com.nkm.mypracticespring.dto.access.SignupRequest;
import com.nkm.mypracticespring.enums.MessageEnum;
import com.nkm.mypracticespring.services.IAccessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/api")
public class AccessController {

    @Autowired
    private IAccessService accessService;

    @PostMapping("/shop/signup")
    public ResponseDto<?> signup(@RequestBody SignupRequest signupReq) {
        signupReq.validate();
        accessService.signUp(signupReq);
        return new ResponseDto<>(RestfulCtx.getRequestId());
    }

    @PostMapping("/shop/login")
    public ResponseDto<?> login(@RequestBody LoginRequest loginReq) {
        return new ResponseDto<>(accessService.login(loginReq));
    }

}
