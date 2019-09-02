package site.shitao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * Author: tao.shi
 * Created: 9/2
 */
@Component
public class Sender {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    //发送消息方法
    public void send() {
        kafkaTemplate.send("content-process-transcode-record", "shitao");
    }
}
