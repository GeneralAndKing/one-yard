package in.gaks.oneyard.config;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Configuration;

/**
 * CorsFilter.
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2019/11/6 下午8:45
 */
@Configuration
public class CorsFilter implements Filter {

  @Override
  public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
      throws IOException, ServletException {

    HttpServletResponse response = (HttpServletResponse) res;

    HttpServletRequest reqs = (HttpServletRequest) req;

    // Origin ->> http://localhost:8080，允许此URL进行资源访问
    // https://developer.mozilla.org/zh-CN/docs/Web/HTTP/Headers/Access-Control-Allow-Origin?utm_source=mozilla&utm_medium=devtools-netmonitor&utm_campaign=default
    response.setHeader("Access-Control-Allow-Origin", reqs.getHeader("Origin"));
    response.setHeader("Access-Control-Allow-Credentials", "true");
    response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, PATCH, OPTIONS, DELETE");
    response.setHeader("Access-Control-Max-Age", "3600");
    // application/x-www-form-urlencoded, multipart/form-data 或 text/plain
    response.setHeader("Access-Control-Allow-Headers", "x-requested-with");
    chain.doFilter(req, res);
  }


}
