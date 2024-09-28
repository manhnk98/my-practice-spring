package com.nkm.mypracticespring.listener.restful;

import com.nkm.mypracticespring.common.context.RestfulCtx;
import com.nkm.mypracticespring.dto.access.LoginRequest;
import com.nkm.mypracticespring.dto.access.RefreshTokenRequest;
import com.nkm.mypracticespring.dto.access.RefreshTokenResponse;
import com.nkm.mypracticespring.dto.access.SignupRequest;
import com.nkm.mypracticespring.dto.common.ResponseDto;
import com.nkm.mypracticespring.enums.MessageEnum;
import com.nkm.mypracticespring.services.IAccessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping("/v1/api/access")
public class AccessController {

    @Autowired
    private IAccessService accessService;

    @PostMapping("/signup")
    public ResponseDto<?> signup(@RequestBody SignupRequest signupReq) {
        return new ResponseDto<>(accessService.register(signupReq));
    }

    @PostMapping(value = "/login")
    public ResponseDto<?> login(@RequestBody LoginRequest request) {
        return new ResponseDto<>(accessService.login(request));
    }

    @PostMapping(value = "/refresh-token")
    public ResponseDto<?> refreshToken(@RequestBody RefreshTokenRequest request) {
        RefreshTokenResponse tokenResponse = accessService.refreshToken(request);
        return new ResponseDto<>(tokenResponse);
    }

    @PostMapping(value = "/logout")
    public ResponseDto<?> logout() {
        accessService.removeSession(Objects.requireNonNull(RestfulCtx.shopContext()).id(), RestfulCtx.getSessionRequest());
        return new ResponseDto<>(MessageEnum.LOGOUT_SUCCESS);
    }

}
