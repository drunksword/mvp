package site.shitao.spring.base.circularref;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 * Created by shitao on 2019/10/15.
 */
@Aspect
@Component
public class AspectTest {

    @Before("execution(* site.shitao.spring.base.circularref.BeanA.*())")
    public void any() {
        System.out.println("holy");
    }
}
