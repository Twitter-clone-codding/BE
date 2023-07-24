package com.twitter.clone.twitterclone.global.util;


import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Component
public class RedisUtil {

    private final RedisTemplate myRedisTemplate;

    //Data type : String
    public void setString(String key, String value) {
        myRedisTemplate.opsForValue().set(key, value);
    }

    public String getString(String key) {
        if (myRedisTemplate.opsForValue().get(key) == null) {
            return "";
        }
        return myRedisTemplate.opsForValue().get(key).toString();
    }

    //Timer
    public void setString(String key, String value, long timer, TimeUnit time) {
        myRedisTemplate.opsForValue().set(key, value, timer, time);
    }

}