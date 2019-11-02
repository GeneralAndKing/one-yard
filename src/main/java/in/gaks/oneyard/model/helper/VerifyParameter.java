package in.gaks.oneyard.model.helper;

import com.alibaba.fastjson.JSONObject;

/**
 * 参数验证注解，对于 controller 的参数校验.
 * <p>当参数在请求体内，只有一个参数且为 {@link JSONObject} 类型时可用</p>
 * <p>验证表达式如下：</p>
 * <blockquote>
 *   字段名称 | 条件表达式 |# 提示信息
 * </blockquote>
 * <ul>
 *   <li>条件表达式中，对于范围使用 - 进行分割，例如 2-5</li>
 *   <li>对于值直接写即可，例如 {@code equal} 例如 email|xxx@163.com|#邮箱不能为空</li>
 *   <li>对于 {@code required} 和 {@code number} 不需要填写条件表达式</li>
 * </ul>
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2019/11/2 下午11:23
 */
public @interface VerifyParameter {

  /**
   * 验证的参数.
   *
   * <p>表达式如下</p>
   * <blockquote>
   *   age|#age为必填项 ： age 只能为数字 提示信息 age为必填项
   * </blockquote>
   *
   * @return 需要验证的字段
   */
  String[] required() default "";

  /**
   * 验证只能为数字.
   *
   * <p>表达式如下</p>
   * <blockquote>
   *   age|#age只能为数字 ： age 只能为数字 提示信息 age只能为数字
   * </blockquote>
   *
   * @return 需要验证的字段
   */
  String[] number() default "";

  /**
   * 需要验证大小的字段.
   *
   * <p>表达式如下 </p>
   * <blockquote>
   *   username|1-5|#username长度只能为5   ： username 的长度范围为 1 - 5 提示信息 username长度只能为 5
   * </blockquote>
   *
   * @return 需要验证的字段
   */
  String[] size() default "";

  /**
   * 需要验证最大值的字段.
   *
   * <p>表达式如下</p>
   * <blockquote>
   *  age|5|#age最大值为5  ：  age 字段最大为 5 提示信息为 age 最大值为 5
   * </blockquote>
   *
   * @return 需要验证的字段
   */
  String[] max() default "";

  /**
   * 需要验证最小值的字段.
   *
   * <p>表达式如下</p>
   * <blockquote>
   *  age|5|#age最小值为5： age 字段最小为 5 提示信息为 age 最小值为 5
   * </blockquote>
   *
   * @return 需要验证的字段
   */
  String[] min() default "";

  /**
   * 需要验证范围的字段.
   *
   * <p>必须为数字，格式如下</p>
   * <blockquote>
   *  age|1-5|#age不在指定范围内  ：  age 字段范围为 1-5 提示信息为 age 不在指定范围内
   * </blockquote>
   *
   * @return 需要验证的字段
   */
  String[] range() default "";

  /**
   * 需要相等的字段.
   *
   * <p> 表达式如下</p>
   * <blockquote>
   *   age|1|#age不能为空  ：  age 字段必须为 1 提示信息为 age 不能为空
   * </blockquote>
   * <blockquote>
   *   email|xxx@163.com|#邮箱不能为空 : email 字段必须为 xxx@163.com 提示信息为 邮箱 不能为空
   * </blockquote>
   *
   * @return 需要验证的字段
   */
  String[] is() default "";
}
