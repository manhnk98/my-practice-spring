package com.nkm.mypracticespring.services.impl;

import com.nkm.mypracticespring.services.IAccessService;
import org.springframework.stereotype.Service;

@Service
public class AccessServiceImpl implements IAccessService {

    @Override
    public void signUp(String name, String email, String password) {

    }

    @Override
    public void login(String email, String password, String refreshToken) {

    }

    @Override
    public void handlerRefreshToken(String refreshToken, String keystore, String password) {

    }
}
