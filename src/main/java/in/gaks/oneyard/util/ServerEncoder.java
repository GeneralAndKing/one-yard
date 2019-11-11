package in.gaks.oneyard.util;

import com.google.gson.Gson;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;
import org.springframework.stereotype.Component;

/**
 * .
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2019/11/11 下午9:09
 */
@Component
public class ServerEncoder implements Encoder.Text<Object> {

  @Override
  public String encode(Object object) throws EncodeException {
    return new Gson().toJson(object);
  }

  @Override
  public void init(EndpointConfig endpointConfig) {

  }

  @Override
  public void destroy() {

  }
}
