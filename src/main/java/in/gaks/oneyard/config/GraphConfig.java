package in.gaks.oneyard.config;

import graphql.schema.GraphQLSchema;
import in.gaks.oneyard.base.BaseController;
import io.leangen.graphql.GraphQLSchemaGenerator;
import io.leangen.graphql.metadata.strategy.query.DefaultOperationBuilder;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * GraphQL 配置.
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2019/11/1 上午10:54
 */
@Configuration
@RequiredArgsConstructor
public class GraphConfig {

  private final @NotNull List<BaseController> controllerList;

  /**
   * graphqlSchema 配置.
   *
   * @return GraphQLSchema
   */
  @Bean
  public GraphQLSchema graphqlSchema() {
    GraphQLSchemaGenerator generator = new GraphQLSchemaGenerator();
    controllerList.forEach(generator::withOperationsFromSingleton);
    generator.withOperationBuilder(
        new DefaultOperationBuilder(DefaultOperationBuilder.TypeInference.LIMITED));
    return generator.generate();
  }

}
