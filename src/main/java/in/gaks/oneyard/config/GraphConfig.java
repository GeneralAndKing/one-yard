package in.gaks.oneyard.config;

import graphql.schema.GraphQLSchema;
import in.gaks.oneyard.controller.TestController;
import io.leangen.graphql.GraphQLSchemaGenerator;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * .${描述}
 *
 * @author echo
 * @date 2019/11/1 上午10:54
 */
@Configuration
@AllArgsConstructor
public class GraphConfig {

  private final @NotNull TestController testController;

  @Bean
  public GraphQLSchema graphqlSchema() {
    return new GraphQLSchemaGenerator()
        .withOperationsFromSingleton(testController).generate();
  }

}
