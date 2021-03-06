package in.gaks.oneyard.model.exception;

/**
 * 资源未找到异常.
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2019/11/2 下午12:56
 */
public class ResourceNotFoundException extends ResourceException {

  public ResourceNotFoundException() {
    super("Resource Not Found Exception");
  }

  public ResourceNotFoundException(String message) {
    super(message);
  }

  public ResourceNotFoundException(String message, Object... args) {
    super(String.format(message, args));
  }
}
