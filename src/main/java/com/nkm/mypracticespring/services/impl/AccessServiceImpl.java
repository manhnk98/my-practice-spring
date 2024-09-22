package com.nkm.mypracticespring.services.impl;

import com.nkm.mypracticespring.common.Constant;
import com.nkm.mypracticespring.dto.access.*;
import com.nkm.mypracticespring.enums.RoleShop;
import com.nkm.mypracticespring.exceptions.AppException;
import com.nkm.mypracticespring.models.ShopModel;
import com.nkm.mypracticespring.repositories.ShopRepository;
import com.nkm.mypracticespring.utils.JwtUtils;
import com.nkm.mypracticespring.services.IAccessService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Log4j2
@Service
public class AccessServiceImpl implements IAccessService {

    @Autowired
    private AuthenticationManager authenticationManager;

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
        Authentication request = new UsernamePasswordAuthenticationToken(loginReq.getEmail(), loginReq.getPassword());
        Authentication authentication = authenticationManager.authenticate(request);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        CustomUserDetails shopDetails = (CustomUserDetails) authentication.getPrincipal();
        ShopModel shopModel = shopDetails.getShopModel();
        ShopInfo shopInfo = new ShopInfo(shopModel.getId(), shopModel.getName(), shopModel.getEmail());

        Map<String, Object> payloadJwt = new HashMap<>();
        payloadJwt.put(Constant.PAYLOAD_USER_ID, shopInfo.id());
        payloadJwt.put(Constant.PAYLOAD_EMAIL, shopInfo.email());
        payloadJwt.put(Constant.PAYLOAD_SHOP_NAME, shopInfo.name());

        String accessToken = JwtUtils.generateToken(payloadJwt, Constant.TOKEN_EXPIRE_TIME);
        String refreshToken = JwtUtils.generateToken(payloadJwt, Constant.REFRESH_TOKEN_EXPIRE_TIME);

        return new LoginResponse(shopInfo, accessToken, refreshToken);
    }

    @Override
    public void handlerRefreshToken(String refreshToken, String keystore, String password) {

    }
}
