package site.shitao.spring.base.circularref;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * Author: tao.shi@xxx.com
 * Created: 11/18
 */
@Slf4j
@Component
public class BeanPostProcessorTest implements BeanPostProcessor {

  @Override
  public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
    return bean;
  }

  @Override
  public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
    if(beanName.equalsIgnoreCase("beanA")) {
      BeanA beanA = new BeanA();
      beanA.setName("beanA1");
      return beanA;
    }
    return bean;
  }
}
