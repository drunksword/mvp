package site.shitao.spring;

import com.alibaba.fastjson.JSON;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import site.shitao.User;
import site.shitao.mapper.UserMapper;

/**
 * Created by shitao on 2019/8/31.
 */
public class MapperTest {

    public static void main(String... args){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-mybatis.xml");
        UserMapper userMapper = (UserMapper) applicationContext.getBean("userMapper");
        User user = new User();
        user.setName("shitao");
        int cnt = userMapper.insert(user);

        user = userMapper.select(1);

        System.out.println(JSON.toJSONString(user));
    }
}
