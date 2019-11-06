package in.gaks.oneyard.model.exception;

/**
 * 资源已存在异常.
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2019/11/2 下午12:56
 */
public class ResourceExistException extends ResourceException {

  public ResourceExistException() {
    super("Resource Already Exist Exception");
  }

  public ResourceExistException(String message) {
    super(message);
  }

  public ResourceExistException(String message, Object... args) {
    super(String.format(message, args));
  }
}
