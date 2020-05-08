package com.wyj.stream_demo;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;
import org.junit.Test;

/**
 * apache容器工具，可以返回两个参数(Pair)或者三个参数(Triple)
 * @author wangyj
 * @Date 2020/5/8 11:30 下午
 */
public class ApacheContainerDemo {


    @Test
    public void test(){
        Pair<String, Integer> nameAndAge = getNameAndAge();
        System.out.println(nameAndAge.getLeft());
        System.out.println(nameAndAge.getRight());

        System.out.println("________________");

        Triple<String, Integer, String> nameAndAgeGender = getNameAndAgeGender();
        System.out.println(nameAndAgeGender.getLeft());
        System.out.println(nameAndAgeGender.getMiddle());
        System.out.println(nameAndAgeGender.getRight());

    }

    /**
     * 实际返回两个参数
     * @return
     */
    private Pair<String,Integer> getNameAndAge(){
        return new ImmutablePair("wangyj",28);
    }

    /**
     * 返回三个参数
     * @return
     */
    private Triple<String,Integer,String> getNameAndAgeGender(){
        return new ImmutableTriple<>("wangyj",28,"man");
    }
}
