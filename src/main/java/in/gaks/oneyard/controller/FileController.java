package in.gaks.oneyard.controller;

import ch.qos.logback.core.util.FileSize;
import com.qiniu.storage.model.DefaultPutRet;
import in.gaks.oneyard.model.entity.SysUser;
import in.gaks.oneyard.model.exception.ResourceUploadException;
import in.gaks.oneyard.model.properties.QiniuProperties;
import in.gaks.oneyard.service.SysUserService;
import in.gaks.oneyard.util.QiniuUtils;
import java.io.IOException;
import java.security.Principal;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * .
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2019/11/15 下午3:57
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/files", produces = MediaType.APPLICATION_JSON_VALUE)
public class FileController {

  private final SysUserService sysUserService;
  private final QiniuUtils qiniuUtils;
  private final QiniuProperties qiniuProperties;
  private static final FileSize MAX_SIZE = FileSize.valueOf("10MB");

  /**
   * 头像上传，用户.
   *
   * @param file     头像
   * @param username 用户名
   * @return 结果
   */
  @PostMapping("avatar")
  public HttpEntity<?> uploadAvatar(@RequestParam("file") MultipartFile file,
      @RequestParam("user") String username) {
    Assert.notNull(file, "文件不能为空");
    Assert.notNull(username, "用户不能为空");
    return upload(file, sysUserService.findByUsername(username));
  }

  /**
   * 头像上传，自己.
   *
   * @param file      文件
   * @param principal 用户信息
   * @return 结果
   */
  @PostMapping("avatar/self")
  public HttpEntity<?> uploadAvatarSelf(@RequestParam("file") MultipartFile file,
      Principal principal) {
    Assert.notNull(file, "文件不能为空");
    return upload(file, sysUserService.findByUsername(principal.getName()));
  }

  private HttpEntity<?> upload(MultipartFile file, SysUser user) {
    if (MAX_SIZE.getSize() < file.getSize()) {
      throw new ResourceUploadException("头像大小不能大于10MB");
    }
    try {
      DefaultPutRet upload = qiniuUtils.upload(file, "/avatar/",
          UUID.randomUUID().toString());
      String url = qiniuProperties.getHost() + upload.key;
      user.setIcon(url);
      return ResponseEntity.ok(sysUserService.save(user));
    } catch (IOException e) {
      log.error("文件上传异常： {}", e.getMessage());
      throw new ResourceUploadException("头像上传失败：", e.getMessage());
    }
  }
}
