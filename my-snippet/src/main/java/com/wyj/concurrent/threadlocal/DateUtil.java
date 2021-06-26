package com.wyj.concurrent.threadlocal;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * SimpleDateFormat设置为静态变量，高并发场景下回出现异常，因为SimpleDateFormat的parse方法找那个有几处ParsePosition类中的索引赋值的操作，
 * 多线程共性SimpleDateFormat实例，会互相影响
 * 解决办法：
 * 1.每次都初始化一个SimpleDateFormat实例
 * 2.对解析加锁，影响性能
 * 3.使用ThreadLocal隔离，这个比较推荐
 * 4.使用java8提供的DateTimeFormatter，这是线程安全的，也推荐使用
 */
public class DateUtil {

    private static DateFormat sdf  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private static ThreadLocal<DateFormat> threadLocalSdf = ThreadLocal.withInitial(
            ()-> new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));


    /**
     * 使用threadLocal的格式化
     *
     * @author W.Y.J
     * @Date 2021/6/26 2:29 下午
     * @param dateStr
     * @return java.util.Date
     */
    public static Date threadLocalParse(String dateStr) {
        Date date = null;
        try {
            date = threadLocalSdf.get().parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }


    /**
     * 公用一个parse  会出现异常
     * @author W.Y.J
     * @Date 2021/6/26 2:31 下午
     * @param dateStr
     * @return java.util.Date
     *
     */
    public static Date parse(String dateStr) {
        Date date = null;
        try {
            date = sdf.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }


    public static void main(String[] args) {

        ExecutorService service = Executors.newFixedThreadPool(20);

        for (int i = 0; i < 20; i++) {
            service.execute(()->{
                System.out.println(DateUtil.parse("2019-06-01 16:34:30"));
//                System.out.println(DateUtil.threadLocalParse("2019-06-01 16:34:30"));
            });
        }
        service.shutdown();
    }
}
