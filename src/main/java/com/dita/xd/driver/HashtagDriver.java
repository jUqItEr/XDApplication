package com.dita.xd.driver;

import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @deprecated For testing
 * */
public class HashtagDriver {
    public static void main(String[] args) {
        String str = "오늘은 #맛있는_밥 을 먹었다 \n#TodayCuisine #Test #정규표현식";

        getHashtags(str).forEach(System.out::println);
    }

    private static boolean matches(String text) {
        return getMatcher(text).matches();
    }

    private static Matcher getMatcher(String text) {
        String regex = "(#+[a-zA-Zㄱ-ㅎ가-힣0-9(_)]+)";
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(text);
    }

    private static Vector<String> getHashtags(String text) {
        Vector<String> vec = new Vector<>();
        Matcher matcher = getMatcher(text);

        while (matcher.find()) {
            vec.addElement(matcher.group().substring(1));
        }
        return vec;
    }
}
