package in.gaks.oneyard.controller;

import com.alibaba.fastjson.JSONObject;
import in.gaks.oneyard.base.BaseController;
import in.gaks.oneyard.model.constant.OneYard;
import in.gaks.oneyard.model.entity.Approval;
import in.gaks.oneyard.model.entity.PlanMaterial;
import in.gaks.oneyard.model.entity.ProcurementOrder;
import in.gaks.oneyard.model.entity.ProcurementPlan;
import in.gaks.oneyard.model.helper.VerifyParameter;
import in.gaks.oneyard.service.ProcurementOrderService;
import in.gaks.oneyard.service.ProcurementPlanService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 采购计划接口.
 *
 * @author Japoul
 * @date 2019/11/13 下午23:31
 */
@Slf4j
@RequiredArgsConstructor
@RepositoryRestController
@RequestMapping(OneYard.PROCUREMENT_ORDER)
public class ProcurementOrderController extends BaseController<ProcurementOrder,
    ProcurementOrderService, Long> {

}
