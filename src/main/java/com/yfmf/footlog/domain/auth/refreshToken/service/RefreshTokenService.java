package com.yfmf.footlog.domain.auth.refreshToken.service;

import com.yfmf.footlog.domain.auth.refreshToken.exception.RedisSaveFailed;
import com.yfmf.footlog.domain.member.domain.Member;
import com.yfmf.footlog.domain.member.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class RefreshTokenService {
    private final StringRedisTemplate redisTemplate;
    private final MemberRepository memberRepository;

    public RefreshTokenService(StringRedisTemplate redisTemplate, MemberRepository memberRepository) {
        this.redisTemplate = redisTemplate;
        this.memberRepository = memberRepository;
    }

    // 리프레시 토큰을 Redis에 저장(유효시간 설정)
    public void saveRefreshToken(String userId, String refreshToken, Long expirationTime) {
        try {
            redisTemplate.opsForValue().set(userId + ":token", refreshToken, expirationTime, TimeUnit.MILLISECONDS);
            log.info("사용자 ID: {}에 대한 리프레시 토큰 저장 완료. 리프레시 토큰: {}", userId, refreshToken);
        } catch (Exception e) {
            log.error("Redis에 리프레시 토큰 저장 실패", e);
            throw new RedisSaveFailed("리프레시 토큰 저장 실패", "[RefreshTokenService] saveRefreshToken");
        }

        String savedToken = redisTemplate.opsForValue().get(userId + ":token");
        if (savedToken == null) {
            log.error("사용자 ID: {}에 대한 리프레시 토큰을 Redis에서 찾을 수 없습니다.", userId);
            throw new RedisSaveFailed("Redis에서 리프레시 토큰을 찾을 수 없습니다.", "[RefreshTokenService] saveRefreshToken");
        }
    }

    // IP 주소 저장
    public void saveIp(String userId, String ip) {
        redisTemplate.opsForValue().set(userId + ":ip", ip);
        log.info("사용자 ID: {}에 대한 IP 주소 저장 완료. IP: {}", userId, ip);
    }

    // 리프레시 토큰 가져오기
    public String getRefreshToken(String userId) {
        log.info("사용자 ID: {}에 대한 리프레시 토큰 조회 요청", userId);
        return redisTemplate.opsForValue().get(userId + ":token");
    }

    // 저장된 IP 가져오기
    public String getStoredIp(String userId) {
        log.info("사용자 ID: {}에 대한 저장된 IP 주소 조회 요청", userId);
        return redisTemplate.opsForValue().get(userId + ":ip");
    }

    // 리프레시 토큰 삭제
    public void deleteRefreshToken(String userId) {
        try {
            redisTemplate.delete(userId + ":token");
            redisTemplate.delete(userId + ":ip");
            log.info("사용자 ID: {}에 대한 리프레시 토큰 및 IP 주소 삭제 완료", userId);
        } catch (Exception e) {
            log.error("Redis에서 리프레시 토큰 또는 IP 삭제 실패", e);
            throw new RedisSaveFailed("리프레시 토큰 또는 IP 삭제 실패", "[RefreshTokenService] deleteRefreshToken");
        }
    }

    // 리프레시 토큰을 기반으로 사용자 조회
    public Optional<Member> getMemberByToken(String refreshToken) {
        log.info("리프레시 토큰으로 사용자 조회 시도. 리프레시 토큰: {}", refreshToken);
        String userId = redisTemplate.opsForValue().get(refreshToken + ":token");  // 토큰을 사용해 유저 ID를 가져옴
        if (userId == null) {
            log.error("Redis에서 리프레시 토큰을 찾을 수 없습니다.");
            throw new RedisSaveFailed("Redis에서 해당 리프레시 토큰을 찾을 수 없습니다.", "[RefreshTokenService] getMemberByToken");
        }

        // MemberRepository를 통해 Member 정보 조회
        log.info("사용자 ID: {}에 대한 사용자 정보 조회 시도", userId);
        return memberRepository.findById(Long.valueOf(userId));
    }
}
