package in.gaks.oneyard.model.exception;

/**
 * .
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2019/11/15 下午5:16
 */
public class ResourceUploadException extends ResourceException {
  public ResourceUploadException() {
    super("Resource Upload Failed Exception");
  }

  public ResourceUploadException(String message) {
    super(message);
  }

  public ResourceUploadException(String message, Object... args) {
    super(String.format(message, args));
  }
}
