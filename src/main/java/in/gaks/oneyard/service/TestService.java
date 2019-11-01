package in.gaks.oneyard.service;

import in.gaks.oneyard.entity.My;
import in.gaks.oneyard.entity.Test;
import io.leangen.graphql.annotations.GraphQLContext;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLNonNull;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * .
 *
 * @author echo
 * @date 2019/11/1 上午10:58
 */
@Slf4j
@Service
@GraphQLApi
public class TestService {

  @GraphQLQuery
  public Collection<Test> tests(Test test) {
    log.info("{} log", test);
    return Stream.of(new Test("1", "p"), new Test("2", "name"))
        .collect(Collectors.toList());
  }


  @GraphQLMutation
  public Test createTest(@GraphQLNonNull Test test) {
    log.info("添加 {}", test);
    return test;
  }

  @GraphQLQuery
  public My my(@GraphQLContext Test test) {
    log.info("进入子查询");
    return new My("123");
  }
}
