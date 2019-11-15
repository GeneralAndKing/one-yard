package in.gaks.oneyard.handle;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import in.gaks.oneyard.model.exception.ResourceErrorException;
import in.gaks.oneyard.model.exception.ResourceNotFoundException;
import in.gaks.oneyard.model.exception.ValidationException;
import javax.validation.ConstraintViolationException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 资源异常捕获处理.
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2019/11/6 下午10:40
 */
@RestControllerAdvice
public class ResourceExceptionHandle {

  /**
   * ConstraintViolationException.
   *
   * @param exception exception
   * @return 400
   */
  @ExceptionHandler(ConstraintViolationException.class)
  public HttpEntity<?> handle(ConstraintViolationException exception) {
    JSONArray errors = new JSONArray();
    exception.getConstraintViolations().forEach(item -> errors.add(item.getMessage()));
    return ResponseEntity.badRequest().body(errors);
  }

  /**
   * ResourceNotFoundException.
   *
   * @param exception exception
   * @return 404
   */
  @ExceptionHandler(ResourceNotFoundException.class)
  public HttpEntity<?> handleResourceNotFoundException(ResourceNotFoundException exception) {
    JSONObject error = new JSONObject();
    error.put("error", "资源未找到");
    error.put("error_description", exception.getMessage());
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
  }

  /**
   * ResourceException.
   *
   * @param exception exception
   * @return 400
   */
  @ExceptionHandler({ValidationException.class,
      ResourceErrorException.class, IllegalArgumentException.class})
  public HttpEntity<?> handleResourceException(Exception exception) {
    JSONObject error = new JSONObject();
    error.put("error", "资源出现错误");
    error.put("error_description", exception.getMessage());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
  }

}
