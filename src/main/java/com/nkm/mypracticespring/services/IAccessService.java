package com.nkm.mypracticespring.services;

import com.nkm.mypracticespring.dto.SignupRequest;
import com.nkm.mypracticespring.dto.access.LoginRequest;

public interface IAccessService {

    void signUp(SignupRequest signupReq);

    void login(LoginRequest loginReq);

    void handlerRefreshToken(String refreshToken, String keystore, String password);
}
