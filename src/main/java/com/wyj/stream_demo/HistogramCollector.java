package com.wyj.stream_demo;

import java.util.HashMap;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.Map;
/**
 * 自定义收集器
 * @author wangyj
 * @date 2020/1/11 21:50
 */
public class HistogramCollector implements Collector<Double,Map<Integer,Integer>,Map<Integer,Integer>>{



    private int bucketSize;

    public HistogramCollector(int bucketSize) {
        this.bucketSize = bucketSize;
    }


    /**
     * 提供一个中间结果的容器
     */
    @Override
    public Supplier<Map<Integer, Integer>> supplier() {
        return HashMap::new;
    }

    @Override
    public BiConsumer<Map<Integer, Integer>, Double> accumulator() {
        return (map, val) -> map.merge((int)(val / bucketSize), 1,
                (a, b) -> a + 1);
    }

    @Override
    public BinaryOperator<Map<Integer, Integer>> combiner() {
        return null;
    }

    @Override
    public Function<Map<Integer, Integer>, Map<Integer, Integer>> finisher() {
        return null;
    }

    @Override
    public Set<Characteristics> characteristics() {
        return null;
    }
}
