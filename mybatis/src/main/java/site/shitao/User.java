package site.shitao;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by shitao on 2019/8/31.
 */
@Data
@AllArgsConstructor
public class User {
    private int id;
    private String name;

    public User(){}
}
