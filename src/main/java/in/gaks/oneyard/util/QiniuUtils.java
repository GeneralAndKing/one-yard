package in.gaks.oneyard.util;

import com.google.gson.Gson;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import in.gaks.oneyard.model.constant.QiniuArea;
import in.gaks.oneyard.model.properties.QiniuProperties;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

/**
 * 七牛云工具类.
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2019/11/15 上午10:10
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class QiniuUtils {

  private final QiniuProperties qiniuProperties;

  /**
   * 上传凭证.
   *
   * @return 上传凭证
   */
  private Auth createAuth() {
    return Auth.create(qiniuProperties.getAccessKey(),
        qiniuProperties.getSecretKey());
  }


  /**
   * 创建简单 七牛云 配置信息.
   *
   * @return 配置信息
   */
  private Configuration configurationQiNiu() {
    String area = qiniuProperties.getArea();
    Configuration config;
    if (QiniuArea.EAST.match(area)) {
      config = new Configuration(Region.huadong());
    } else if (QiniuArea.NORTH.match(area)) {
      config = new Configuration(Region.huabei());
    } else if (QiniuArea.SOUTH.match(area)) {
      config = new Configuration(Region.huanan());
    } else if (QiniuArea.AMERICA.match(area)) {
      config = new Configuration(Region.beimei());
    } else if (QiniuArea.Asia.match(area)) {
      config = new Configuration(Region.xinjiapo());
    } else {
      config = new Configuration(Region.autoRegion());
    }
    return config;
  }

  /**
   * 七牛云 文件上传.
   *
   * @param file      要上传的文件
   * @param pathChild 子目录
   * @param fileName  文件名
   * @return DefaultPutRet
   */
  public DefaultPutRet upload(MultipartFile file, String pathChild, String fileName)
      throws IOException {
    UploadManager uploadManager = new UploadManager(configurationQiNiu());
    String token = createAuth().uploadToken(qiniuProperties.getBucket());
    Response response = uploadManager
        .put(file.getBytes(), qiniuProperties.getDirName() + pathChild + fileName, token);
    return new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
  }

}
