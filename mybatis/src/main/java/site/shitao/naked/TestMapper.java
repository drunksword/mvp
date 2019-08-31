package site.shitao.naked;

import com.alibaba.fastjson.JSON;
import site.shitao.User;
import site.shitao.mapper.UserMapper;

/**
 * Created by shitao on 2019/8/31.
 */
public class TestMapper {

    public static void main(String... args){
        UserMapper userMapper = MybatisUtil.getSqlSessionFactory().openSession().getMapper(UserMapper.class);
        User user = new User();
        user.setName("shitao");
        int cnt = userMapper.insert(user);

        user = userMapper.select(1);

        System.out.println(JSON.toJSONString(user));
    }
}
