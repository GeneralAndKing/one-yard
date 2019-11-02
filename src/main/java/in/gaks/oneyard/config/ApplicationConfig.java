package in.gaks.oneyard.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.web.HateoasPageableHandlerMethodArgumentResolver;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 应用配置.
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2019/11/2 下午11:10
 */
@Configuration
@RequiredArgsConstructor
public class ApplicationConfig implements WebMvcConfigurer {

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

}
