package site.shitao.redis.subpub;

import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.JedisPubSub;

/**
 * Author: tao.shi@ximalaya.com
 * Created: 8/14
 */
@Slf4j
public class MyListener extends JedisPubSub {

    @Override
    public void onPMessage(String pattern, String channel, String message) {
        log.info("{}, {}, {}", pattern, channel, message);
    }
    @Override
    public void onPUnsubscribe(String pattern, int subscribedChannels) {
    }
    @Override
    public void onPSubscribe(String pattern, int subscribedChannels) {
    }

}
