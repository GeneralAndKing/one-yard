package in.gaks.oneyard.model.entity;

import in.gaks.oneyard.base.BaseEntity;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Where;


/**
 * material_plan_summary.
 * @author BugRui EchoCow Japoul
 * @date 2019年11月4日 下午10:17:20
 *
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@Table(name = "material_plan_summary")
@Entity(name = "material_plan_summary")
@Where(clause = "is_enable = 1")
@EqualsAndHashCode(callSuper = true)
public class MaterialPlanSummary extends BaseEntity {

  /**
   * 汇总表物资清单.
   */
  @Transient
  private List<PlanMaterial> planMaterials;

}
