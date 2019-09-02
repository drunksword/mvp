package site.shitao;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * Author: tao.shi
 * Created: 9/2
 */
@SpringBootApplication
public class KafkaApp {
    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(KafkaApp.class, args);

        Sender sender = applicationContext.getBean(Sender.class);

        for (int i = 0; i < 3; i++) {
            sender.send();
        }
    }
}
