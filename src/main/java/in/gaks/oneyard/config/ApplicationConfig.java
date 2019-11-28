package in.gaks.oneyard.config;

import java.util.concurrent.Executor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.config.MethodInvokingFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.web.HateoasPageableHandlerMethodArgumentResolver;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * 应用配置.
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2019/11/2 下午11:10
 */
@EnableAsync
@Configuration
@EnableWebSocket
@RequiredArgsConstructor
@EnableHypermediaSupport(type = {EnableHypermediaSupport.HypermediaType.HAL})
public class ApplicationConfig implements WebMvcConfigurer, AsyncConfigurer {

  private final HateoasPageableHandlerMethodArgumentResolver pageableResolver;

  /**
   * 分页资源汇编器.
   *
   * @return PagedResourcesAssembler
   */
  @Bean
  @Primary
  public PagedResourcesAssembler myPagedResourcesAssembler() {
    PagedResourcesAssembler<Object> pagedResourcesAssembler = new PagedResourcesAssembler<>(
        pageableResolver, null);
    pagedResourcesAssembler.setForceFirstAndLastRels(true);
    return pagedResourcesAssembler;
  }

  @Override
  public Executor getAsyncExecutor() {
    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
    executor.setCorePoolSize(10);
    executor.setMaxPoolSize(50);
    executor.setQueueCapacity(100);
    executor.initialize();
    return executor;
  }

  @Bean
  public ServerEndpointExporter serverEndpointExporter() {
    return new ServerEndpointExporter();
  }

  /**
   * 添加 SecurityContextHolder 上下文传递支持.
   *
   * @return MethodInvokingFactoryBean
   */
  @Bean
  public MethodInvokingFactoryBean methodInvokingFactoryBean() {
    MethodInvokingFactoryBean methodInvokingFactoryBean = new MethodInvokingFactoryBean();
    methodInvokingFactoryBean.setTargetClass(SecurityContextHolder.class);
    methodInvokingFactoryBean.setTargetMethod("setStrategyName");
    methodInvokingFactoryBean.setArguments(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
    return methodInvokingFactoryBean;
  }

}
