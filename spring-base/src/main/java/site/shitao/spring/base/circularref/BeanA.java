package site.shitao.spring.base.circularref;

import lombok.Data;

/**
 * Created by shitao on 2019/10/15.
 */
@Data
public class BeanA {

    private String name = "beanA";

    private BeanB beanB;
}
