package site.shitao.redis.subpub.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.util.concurrent.Executor;

/**
 * Author: tao.shi
 * Created: 9/12
 */
@Configuration
public class Config {

    @Bean
    public RedisTemplate redisTemplate(JedisConnectionFactory connectionFactory){
        RedisTemplate redisTemplate = new RedisTemplate<>();
        redisTemplate.setKeySerializer(new StringRedisSerializer()); //默认jdk序列化会添加特殊字符，参考https://blog.csdn.net/lkforce/article/details/91972776
        redisTemplate.setConnectionFactory(connectionFactory);

        return redisTemplate;
    }

    @Autowired
    MessageListener redisKeyListener;

    @Bean
    public Executor executor(){
        Executor executor = new ThreadPoolTaskScheduler();
        ((ThreadPoolTaskScheduler) executor).setPoolSize(3);
        return executor;
    }

    @Bean(destroyMethod = "destroy")
    public RedisMessageListenerContainer container(JedisConnectionFactory connectionFactory, Executor executor){
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setTaskExecutor(executor);

        PatternTopic patternTopic = new PatternTopic("__keyspace@*__:redis_tao_shi*");
        container.addMessageListener(redisKeyListener, patternTopic);

        return container;
    }
}
