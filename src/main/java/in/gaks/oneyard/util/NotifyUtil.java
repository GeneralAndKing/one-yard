package in.gaks.oneyard.util;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 通知工具类.
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2019/11/11 下午6:26
 */
@Slf4j
@Component
@NoArgsConstructor
@ServerEndpoint(value = "/notify/{userId}", encoders = ServerEncoder.class)
public class NotifyUtil {

  private Session session;
  private static ConcurrentHashMap<String, NotifyUtil> webSocketMap = new ConcurrentHashMap<>();

  /**
   * 链接的时候.
   *
   * @param userId  用户id
   * @param session session
   */
  @OnOpen
  public void onOpen(@PathParam(value = "userId") String userId, Session session) {
    this.session = session;
    webSocketMap.put(userId, this);
    log.debug("[用户 {} 打开连接，目前连接数：{}]", userId, webSocketMap.size());
  }

  /**
   * 关闭的时候.
   *
   * @param userId 用户id
   */
  @OnClose
  public void onClose(@PathParam(value = "userId") String userId) {
    webSocketMap.remove(userId);
    log.debug("[用户 {} 关闭连接，目前连接数：{}]", userId, webSocketMap.size());
  }

  /**
   * 接收到消息.
   *
   * @param message 消息
   */
  @OnMessage
  public void onMessage(@PathParam(value = "userId") String userId, String message) {
    log.debug("[服务器接受到用户 {} 信息：{}]", userId, message);
  }

  /**
   * 发送消息.
   *
   * @param userId  用户id
   * @param message 信息
   */
  public void sendMessage(String userId, Object message) {
    NotifyUtil webSocket = webSocketMap.get(userId);
    if (Objects.isNull(webSocket)) {
      return;
    }
    try {
      webSocket.session.getBasicRemote().sendObject(message);
    } catch (IOException | EncodeException e) {
      log.error(e.getMessage());
    }
  }

}

