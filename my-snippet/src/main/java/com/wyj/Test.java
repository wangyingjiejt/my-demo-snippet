package com.wyj;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class Test {


    public static void main(String[] args) {


//        LocalDateTime zero = LocalDate.now().atTime(0,0,0);
//        long longZero=zero.toEpochSecond(ZoneOffset.of("+8"));
//        System.out.println("zero:"+LocalDate.now()+"     " + longZero);
//        while (true) {
//            LocalDateTime now = LocalDateTime.now();
//            long longNow=now.toEpochSecond(ZoneOffset.of("+8"));
//            System.out.println("now:" + now +"    "+longNow);
//            System.out.println("no:" + (longNow - longZero) / 60);
//            System.out.println("-------------------------");
//            try {
//                Thread.sleep(2000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }

        System.out.println(String.valueOf(49).equals("49"));
    }



    /**
     * 获取当前时间所属评论池序号
     * @author wangyj
     * @date 2019/10/10 19:35
     * @param interval	评论池间隔单位分钟
     * @return int
     */
    public long getCommentPoolNo(int interval){
        //当天零点时的毫秒数
        long currentDaySec = LocalDate.now().atTime(0,0,0).toEpochSecond(ZoneOffset.of("+8"));
        long nowSec=LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8"));
        return (nowSec-currentDaySec)/interval*60;
    }

}
