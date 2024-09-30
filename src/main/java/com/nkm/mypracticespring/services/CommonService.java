package com.nkm.mypracticespring.services;

import com.nkm.mypracticespring.common.Constant;
import com.nkm.mypracticespring.common.RedisAppManager;
import com.nkm.mypracticespring.dto.jwt.CreateJwtDto;
import com.nkm.mypracticespring.dto.jwt.TokenGeneratedDto;
import com.nkm.mypracticespring.enums.SessionStatus;
import com.nkm.mypracticespring.models.SessionModel;
import com.nkm.mypracticespring.models.ShopModel;
import com.nkm.mypracticespring.repositories.ISessionRepository;
import com.nkm.mypracticespring.utils.CommonUtils;
import com.nkm.mypracticespring.utils.JwtUtils;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Log4j2
@Service
public class CommonService {

    @Autowired
    private ISessionRepository ISessionRepository;

    @Autowired
    private RedisAppManager redisAppManager;

    public TokenGeneratedDto saveSession(ShopModel shopModel) {
        LocalDateTime now = LocalDateTime.now();

        SessionModel sessionModel = new SessionModel();
        sessionModel.setUserId(shopModel.getId());
        sessionModel.setStatus(SessionStatus.ACTIVE);
        sessionModel.setCreatedTime(now);
        sessionModel.setUpdatedTime(now);
        sessionModel.setExpireTime(now.plus(Constant.REFRESH_TOKEN_EXPIRE_TIME.toMillis(), ChronoUnit.MILLIS));
        ISessionRepository.save(sessionModel);

        String sessionId = sessionModel.getId();
        String key = CommonUtils.keySession(shopModel.getId(), sessionId);
        try {
            redisAppManager.addSession(key, SessionStatus.ACTIVE.name(), Constant.REFRESH_TOKEN_EXPIRE_TIME);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return JwtUtils.generateToken(new CreateJwtDto(shopModel.getId(), shopModel.getEmail(), sessionId));
    }

    public void removeSession(String userId, String sessionId) {
        ISessionRepository.deleteById(sessionId);
        String key = CommonUtils.keySession(userId, sessionId);
        try {
            redisAppManager.removeSession(key);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public String getStatusSession(String userId, String sessionId) {
        String keySession = CommonUtils.keySession(userId, sessionId);
        String sessionStatus = this.getSessionFromRedis(keySession);

        if (sessionStatus == null) {
            sessionStatus = this.handleMissingSessionInRedis(sessionId, keySession);
        }

        log.info("sessionStatus: {}", sessionStatus);
        return sessionStatus;
    }

    private String getSessionFromRedis(String keySession) {
        try {
            return redisAppManager.getSession(keySession);
        } catch (Exception e) {
            log.error("Error fetching session from Redis for key {}: {}", keySession, e.getMessage());
            return null;
        }
    }

    private String handleMissingSessionInRedis(String sessionId, String keySession) {
        Optional<SessionModel> sessionModel = ISessionRepository.findById(sessionId);
        if (sessionModel.isPresent() &&
                sessionModel.get().getExpireTime().isAfter(LocalDateTime.now()) &&
                sessionModel.get().getStatus() == SessionStatus.ACTIVE) {
            updateRedisWithActiveSession(keySession, sessionModel.get());
            return sessionModel.get().getStatus().name();
        } else {
            handleExpiredSession(sessionId, keySession);
            return StringUtils.EMPTY;
        }
    }

    private void updateRedisWithActiveSession(String keySession, SessionModel sessionModel) {
        Duration expireTime = Duration.between(LocalDateTime.now(), sessionModel.getExpireTime());
        try {
            redisAppManager.addSession(keySession, SessionStatus.ACTIVE.name(), expireTime);
        } catch (Exception e) {
            log.error("Error updating Redis with active session for key {}: {}", keySession, e.getMessage());
        }
    }

    private void handleExpiredSession(String sessionId, String keySession) {
        try {
            redisAppManager.addSession(keySession, StringUtils.EMPTY, Duration.ofSeconds(10));
        } catch (Exception e) {
            log.error("Error adding empty session to Redis for key {}: {}", keySession, e.getMessage());
        }
        ISessionRepository.deleteById(sessionId);
    }

}
