package site.shitao.spring.base.lifecycle;

import com.alibaba.fastjson.JSON;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Author: tao.shi@xxx.com
 * Created: 7/25
 */
public class LifecycleApp {

    public static void main(String... args){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("application.xml");

        TestBean bean = (TestBean) applicationContext.getBean("shitao");

        ((ClassPathXmlApplicationContext) applicationContext).destroy();

        ((ClassPathXmlApplicationContext) applicationContext).registerShutdownHook();

        System.out.println(String.format("\n>>>>>>\n\n%s\n\n>>>>>>>>\n", JSON.toJSONString(bean)));

        bean = TestBean.builder().job("").name("shitao").build();
    }
}
