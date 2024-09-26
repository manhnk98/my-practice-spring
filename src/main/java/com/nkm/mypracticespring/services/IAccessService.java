package com.nkm.mypracticespring.services;

import com.nkm.mypracticespring.dto.access.*;

public interface IAccessService {

    SignupResponse signUp(SignupRequest signupReq);

    LoginResponse login(LoginRequest loginReq);

    RefreshTokenResponse refreshToken(RefreshTokenRequest refreshTokenReq);
}
