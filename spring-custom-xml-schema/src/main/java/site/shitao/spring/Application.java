package site.shitao.spring;

import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Author: tao.shi@xxx.com
 * Created: 7/20
 */
public class Application {
    @Test
    public void main(){
        ApplicationContext context = new ClassPathXmlApplicationContext("application.xml");
        MethodLog methodLog = (MethodLog) context.getBean("methodLog");

        System.out.println(String.format("\n>>>>>>\n\n%s\n\n>>>>>>>>\n", JSON.toJSONString(methodLog)));
    }
}
