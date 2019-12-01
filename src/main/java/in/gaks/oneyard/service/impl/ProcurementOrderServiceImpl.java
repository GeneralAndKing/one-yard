package in.gaks.oneyard.service.impl;

import in.gaks.oneyard.base.impl.BaseServiceImpl;
import in.gaks.oneyard.model.entity.Approval;
import in.gaks.oneyard.model.entity.OrderTerms;
import in.gaks.oneyard.model.entity.ProcurementMaterial;
import in.gaks.oneyard.model.entity.ProcurementOrder;
import in.gaks.oneyard.repository.ProcurementOrderRepository;
import in.gaks.oneyard.service.ProcurementOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public void approvalProcurementOrder(ProcurementOrder procurementOrder, Approval approval) {

    }

    @Override
    public void withdrawApproval(ProcurementOrder procurementOrder, String role) {

    }

    @Override
    public void saveProcurementOrder(ProcurementOrder procurementOrder, List<ProcurementMaterial> materials, List<OrderTerms> orderTerms) {

    }
}
