package com.twitter.clone.twitterclone.tweet.service;

import com.twitter.clone.twitterclone.global.util.RedisUtil;
import com.twitter.clone.twitterclone.tweet.model.response.HashTagResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TweetHashTagService {

    private final RedisUtil redisUtil;

    public List<HashTagResponse> getHashTagList(){

        int size = 10;

        List<HashTagResponse> rankHashtagList = new ArrayList<>();

        String hashTags = redisUtil.getString("hashTag");

        String[] hashTagList = hashTags.split(",");

        Map<String, Integer> hashTagCounts = new HashMap<>();
        for (String tag : hashTagList) {
            hashTagCounts.put(tag, hashTagCounts.getOrDefault(tag, 0) + 1);
        }

        // 해시태그 등장 횟수에 따른 정렬
        List<Map.Entry<String, Integer>> sortedEntries = new ArrayList<>(hashTagCounts.entrySet());
        sortedEntries.sort(Map.Entry.<String, Integer>comparingByValue().reversed());

        for (Map.Entry<String, Integer> entry : sortedEntries) {

            if(rankHashtagList.size() >= size+1) break;

            if(entry.getKey().isEmpty() || entry.getKey().isEmpty()) continue;
            rankHashtagList.add(new HashTagResponse("#"+entry.getKey(), entry.getValue()));
        }

        return rankHashtagList;
    }

}
