package in.gaks.oneyard.controller;

import in.gaks.oneyard.model.constant.OneYard;
import in.gaks.oneyard.model.entity.Notification;
import in.gaks.oneyard.util.NotifyUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * .
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2019/11/11 下午5:39
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(OneYard.NOTIFICATION)
public class NotificationController {

  private final NotifyUtil notifyUtil;

  @GetMapping("/test/{user}")
  public HttpEntity<?> broadcast(@PathVariable String user) {
    Notification notification = new Notification();
    notification.setMessage("content");
    notification.setName("title");
    notifyUtil.sendMessage(user, notification);
    return ResponseEntity.ok().build();
  }

}
