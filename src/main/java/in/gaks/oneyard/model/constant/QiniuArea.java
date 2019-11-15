package in.gaks.oneyard.model.constant;

/**
 * .
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2019/11/15 上午10:12
 */
public enum QiniuArea {

  /**
   * 华东.
   */
  EAST("华东"),
  /**
   * 华南.
   */
  SOUTH("华南"),
  /**
   * 华北.
   */
  NORTH("华北"),
  /**
   * 北美.
   */
  AMERICA("北美"),
  /**
   * 东南亚.
   */
  Asia("东南亚");
  /**
   * 地区.
   */
  private final String area;

  /**
   * 构造.
   *
   * @param area 地区
   */
  QiniuArea(String area) {
    this.area = area;
  }

  /**
   * 是否匹配.
   *
   * @param area 地区
   * @return 结果
   */
  public boolean match(String area) {
    return this.area.equals(area);
  }

}
