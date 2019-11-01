package in.gaks.oneyard.entity;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * .
 *
 * @author echo
 * @date 2019/11/1 上午11:00
 */
@Data
@RequiredArgsConstructor
public class Test {
  @NonNull
  private String name;
  @NonNull
  private String password;
  private My my;
}
