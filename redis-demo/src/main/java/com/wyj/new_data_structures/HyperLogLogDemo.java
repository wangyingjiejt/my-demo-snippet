package com.wyj.new_data_structures;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Hyperloglog基数统计
 *
 * @author wangyj
 * @Date 2020/5/7 11:17 下午
 */
public class HyperLogLogDemo {


    private static Jedis myJedisClient;

    /**
     * 在 Redis 里面，每个 HyperLogLog 键只需要花费 12 KB 内存，就可以计算接近 2^64 个不同元素的基数。
     * 这就非常适合统计元素比较多或者体积比较大的场景，大小固定，占用空间少，但是会损失一定的精度，且无法还原所有元素
     */

    /**
     * 获取一个客户端实例
     *
     * @author wangyj
     * @Date 2020/5/5 7:30 下午
     */
    @BeforeClass
    public static void getMyJedisClient() {
        ResourceBundle redisProperties = ResourceBundle.getBundle("redis-local");
        String url = redisProperties.getString("redis.url");
        Integer port = Integer.parseInt(redisProperties.getString("redis.port"));
        myJedisClient = new Jedis(url, port);
        System.out.println("初始化客户端连接...");
    }

    /**
     * 关闭客户端连接
     *
     * @author wangyj
     * @Date 2020/5/5 11:23 下午
     */
    @AfterClass
    public static void closeJedisClient() {
        myJedisClient.close();
        System.out.println("关闭客户端连接...");
    }

    @Before
    public void flushdb() {
        myJedisClient.flushDB();
        System.out.println("清空缓存");
    }

    /**
     * 统计基数
     * 基数可以理解为一个数据集中不重复元素的个数
     *
     * @param
     * @return void
     * @author wangyj
     * @Date 2020/5/7 11:24 下午
     */
    @Test
    public void hyperloglog() {
        String [] numArr=new String[200];
        Set<String> noRepeatSet =new HashSet<>();
        //生成200个0-100的数字，统计不重复的数字
        Random random = new Random();
        List<Integer> collect = random.ints(0, 100)
                .limit(200)
                .boxed()
                .collect(Collectors.toList());

        for (int i=0;i<collect.size();i++){
            numArr[i]=collect.get(i)+"";
            noRepeatSet.add(numArr[i]);
        }
        myJedisClient.pfadd("uv", numArr);
        System.out.println("不重复的真实个数 = " + noRepeatSet.size());
        System.out.println("hyperloglog统计数 = " + myJedisClient.pfcount("uv"));
        /**
         * console log
         *
         * 不重复的真实个数 = 92
         * hyperloglog统计数 = 92
         */
    }
}
