package site.shitao.spring.base.circularref;

import lombok.Data;

/**
 * Created by shitao on 2019/10/15.
 */
@Data
public class BeanB {

    private String name = "beanB";

    private BeanA beanA;
}
