package com.nkm.mypracticespring.services.impl;

import com.nkm.mypracticespring.dto.SignupRequest;
import com.nkm.mypracticespring.dto.access.LoginRequest;
import com.nkm.mypracticespring.dto.common.ErrorResponse;
import com.nkm.mypracticespring.enums.RoleShop;
import com.nkm.mypracticespring.exceptions.CommonException;
import com.nkm.mypracticespring.models.ShopModel;
import com.nkm.mypracticespring.repositories.ShopRepository;
import com.nkm.mypracticespring.services.IAccessService;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Log4j2
@Service
public class AccessServiceImpl implements IAccessService {

    @Autowired
    private ShopRepository shopRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void signUp(SignupRequest signupReq) {
        Optional<ShopModel> shopInfo = shopRepository.findFirstByEmail(signupReq.getEmail());
        if (shopInfo.isPresent()) {
            throw new CommonException(new ErrorResponse("Shop already registered", "EXISTED"));
        }

        if (StringUtils.isBlank(signupReq.getPassword()) || signupReq.getPassword().length() < 7) {
            throw new CommonException(new ErrorResponse("Password should be at least 7 characters", "PASSWORD_INVALID"));
        }

        String passwordHash = passwordEncoder.encode(signupReq.getPassword());

        ShopModel newShop = new ShopModel();
        newShop.setName(signupReq.getName());
        newShop.setEmail(signupReq.getEmail());
        newShop.setPassword(passwordHash);
        newShop.setRoles(List.of(RoleShop.SHOP.name()));
        shopRepository.save(newShop);

    }

    @Override
    public void login(LoginRequest loginReq) {
        Optional<ShopModel> shopInfo = shopRepository.findFirstByEmail(loginReq.getEmail());
        if (shopInfo.isPresent()) {
            throw new CommonException(new ErrorResponse("Shop already registered", "EXISTED"));
        }
    }

    @Override
    public void handlerRefreshToken(String refreshToken, String keystore, String password) {

    }
}
