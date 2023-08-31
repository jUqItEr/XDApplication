package com.dita.xd.driver;

import com.dita.xd.service.implementation.FeedServiceImpl;

import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * @deprecated For testing
 * */
public class FeedDriver {
    public static void main(String[] args) {
        FeedServiceImpl impl = new FeedServiceImpl();

        // 피드 생성
        //System.out.println(impl.create("123", "테스트입니다"));

        // 피드 가져오기
        impl.getFeeds("123").forEach(System.out::println);
        // impl.getFeeds("123", Timestamp.valueOf("2023-08-30 23:59:59")).forEach(System.out::println);

        //impl.getFeeds("123", Timestamp.valueOf("2023-08-29 00:00:00"), Timestamp.valueOf("2023-08-30 23:59:59")).forEach(System.out::println);

        //impl.getFeeds("123", Timestamp.valueOf(LocalDateTime.now())).forEach(System.out::println);

        //impl.search("밥").forEach(System.out::println);
    }
}
