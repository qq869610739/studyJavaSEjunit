package com.junit.tools;

import redis.clients.jedis.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kangkang on 2017/4/13.
 */
public class RedisClient {

    private Jedis jedis;//非切片客户端连接
    private static JedisPool jedisPool;//非切片连接
    private ShardedJedis shardedJedis;//切片额客户端
    private ShardedJedisPool shardedJedisPool;//切片连接池


    //Redis服务器IP
    private static String ADDR = "118.89.139.135";

    //Redis的端口号
    private static int PORT = 6379;

     //访问密码
    private static String AUTH = "myredis";

    //可用连接实例的最大数目，默认值为8；
    //如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
    private static int MAX_ACTIVE = 1024;

    //控制一个pool最多有多少个状态为idle(空闲的)的jedis实例，默认值也是8。
    private static int MAX_IDLE = 200;

    //等待可用连接的最大时间，单位毫秒，默认值为-1，表示永不超时。如果超过等待时间，则直接抛出JedisConnectionException；
    private static int MAX_WAIT = 10000;

    private static int TIMEOUT = 10000;
    //在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
    private static boolean TEST_ON_BORROW = true;

    /**
     * 初始化Redis连接池
     */
    static {
        try {
            //池基本配置
            JedisPoolConfig config=new JedisPoolConfig();
            config.setMaxTotal(MAX_ACTIVE);
            config.setMaxIdle(MAX_IDLE);
            //等待时间
            config.setMaxWaitMillis(MAX_WAIT);
            config.setTestOnBorrow(TEST_ON_BORROW);
            jedisPool = new JedisPool(config,ADDR,PORT,TIMEOUT,AUTH);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

//    /**
//     * 初始化切片池
//     */
//    private  void  initinalPool()
//    {
//        //池基本配置
//        JedisPoolConfig config=new JedisPoolConfig();
//        config.setMaxTotal(MAX_ACTIVE);
//        config.setMaxIdle(MAX_IDLE);
//        //等待时间
//        config.setMaxWaitMillis(MAX_WAIT);
//        config.setTestOnBorrow(TEST_ON_BORROW);
//        jedisPool = new JedisPool(config,ADDR,PORT,TIMEOUT,AUTH);
//    }
//
//    /**
//     * 初始化切片池
//     */
//    private void initialShardedPool() {
//        // 池基本配置
//        JedisPoolConfig config = new JedisPoolConfig();
//        config.setMaxTotal(MAX_ACTIVE);
//        config.setMaxIdle(5);
//        config.setMaxWaitMillis(1000);
//        config.setTestOnBorrow(false);
//        //slave链接
//        List<JedisShardInfo> shards=new ArrayList<JedisShardInfo>();
//        shards.add(new JedisShardInfo("118.89.139.135",6379,"master"));
//        //够造池
//        shardedJedisPool=new ShardedJedisPool(config,shards);
//    }


    public synchronized  static  Jedis getJedis(){
        try {
            if (jedisPool!=null)
            {
                Jedis resource=jedisPool.getResource();
                return resource;
            }else {
                return null;
            }
        }catch (Exception e)
        {
            e.printStackTrace();
            return  null;
        }
    }
}
