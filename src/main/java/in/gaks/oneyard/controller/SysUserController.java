package in.gaks.oneyard.controller;

import in.gaks.oneyard.base.BaseController;
import in.gaks.oneyard.model.entity.SysUser;
import in.gaks.oneyard.model.helper.OneYard;
import in.gaks.oneyard.service.SysUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 用户接口.
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2019/11/2 上午9:31
 */
@Slf4j
@RequiredArgsConstructor
@RepositoryRestController
@RequestMapping(OneYard.SYS_USER)
public class SysUserController extends BaseController<SysUser, SysUserService, Long> {

}
