package in.gaks.oneyard.service.impl;

import in.gaks.oneyard.base.impl.BaseServiceImpl;
import in.gaks.oneyard.model.entity.ProcurementOrder;
import in.gaks.oneyard.repository.ProcurementOrderRepository;
import in.gaks.oneyard.service.ProcurementOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 汇总表事务处理.
 *
 * @author Japoul
 * @date 2019/11/2 下午11:01
 */
@Service
@RequiredArgsConstructor
public class ProcurementOrderServiceImpl extends BaseServiceImpl<ProcurementOrderRepository,
    ProcurementOrder, Long>
    implements ProcurementOrderService {

}
