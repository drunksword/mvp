package site.shitao.redis.subpub.spring;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

/**
 * Author: tao.shi
 * Created: 9/11
 */
@Component
public class RedisKeyListener implements MessageListener {


    @Override
    public void onMessage(Message message, byte[] bytes) {
        byte[] body = message.getBody();//请使用valueSerializer
        byte[] channel = message.getChannel();

        String itemValue = new String(body);
        String topic = new String(channel);

        // clear local cache
        System.out.println(String.format("\n>>>>>>\n\n%s\n\n>>>>>>>>\n", "clear local cache, body = " + itemValue));
    }

}
