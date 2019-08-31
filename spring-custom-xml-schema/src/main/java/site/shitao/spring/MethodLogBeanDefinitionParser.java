package site.shitao.spring;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.w3c.dom.Element;

/**
 * Author: tao.shi@xxx.com
 * Created: 7/20
 */
public class MethodLogBeanDefinitionParser extends AbstractSingleBeanDefinitionParser {

    @Override
    protected Class getBeanClass(Element element){
        return MethodLog.class;
    }

    @Override
    protected void doParse(Element element, BeanDefinitionBuilder bean){
        String scan = element.getAttribute("scan");
        bean.addConstructorArgValue(scan);

        String isImportFootball = element.getAttribute("is-import-football");
        bean.addConstructorArgValue(Boolean.valueOf(isImportFootball));
    }

}
