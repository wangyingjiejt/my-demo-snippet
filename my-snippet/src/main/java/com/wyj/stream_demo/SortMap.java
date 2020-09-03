package com.wyj.stream_demo;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 遍历map
 * @author W.Y.J
 * @Date 2020/9/3 10:23 上午
 */
public class SortMap {

    public static void main(String[] args) {
        final Map<String, Integer> wordCounts = new HashMap<>();
        wordCounts.put("USA", 100);
        wordCounts.put("jobs", 200);
        wordCounts.put("software", 50);
        wordCounts.put("technology", 70);
        wordCounts.put("opportunity", 200);
        iteraltMap(wordCounts);
        System.out.println("---------");

        final Map<String, Integer> sortedByCount = wordCounts.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
        iteraltMap(sortedByCount);

        System.out.println("-------");


        final Map<Integer, String> levelMap = new HashMap<>();
        levelMap.put(100,"USA");
        levelMap.put(38,"USA");
        levelMap.put(990,"USA");
        levelMap.put(45,"USA");
        levelMap.put(123,"USA");

        levelMap.keySet().stream().sorted().forEach(System.out::println);
    }


    public static void iteraltMap(Map map){
        map.forEach((k,v)->{
            System.out.println(k+"----"+v);
        });

    }
}
