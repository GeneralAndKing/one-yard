package in.gaks.oneyard.handle;

import com.google.gson.Gson;
import in.gaks.oneyard.model.entity.SysLog;
import in.gaks.oneyard.service.SysLogService;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * .
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2019/11/28 下午6:51
 */
@RestControllerAdvice
@RequiredArgsConstructor
public class LogAdviceHandle implements ResponseBodyAdvice {

  private final SysLogService sysLogService;

  @Override
  public boolean supports(MethodParameter returnType, Class converterType) {
    return true;
  }

  @Override
  public Object beforeBodyWrite(Object body, MethodParameter returnType,
      MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request,
      ServerHttpResponse response) {
    SysLog sysLog = new SysLog();
    Gson gson = new Gson();
    HttpServletRequest httpServletRequest = ((ServletServerHttpRequest) request)
        .getServletRequest();
    int status = ((ServletServerHttpResponse) response).getServletResponse().getStatus();
    sysLog
        .setArgs(StringUtils.left(gson.toJson(httpServletRequest.getParameterMap()), 1000))
        .setBrowser(httpServletRequest.getHeader("User-Agent"))
        .setSource(httpServletRequest.getRemoteAddr())
        .setUrl(httpServletRequest.getRequestURI())
        .setMethod(httpServletRequest.getMethod())
        .setResult(StringUtils.left(body.toString(), 1000))
        .setName(String.valueOf(status));
    sysLogService.asyncSave(sysLog);
    return body;
  }
}
