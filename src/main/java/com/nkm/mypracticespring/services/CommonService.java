package com.nkm.mypracticespring.services;

import com.nkm.mypracticespring.dto.jwt.CreateJwtDto;
import com.nkm.mypracticespring.dto.jwt.TokenGeneratedDto;
import com.nkm.mypracticespring.enums.SessionStatus;
import com.nkm.mypracticespring.models.SessionModel;
import com.nkm.mypracticespring.models.ShopModel;
import com.nkm.mypracticespring.repositories.SessionRepository;
import com.nkm.mypracticespring.utils.CommonUtils;
import com.nkm.mypracticespring.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
public class CommonService {

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private RedisTemplate<String, String> sessionManagerRedis;

    public TokenGeneratedDto genAndSaveToken(ShopModel shopModel) {
        SessionModel sessionModel = new SessionModel();
        sessionModel.setUserId(shopModel.getId());
        sessionModel.setStatus(SessionStatus.ACTIVE);
        sessionModel.setCreateTime(LocalDateTime.now());
        sessionModel.setUpdateTime(LocalDateTime.now());
        sessionRepository.save(sessionModel);

        String sessionId = sessionModel.getId();
        String key = CommonUtils.keySession(shopModel.getId(), sessionId);
        sessionManagerRedis.opsForValue().set(key, SessionStatus.ACTIVE.name(), 1, TimeUnit.DAYS);

        return JwtUtils.generateToken(new CreateJwtDto(shopModel.getId(), shopModel.getEmail(), sessionId));
    }

    public String getStatusSession(String userId, String jti) {
        String keySession = CommonUtils.keySession(userId, jti);
        String sessionStatus = sessionManagerRedis.opsForValue().get(keySession);
        if (sessionStatus == null) {
            Optional<SessionModel> sessionModel = sessionRepository.findById(jti);
            if (sessionModel.isPresent() && sessionModel.get().getStatus() == SessionStatus.ACTIVE) {
                sessionManagerRedis.opsForValue().set(keySession, sessionModel.get().getId(), 1, TimeUnit.DAYS);
            } else {
                sessionManagerRedis.opsForValue().set(keySession, "", 1, TimeUnit.DAYS);
                return "";
            }
        }

        return sessionStatus;
    }

}
