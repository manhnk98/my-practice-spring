package com.nkm.mypracticespring.services;

import com.nkm.mypracticespring.dto.access.LoginResponse;
import com.nkm.mypracticespring.dto.access.SignupRequest;
import com.nkm.mypracticespring.dto.access.LoginRequest;

public interface IAccessService {

    void signUp(SignupRequest signupReq);

    LoginResponse login(LoginRequest loginReq);

    void handlerRefreshToken(String refreshToken, String keystore, String password);
}
