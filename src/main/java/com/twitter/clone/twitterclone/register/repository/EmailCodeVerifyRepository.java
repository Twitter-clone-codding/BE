package com.twitter.clone.twitterclone.register.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;

@RequiredArgsConstructor
@Repository
public class EmailCodeVerifyRepository {
    private final String PREFIX = "code:";  // (1)
    private final int LIMIT_TIME = 3 * 60;  // (2)

    private final StringRedisTemplate stringRedisTemplate;

    public void createEmailCodeVerify(String email, String emailCode) { //(3)
        stringRedisTemplate.opsForValue()
                .set(PREFIX + email, emailCode, Duration.ofSeconds(LIMIT_TIME));
    }

    public String getEmailCodeVerify(String email) { // (4)
        return stringRedisTemplate.opsForValue().get(PREFIX + email);
    }

    public void removeEmailCodeVerify(String email) { // (5)
        stringRedisTemplate.delete(PREFIX + email);
    }

    public boolean hasKey(String email) {  //(6)
        return stringRedisTemplate.hasKey(PREFIX + email);
    }
}
