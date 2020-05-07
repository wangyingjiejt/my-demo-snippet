package com.wyj.new_data_structures;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import redis.clients.jedis.GeoUnit;
import redis.clients.jedis.Jedis;

import java.util.ResourceBundle;

/**
 * redis geospatial(地理空间)类型数据
 *
 * @author wangyj
 * @Date 2020/5/5 6:52 下午
 */
public class GeospatialDemo {


    private static Jedis myJedisClient;

    /**
     * 获取一个客户端实例
     * @author wangyj
     * @Date 2020/5/5 7:30 下午
     */
    @BeforeClass
    public static void getMyJedisClient(){
        ResourceBundle redisProperties = ResourceBundle.getBundle("redis-local");
        String url= redisProperties.getString("redis.url");
        Integer port = Integer.parseInt(redisProperties.getString("redis.port"));
        myJedisClient= new Jedis(url,port);
        System.out.println("初始化客户端连接...");
    }

    /**
     * 关闭客户端连接
     * @author wangyj
     * @Date 2020/5/5 11:23 下午
     */
    @AfterClass
    public static  void closeJedisClient(){
        myJedisClient.close();
        System.out.println("关闭客户端连接...");
    }


    /**
     * 清空redis并塞入测试数据
     * @author wangyj
     * @Date 2020/5/5 11:20 下午
     */
    @Before
    public void initDb() {
        //每次连接后首先清除原有数据
        myJedisClient.flushDB();

        //添加几个城市测试数据，可以在网上找到一些城市坐标
        myJedisClient.geoadd("maincity", 116.405285, 39.904989, "beijing");
        myJedisClient.geoadd("maincity", 121.472644, 31.231706, "shanghai");
        myJedisClient.geoadd("maincity", 117.190182, 39.125596, "tianjin");
        myJedisClient.geoadd("maincity", 113.122717, 23.028762, "foshan");
        System.out.println("加入测试数据...");
    }


    /**
     * 获取一个地理空间的坐标
     * @author wangyj
     * @Date 2020/5/5 11:25 下午
     */
    @Test
    public void geopos(){
        //获取北京的空间坐标
        System.out.println(myJedisClient.geopos("maincity", "beijing", "shanghai"));
    }


    /**
     * 计算两个坐标的直线距离
     * @author wangyj
     * @Date 2020/5/5 11:29 下午
     * @param
     * @return void
     */
    @Test
    public void geoDist(){
        //计算北京到佛山的距离
        Double distance = myJedisClient.geodist("maincity", "beijing", "foshan", GeoUnit.KM);
        System.out.println("北京到佛山的直线距离为"+distance+"KM");
    }


    /**
     * 以给定的经纬度为中心， 找出某一半径内的元素
     * @author wangyj
     * @Date 2020/5/5 11:32 下午
     */
    @Test
    public void georadius(){
        //取香河县（117.00634，39.76133）作为测试点,查看距离香河200公里以内的有哪些元素
        myJedisClient.georadius("maincity", 117.00634, 39.76133, 200, GeoUnit.KM)
                .forEach(g-> System.out.println(g.getMemberByString()));


        System.out.println("--------------------");
        //如果是已经加入到地理空间的位置，可以直接用成员名进行检索
        myJedisClient.georadiusByMember("maincity","beijing",200,GeoUnit.KM)
                .forEach(g-> System.out.println(g.getMemberByString()));

        /**
         * console log
         * 初始化客户端连接...
         * 加入测试数据...
         * tianjin
         * beijing
         * --------------------
         * beijing
         * tianjin
         * 关闭客户端连接...
         */
    }




    /**
     * 找出位于指定元素周围的其他元素
     * @author wangyj
     * @Date 2020/5/7 10:50 下午
     */
    @Test
    public void georadiusByMember(){
        //取香河县（117.00634，39.76133）作为测试点,查看距离香河200公里以内的有哪些元素
        myJedisClient.georadiusByMember("maincity", "beijing", 200, GeoUnit.KM)
                .forEach(g-> System.out.println(g.getMemberByString()));


        System.out.println("--------------------");
        //如果是已经加入到地理空间的位置，可以直接用成员名进行检索
        myJedisClient.georadiusByMember("maincity","beijing",200,GeoUnit.KM)
                .forEach(g-> System.out.println(g.getMemberByString()));

        /**
         * console log
         * 初始化客户端连接...
         * 加入测试数据...
         * tianjin
         * beijing
         * --------------------
         * beijing
         * tianjin
         * 关闭客户端连接...
         */
    }

    /**
     * GeoHash 算法将二维的经纬度数据映射到一维的整数，这样所有的元素都将在挂载到一条线上，距离靠近的二维坐标映射到一维后的点之间距离也会很接近。
     * 当我们想要计算「附近的人时」，首先将目标位置映射到这条线上，然后在这个一维的线上获取附近的点就行了
     * @author wangyj
     * @Date 2020/5/7 11:06 下午
     */
    @Test
    public void geohash(){
        System.out.println(myJedisClient.geohash("maincity","beijing", "tianjin", "shanghai"));
        /**
         * console log
         * [wx4g0b7xrt0, wwgqddx7150, wtw3sjt9vg0]
         */
    }
}
