package site.shitao.spring.base.lifecycle;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * Author: tao.shi@xxx.com
 * Created: 11/18
 */
@Slf4j
public class BeanPostProcessorTest implements BeanPostProcessor {

  @Override
  public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
    if(beanName.equalsIgnoreCase("beanLifecycle"))
      log.warn(">>>>>\npostProcessBeforeInitialization\n>>>>>");
    return bean;
  }

  @Override
  public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
    if(beanName.equalsIgnoreCase("beanLifecycle")) log.warn(">>>>>\npostProcessAfterInitialization\n>>>>>");
    return bean;
  }
}
