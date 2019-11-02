package in.gaks.oneyard.test;

import in.gaks.oneyard.base.BaseController;
import io.leangen.graphql.annotations.GraphQLContext;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLNonNull;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.annotations.GraphQLSubscription;
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi;
import io.leangen.graphql.spqr.spring.util.ConcurrentMultiMap;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

/**
 * .测试
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2019/11/1 下午10:02
 */
@Slf4j
@Controller
@GraphQLApi
@SuppressWarnings("unused")
public class TestController implements BaseController {

  private final ConcurrentMultiMap<String, FluxSink<Test>> subscribers = new ConcurrentMultiMap<>();

  /**
   * .测试查询
   *
   * @param test 条件查询
   * @return 写死的数据
   */
  @GraphQLQuery
  public Collection<Test> tests(Test test) {
    log.info("{} log", test);
    return Stream.of(new Test("1", "p"), new Test("2", "name"))
        .collect(Collectors.toList());
  }

  /**
   * .测试创建
   *
   * @param test 要创建的数据
   * @return 创建的数据
   */
  @GraphQLMutation
  public Test createTest(@GraphQLNonNull Test test) {
    log.info("添加 {}", test);
    return test;
  }

  /**
   * .测试关联查询
   *
   * @param test 测试数据
   * @return 结果
   */
  @GraphQLQuery
  public My my(@GraphQLContext Test test) {
    log.info("进入子查询");
    return new My("123");
  }

  /**
   * .测试发布订阅
   *
   * @param code 监听的 code
   * @return 发布者
   */
  @GraphQLSubscription
  public Publisher<Test> collectionPublisher(String code) {
    return Flux.create(subscriber -> subscribers
            .add(code, subscriber.onDispose(() -> subscribers.remove(code, subscriber))),
        FluxSink.OverflowStrategy.LATEST);
  }
}
