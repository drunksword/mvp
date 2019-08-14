package site.shitao.redis.subpub;

import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;

/**
 * Author: tao.shi@ximalaya.com
 * Created: 8/14
 */
@Slf4j
public class Subscribe {

    public static void main(String... args){
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        MyListener listener = new MyListener();

        new Thread(() -> {
            try {
                Thread.sleep(1000);

                Jedis jedis1 = new Jedis("127.0.0.1", 6379);

                jedis1.set("shitao", "holy");
                jedis1.get("shitao");
                jedis1.del("shitao");
            } catch (InterruptedException e) {
               log.error("what's your problem?", e);
            }
        }).start();

        jedis.psubscribe(listener, "__keyspace@*__:shitao");
    }

}
