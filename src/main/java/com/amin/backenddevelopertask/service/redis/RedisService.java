package com.amin.backenddevelopertask.service.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class RedisService {

    private final StringRedisTemplate stringRedisTemplate;

    @Value("${redis.user.cache.expiration:600}") // 10 dəqiqə default
    private long userCacheExpiration;

    public void cacheUser(String email, String adSoyad) {
        stringRedisTemplate.opsForValue().set(
                buildUserKey(email),
                adSoyad,
                Duration.ofSeconds(userCacheExpiration)
        );
    }

    public String getUserByEmail(String email) {
        return stringRedisTemplate.opsForValue().get(buildUserKey(email));
    }

    private String buildUserKey(String email) {
        return "user:" + email;
    }
}


