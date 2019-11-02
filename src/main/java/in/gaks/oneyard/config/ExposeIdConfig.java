package in.gaks.oneyard.config;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.EntityManagerFactory;
import javax.persistence.metamodel.ManagedType;
import javax.persistence.metamodel.Metamodel;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;

/**
 * rest export id.
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2019/11/2 下午11:12
 */
@Configuration
public class ExposeIdConfig implements RepositoryRestConfigurer {

  private final EntityManagerFactory entityManagerFactory;

  public ExposeIdConfig(EntityManagerFactory entityManagerFactory) {
    this.entityManagerFactory = entityManagerFactory;
  }

  /**
   * 获取所有实体类型.
   */
  private List<Class<?>> getAllManagedEntityTypes() {
    List<Class<?>> entityClasses = new ArrayList<>();
    Metamodel metamodel = entityManagerFactory.getMetamodel();
    for (ManagedType<?> managedType : metamodel.getManagedTypes()) {
      Class<?> javaType = managedType.getJavaType();
      if (javaType.isAnnotationPresent(Entity.class)) {
        entityClasses.add(managedType.getJavaType());
      }
    }
    return entityClasses;
  }

  /**
   * 暴露所有实体id.
   */
  @Override
  public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
    List<Class<?>> entityClasses = getAllManagedEntityTypes();
    for (Class<?> entityClass : entityClasses) {
      config.exposeIdsFor(entityClass);
    }
  }
}
