package site.shitao.redis.lock;

import redis.clients.jedis.Jedis;

import java.util.Arrays;

/**
 * Author: tao.shi@ximalaya.com
 * Created: 8/20
 */
public class DistributedLock {

    Jedis jedis;

    String lua = "if redis.call(\"get\",KEYS[1]) == ARGV[1] then\n" +
            "    return redis.call(\"del\",KEYS[1])\n" +
            "else\n" +
            "    return 0\n" +
            "end";

    public DistributedLock(){
        jedis = new Jedis("127.0.0.1", 6379);
        jedis.connect();
    }

    public boolean acquire(String key){
        String res = jedis.set(key, String.valueOf(Thread.currentThread().getId()), "NX", "PX", 60 * 1000);
        return "OK".equals(res);
    }

    public boolean release(String key){
        String threadId = String.valueOf(Thread.currentThread().getId());
        Long val = (Long) jedis.eval(lua, Arrays.asList(key), Arrays.asList(threadId));
        return val > 0;
    }

}
