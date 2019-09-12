package site.shitao.redis.subpub.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * Author: tao.shi
 * Created: 9/12
 */
public class Main {
    public static void main(String... args) throws InterruptedException {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:application-context.xml");

        RedisTemplate redisTemplate = (RedisTemplate<String, String>) applicationContext.getBean("redisTemplate");

        int id = 100;
        redisTemplate.opsForValue().set(getKey(id), "holy");

        Thread.sleep(10000000);
    }

    private static String getKey(long i){
        return "redis_tao_shi" + i;
    }
}
