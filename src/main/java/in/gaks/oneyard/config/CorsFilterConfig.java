package in.gaks.oneyard.config;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

/**
 * CorsFilter.
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2019/11/6 下午8:45
 */
@Configuration
public class CorsFilterConfig implements Filter {

  @Override
  public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
      throws IOException, ServletException {

    HttpServletResponse response = (HttpServletResponse) res;

    // Origin ->> http://localhost:8080，允许此URL进行资源访问
    // https://developer.mozilla.org/zh-CN/docs/Web/HTTP/Headers/Access-Control-Allow-Origin?utm_source=mozilla&utm_medium=devtools-netmonitor&utm_campaign=default
    response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
    response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");
    response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS,
        "POST, GET, PUT, PATCH, OPTIONS, DELETE");
    // application/x-www-form-urlencoded, multipart/form-data 或 text/plain
    response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS,
        "Origin, X-Requested-With, Content-Type, Accept, authorization");
    response.setHeader(HttpHeaders.ACCESS_CONTROL_MAX_AGE, "3600");
    chain.doFilter(req, res);
  }


}
