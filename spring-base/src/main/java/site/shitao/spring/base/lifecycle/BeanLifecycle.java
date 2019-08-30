package site.shitao.spring.base.lifecycle;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * Author: tao.shi@xxx.com
 * Created: 11/18
 */
@Slf4j
public class BeanLifecycle implements InitializingBean, BeanNameAware, BeanFactoryAware, DisposableBean {

  @PostConstruct
  public void postContruct(){
    log.warn(">>>>>\npostContruct\n>>>>>");
  }
  public void init() {
    log.warn(">>>>>\ninit\n>>>>>");
  }
  public void customerDestroy() {
    log.warn(">>>>>\ncustomerDestroy\n>>>>>");
  }
  @PreDestroy
  public void preDestroy(){
    log.warn(">>>>>\npreDestroy\n>>>>>");
  }

  @Override
  public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
    log.warn(">>>>>\nsetBeanFactory\n>>>>>");
  }

  @Override
  public void setBeanName(String s) {
    log.warn(">>>>>\nsetBeanName\n>>>>>");
  }

  @Override
  public void destroy() throws Exception {
    log.warn(">>>>>\nDisposableBean.destroy\n>>>>>");
  }

  @Override
  public void afterPropertiesSet() throws Exception {
    log.warn(">>>>>\nafterPropertiesSet\n>>>>>");
  }
}
