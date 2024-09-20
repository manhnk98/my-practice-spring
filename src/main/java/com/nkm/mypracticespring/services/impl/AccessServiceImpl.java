package com.nkm.mypracticespring.services.impl;

import com.nkm.mypracticespring.common.Constant;
import com.nkm.mypracticespring.dto.access.LoginResponse;
import com.nkm.mypracticespring.dto.access.ShopInfo;
import com.nkm.mypracticespring.dto.access.SignupRequest;
import com.nkm.mypracticespring.dto.access.LoginRequest;
import com.nkm.mypracticespring.dto.jwt.CreateJwtDto;
import com.nkm.mypracticespring.enums.RoleShop;
import com.nkm.mypracticespring.exceptions.AppException;
import com.nkm.mypracticespring.models.ShopModel;
import com.nkm.mypracticespring.repositories.ShopRepository;
import com.nkm.mypracticespring.security.jwt.JwtTokenProvider;
import com.nkm.mypracticespring.services.IAccessService;
import lombok.extern.log4j.Log4j2;
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
            throw new AppException("Shop already registered");
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
    public LoginResponse login(LoginRequest loginReq) {
        Optional<ShopModel> shopModel = shopRepository.findFirstByEmail(loginReq.getEmail());
        if (shopModel.isEmpty()) {
            throw new AppException("Shop already registered");
        }

        boolean match = passwordEncoder.matches(loginReq.getPassword(), shopModel.get().getPassword());
        if (!match) {
            throw new AppException("Login failed");
        }

        ShopInfo shopInfo = new ShopInfo(shopModel.get().getId(), shopModel.get().getName(), shopModel.get().getEmail());

        String accessToken = JwtTokenProvider.generateToken(
                new CreateJwtDto(shopModel.get().getId(), shopModel.get().getEmail()), Constant.TOKEN_EXPIRE_TIME);
        String refreshToken = JwtTokenProvider.generateToken(
                new CreateJwtDto(shopModel.get().getId(), shopModel.get().getEmail()), Constant.REFRESH_TOKEN_EXPIRE_TIME);

        return new LoginResponse(shopInfo, accessToken, refreshToken);
    }

    @Override
    public void handlerRefreshToken(String refreshToken, String keystore, String password) {

    }
}
