package site.shitao;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * Author: tao.shi
 * Created: 9/2
 */
@Component
@Slf4j
public class Receiver {

    @KafkaListener(topics = "content-process-transcode-record")
    public void listen(ConsumerRecord<?, ?> record){
        Object message = record.value();

        log.info("--------------message: {}", message);
    }
}
