package com.nkm.mypracticespring.services.impl;

import com.nkm.mypracticespring.common.Constant;
import com.nkm.mypracticespring.dto.access.*;
import com.nkm.mypracticespring.dto.jwt.TokenGeneratedDto;
import com.nkm.mypracticespring.enums.RoleShop;
import com.nkm.mypracticespring.enums.SessionStatus;
import com.nkm.mypracticespring.enums.TokenType;
import com.nkm.mypracticespring.exceptions.AppException;
import com.nkm.mypracticespring.exceptions.AuthenticationException;
import com.nkm.mypracticespring.models.ShopModel;
import com.nkm.mypracticespring.repositories.SessionRepository;
import com.nkm.mypracticespring.repositories.ShopRepository;
import com.nkm.mypracticespring.services.CommonService;
import com.nkm.mypracticespring.services.IAccessService;
import com.nkm.mypracticespring.utils.JwtUtils;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Log4j2
@Service
public class AccessServiceImpl implements IAccessService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private ShopRepository shopRepository;

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CommonService commonService;

    @Override
    public SignupResponse register(SignupRequest signupReq) {
        Optional<ShopModel> shopInfoModel = shopRepository.findFirstByEmail(signupReq.getEmail());
        if (shopInfoModel.isPresent()) {
            throw new AppException("Shop already registered");
        }

        String passwordHash = passwordEncoder.encode(signupReq.getPassword());

        ShopModel newShop = new ShopModel();
        newShop.setName(signupReq.getName());
        newShop.setEmail(signupReq.getEmail());
        newShop.setPassword(passwordHash);
        newShop.setRoles(List.of(RoleShop.SHOP.name()));
        shopRepository.save(newShop);

        ShopInfo shopInfo = new ShopInfo(newShop.getId(), newShop.getName(), newShop.getEmail());
        TokenGeneratedDto tokenGenerated = commonService.saveSession(newShop);

        return new SignupResponse(shopInfo, tokenGenerated.accessToken(), tokenGenerated.refreshToken());
    }

    @Override
    public LoginResponse login(LoginRequest loginReq) {
        Authentication request = new UsernamePasswordAuthenticationToken(loginReq.getEmail(), loginReq.getPassword());
        Authentication authentication = authenticationManager.authenticate(request);

        CustomUserDetails shopDetails = (CustomUserDetails) authentication.getPrincipal();
        ShopModel shopModel = shopDetails.getShopModel();
        ShopInfo shopInfo = new ShopInfo(shopModel.getId(), shopModel.getName(), shopModel.getEmail());
        TokenGeneratedDto tokenGenerated = commonService.saveSession(shopModel);

        return new LoginResponse(shopInfo, tokenGenerated.accessToken(), tokenGenerated.refreshToken());
    }

    @Override
    public RefreshTokenResponse refreshToken(RefreshTokenRequest request) {
        String refreshToken = request.refreshToken();

        if (StringUtils.isEmpty(refreshToken) || !JwtUtils.tokenIsValid(refreshToken)) {
            throw new AuthenticationException("Invalid refresh token");
        }

        String subOfToken = JwtUtils.getFromJwt(refreshToken, Constant.SUB_JWT);
        if (!TokenType.REFRESH_TOKEN.name().equals(subOfToken)) {
            throw new AuthenticationException("Invalid refresh token");
        }

        String userId = JwtUtils.getFromJwt(refreshToken, Constant.PAYLOAD_USER_ID);
        String sessionIdJwt = JwtUtils.getFromJwt(refreshToken, Constant.JTI_JWT);
        String statusSession = commonService.getStatusSession(userId, sessionIdJwt);
        if (StringUtils.isEmpty(statusSession) || !SessionStatus.ACTIVE.name().equals(statusSession)) {
            throw new AuthenticationException("Session inactive");
        }

        String shopId = JwtUtils.getFromJwt(refreshToken, Constant.PAYLOAD_USER_ID);
        Optional<ShopModel> shopInfoModel = shopRepository.findById(shopId);
        if (shopInfoModel.isEmpty()) {
            throw new AuthenticationException("Shop information not found");
        }
        commonService.removeSession(userId, sessionIdJwt);
        TokenGeneratedDto tokenGenerated = commonService.saveSession(shopInfoModel.get());

        return new RefreshTokenResponse(tokenGenerated.accessToken(), tokenGenerated.refreshToken());
    }

    @Override
    public void removeSession(String userId, String sessionId) {
        if (sessionId != null) {
            commonService.removeSession(userId, sessionId);
            sessionRepository.deleteById(sessionId);
        }
    }
}
