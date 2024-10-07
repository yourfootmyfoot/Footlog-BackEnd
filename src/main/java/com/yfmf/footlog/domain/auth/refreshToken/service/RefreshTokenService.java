package com.yfmf.footlog.domain.auth.refreshToken.service;

import com.yfmf.footlog.domain.auth.refreshToken.exception.RedisSaveFailed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class RefreshTokenService {
    private final StringRedisTemplate redisTemplate;

    public RefreshTokenService(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    // 리프레시 토큰을 Redis에 저장(유효시간 설정)
    // 리프레시 토큰과 IP 주소 저장
    public void saveRefreshToken(String userId, String refreshToken, Long expirationTime) {
        try {
            // userId + ":token" 키로 리프레시 토큰 저장
            redisTemplate.opsForValue().set(userId + ":token", refreshToken, expirationTime, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            log.error("Failed to save refresh token in Redis", e);
            throw new RedisSaveFailed("리프레시 토큰 저장 실패", "[RefreshTokenService] saveRefreshToken");
        }

        String savedToken = redisTemplate.opsForValue().get(userId + ":token");
        if (savedToken == null) {
            log.error("Failed to retrieve saved refresh token from Redis");
            throw new RedisSaveFailed("Redis에서 리프레시 토큰을 찾을 수 없습니다.", "[RefreshTokenService] saveRefreshToken");
        }
    }

    // IP 주소 저장
    public void saveIp(String userId, String ip) {
        redisTemplate.opsForValue().set(userId + ":ip", ip);
    }

    // 리프레시 토큰 가져오기
    public String getRefreshToken(String userId) {
        return redisTemplate.opsForValue().get(userId + ":token");
    }

    // 저장된 IP 가져오기
    public String getStoredIp(String userId) {
        return redisTemplate.opsForValue().get(userId + ":ip");
    }

    // 리프레시 토큰 삭제
    public void deleteRefreshToken(String userId) {
        try {
            // 토큰 및 IP 삭제
            redisTemplate.delete(userId + ":token");
            redisTemplate.delete(userId + ":ip");
        } catch (Exception e) {
            log.error("Failed to delete refresh token or IP from Redis", e);
            throw new RedisSaveFailed("리프레시 토큰 또는 IP 삭제 실패", "[RefreshTokenService] deleteRefreshToken");
        }
    }
}
