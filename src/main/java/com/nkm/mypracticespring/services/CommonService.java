package com.nkm.mypracticespring.services;

import com.nkm.mypracticespring.common.Constant;
import com.nkm.mypracticespring.dto.jwt.CreateJwtDto;
import com.nkm.mypracticespring.dto.jwt.TokenGeneratedDto;
import com.nkm.mypracticespring.enums.SessionStatus;
import com.nkm.mypracticespring.models.SessionModel;
import com.nkm.mypracticespring.models.ShopModel;
import com.nkm.mypracticespring.repositories.SessionRepository;
import com.nkm.mypracticespring.utils.CommonUtils;
import com.nkm.mypracticespring.utils.JwtUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Service
public class CommonService {

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private RedisTemplate<String, String> sessionManagerRedis;

    public TokenGeneratedDto saveSession(ShopModel shopModel) {
        LocalDateTime now = LocalDateTime.now();

        SessionModel sessionModel = new SessionModel();
        sessionModel.setUserId(shopModel.getId());
        sessionModel.setStatus(SessionStatus.ACTIVE);
        sessionModel.setCreatedTime(now);
        sessionModel.setUpdatedTime(now);
        sessionModel.setExpireTime(now.plus(Constant.REFRESH_TOKEN_EXPIRE_TIME.toMillis(), ChronoUnit.MILLIS));
        sessionRepository.save(sessionModel);

        String sessionId = sessionModel.getId();
        String key = CommonUtils.keySession(shopModel.getId(), sessionId);
        sessionManagerRedis.opsForValue().set(key, SessionStatus.ACTIVE.name(), Constant.REFRESH_TOKEN_EXPIRE_TIME);

        return JwtUtils.generateToken(new CreateJwtDto(shopModel.getId(), shopModel.getEmail(), sessionId));
    }

    public void removeSession(String userId, String sessionId) {
        sessionRepository.deleteById(sessionId);
        String key = CommonUtils.keySession(userId, sessionId);
        sessionManagerRedis.delete(key);
    }

    public String getStatusSession(String userId, String sessionId) {
        String keySession = CommonUtils.keySession(userId, sessionId);
        String sessionStatus = sessionManagerRedis.opsForValue().get(keySession);
        System.out.println("sessionStatus : " + sessionStatus);
        if (sessionStatus == null) {
            Optional<SessionModel> sessionModel = sessionRepository.findById(sessionId);
            if (sessionModel.isPresent() &&
                    sessionModel.get().getExpireTime().isAfter(LocalDateTime.now()) &&
                    sessionModel.get().getStatus() == SessionStatus.ACTIVE) {
                Duration expireTime = Duration.between(LocalDateTime.now(), sessionModel.get().getExpireTime());
                sessionManagerRedis.opsForValue().set(keySession, SessionStatus.ACTIVE.name(), expireTime.toMinutes());
            } else {
                sessionManagerRedis.opsForValue().set(keySession, StringUtils.EMPTY, Duration.ofMinutes(1));
                sessionRepository.deleteById(sessionId);
                return StringUtils.EMPTY;
            }
        }

        return sessionStatus;
    }

}
