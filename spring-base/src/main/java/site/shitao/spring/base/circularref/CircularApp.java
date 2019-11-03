package site.shitao.spring.base.circularref;

import com.alibaba.fastjson.JSON;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Author: tao.shi@xxx.com
 * Created: 7/25
 */
public class CircularApp {

    public static void main(String... args){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("application-circularref.xml");

        BeanA beanA = (BeanA) applicationContext.getBean("beanA");


        System.out.println(String.format("\n>>>>>>\n\n%s\n\n>>>>>>>>\n", JSON.toJSONString(beanA)));

    }
}
