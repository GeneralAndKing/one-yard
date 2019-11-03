package in.gaks.oneyard.model.helper;

/**
 * 函数式接口，用来自定义校验规则測試.
 *
 * @param <T> 第一个
 * @param <V> 第二个
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2019/11/2 下午11:24
 */
public interface VerifyParameterPredicate<T, V> {

  /**
   * 测试.
   *
   * @param t 第一个
   * @param v 第二个
   * @return 结果
   */
  boolean test(T t, V v);
}
