package in.gaks.oneyard.model.exception;

/**
 * .
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2019/11/2 下午11:39
 */
public class ValidationException extends RuntimeException {

  public ValidationException(String message) {
    super(message);
  }
}
