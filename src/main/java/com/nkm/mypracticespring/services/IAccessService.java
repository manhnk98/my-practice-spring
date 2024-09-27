package com.nkm.mypracticespring.services;

import com.nkm.mypracticespring.dto.access.*;

public interface IAccessService {

    SignupResponse register(SignupRequest signupReq);

    LoginResponse login(LoginRequest loginReq);

    RefreshTokenResponse refreshToken(RefreshTokenRequest refreshTokenReq);

    void removeSession(String userId, String sessionId);
}
