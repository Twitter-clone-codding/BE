package com.twitter.clone.twitterclone.auth.authlogin.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;

@RequiredArgsConstructor
@Repository
public class EmailCodeVerifyRepository {
    private final String PREFIX = "code:";
    private final int LIMIT_TIME = 3 * 60;

    private final StringRedisTemplate stringRedisTemplate;

    public void createEmailCodeVerify(String email, String emailCode) {
        stringRedisTemplate.opsForValue()
                .set(PREFIX + email, emailCode, Duration.ofSeconds(LIMIT_TIME));
    }

    public String getEmailCodeVerify(String email) {
        return stringRedisTemplate.opsForValue().get(PREFIX + email);
    }

    public void removeEmailCodeVerify(String email) {
        stringRedisTemplate.delete(PREFIX + email);
    }

    public boolean hasKey(String email) {
        return stringRedisTemplate.hasKey(PREFIX + email);
    }
}
