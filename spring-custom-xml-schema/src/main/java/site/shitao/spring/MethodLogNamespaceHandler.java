package site.shitao.spring;


import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * Author: tao.shi@xxx.com
 * Created: 7/20
 */
public class MethodLogNamespaceHandler extends NamespaceHandlerSupport {

    @Override
    public void init() {
        registerBeanDefinitionParser("main", new MethodLogBeanDefinitionParser());
    }

}
