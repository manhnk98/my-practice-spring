package com.nkm.mypracticespring.services;

public interface IAccessService {

    void signUp(String name, String email, String password);

    void login(String email, String password, String refreshToken);

    void handlerRefreshToken(String refreshToken, String keystore, String password);
}
