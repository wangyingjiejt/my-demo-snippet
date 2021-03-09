package com.wyj;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.List;

public class Demo {
    public static void main(String[] args) {

        String a ="a,b,,,,";
//        int length = StringUtils.split(a, ",").length;
        String[] split = a.split(",");
        System.out.println(split.length);
    }

    private static void changeA(String a) {
        System.out.println(a.toString());
        a="bbb";
        return;
    }




}
