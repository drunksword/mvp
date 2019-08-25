package site.shitao.redis.lock;

import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCommands;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Arrays;

/**
 * Author: tao.shi@ximalaya.com
 * Created: 8/20
 */
public class DistributedLock {

    JedisPool jedisPool;

    String lua = "if redis.call(\"get\",KEYS[1]) == ARGV[1] then\n" +
            "    return redis.call(\"del\",KEYS[1])\n" +
            "else\n" +
            "    return 0\n" +
            "end";

    public DistributedLock(){
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxIdle(10);
        poolConfig.setTestOnBorrow(false);
        poolConfig.setMaxWaitMillis(10000);

        jedisPool = new JedisPool(poolConfig, "127.0.0.1", 6379, 2000);
    }

    public boolean acquire(String key){

        Jedis jedis = jedisPool.getResource();
        jedis.connect();

        String res = jedis.set(key, String.valueOf(Thread.currentThread().getId()), "NX", "PX", 60 * 1000);

        jedis.close();
        return "OK".equals(res);
    }

    public boolean release(String key){
        Jedis jedis = jedisPool.getResource();
        jedis.connect();

        String threadId = String.valueOf(Thread.currentThread().getId());
        Long val = (long) jedis.eval(lua, Arrays.asList(key), Arrays.asList(threadId));

        jedis.close();
        return val > 0;
    }

}
