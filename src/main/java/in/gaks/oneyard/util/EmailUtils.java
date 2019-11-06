package in.gaks.oneyard.util;

import in.gaks.oneyard.model.properties.EmailProperties;
import java.util.Map;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

/**
 * 邮件工具类.
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2019/11/6 下午3:31
 */
@Async
@Slf4j
@Component
@RequiredArgsConstructor
public class EmailUtils {

  private final JavaMailSender javaMailSender;
  private final EmailProperties emailProperties;
  private final TemplateEngine templateEngine;

  /**
   * 简单文字邮件发送.
   *
   * @param toEmail 接收者邮箱
   * @param subject 邮件主题
   * @param content 邮件内容
   */
  public void sendSimpleMail(String toEmail, String subject, String content) {
    SimpleMailMessage message = new SimpleMailMessage();
    message.setFrom(emailProperties.getUsername());
    message.setTo(toEmail);
    message.setSubject(subject);
    message.setText(content);
    javaMailSender.send(message);
  }

  /**
   * 发送带模板的邮件.
   *
   * @param toEmail   接收者邮箱
   * @param type      邮件类型
   * @param subject   邮件主题
   * @param template  邮件模板名称(默认资源路径下)
   * @param variables 模板内变量集合
   */
  public void sendTemplateMail(String toEmail, String type, String subject,
      String template, Map<String, Object> variables) {
    MimeMessage message = javaMailSender.createMimeMessage();
    Context context = new Context();
    variables.forEach(context::setVariable);
    context.setVariable("type", type);
    String content = templateEngine.process(template, context);
    try {
      MimeMessageHelper messageHelper = new MimeMessageHelper(message, true);
      messageHelper.setFrom(emailProperties.getUsername());
      messageHelper.setTo(toEmail);
      messageHelper.setSubject(subject);
      messageHelper.setText(content, true);
      javaMailSender.send(message);
      log.debug("向 {} 发送 {} 邮件成功", toEmail, type);
    } catch (MessagingException e) {
      log.error(e.getLocalizedMessage());
      log.warn("向 {} 发送 {} 邮件失败", toEmail, type);
    }
  }
}
