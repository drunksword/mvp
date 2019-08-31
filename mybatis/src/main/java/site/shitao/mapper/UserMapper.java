package site.shitao.mapper;

import site.shitao.User;

/**
 * Created by shitao on 2019/8/31.
 */
public interface UserMapper {
    public int insert(User user);
    public User select(int id);
}
