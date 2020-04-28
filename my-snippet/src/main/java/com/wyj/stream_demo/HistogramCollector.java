package com.wyj.stream_demo;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

/**
 * 自定义收集器
 * 输入一组数据，输出直方图统计数据
 * https://blog.indrek.io/articles/creating-a-collector-in-java-8/
 * @author wangyj
 * @date 2020/1/11 21:50
 */
public class HistogramCollector implements Collector<Double, Map<Integer, Integer>, Map<Integer, Integer>> {


    //直方图的组距
    private int bucketSize;

    public HistogramCollector(int bucketSize) {
        this.bucketSize = bucketSize;
    }


    /**
     * 提供一个存放中间结果的空容器
     */
    @Override
    public Supplier<Map<Integer, Integer>> supplier() {
        return HashMap::new;
    }

    /**
     * 定义规则如何将流的元素归并到结果容器中
     * 第一个参数是存放间结果的容器
     * 第二个参数是流中的下一个元素
     */
    @Override
    public BiConsumer<Map<Integer, Integer>, Double> accumulator() {
        return (map, val) -> map.merge((int) (val / bucketSize), 1,
                (a, b) -> a + 1);
    }


    /**
     * 在并行流中，定义如何将多个accumulator合并成一个
     */
    @Override
    public BinaryOperator<Map<Integer, Integer>> combiner() {
        return (map1, map2) -> {
            map2.forEach((key, value) -> map1.merge(key, value, (a, b) -> a + b));
            return map1;
        };
    }

    /**
     * accumulator返回的已经是需要的结果，finisher返回自身即可
     */
    @Override
    public Function<Map<Integer, Integer>, Map<Integer, Integer>> finisher() {
        return Function.identity();
    }


    /**
     * 定义流的特征，告诉收集器在处理流的过程中根据特征进行何种优化
     */
    @Override
    public Set<Characteristics> characteristics() {
        return Collections.unmodifiableSet(EnumSet.of(Collector.Characteristics.UNORDERED,
                Collector.Characteristics.IDENTITY_FINISH));
    }


    /**
     * 静态获取一个hsitogram对象
     * @param bucketSize
     * @return
     */
    public static HistogramCollector toHistogram(int bucketSize) {
        return new HistogramCollector(bucketSize);
    }


    public static void main(String[] args) {
        List<Double> numbers = Arrays.asList(1.0, 3.1, 1.4, 1.7, 1.4, 5.4, 9.9,6.2);
        Map<Integer, Integer> histogram = numbers.stream().collect(toHistogram(2));
        System.out.println(histogram);
    }

    /**
     * print result:
     * {0=4, 1=1, 2=1, 3=1, 4=1}
     */
}
