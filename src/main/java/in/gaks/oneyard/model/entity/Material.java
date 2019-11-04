package in.gaks.oneyard.model.entity;

import in.gaks.oneyard.base.BaseEntity;
import lombok.experimental.Accessors;
import lombok.*;
import javax.persistence.*;
import org.hibernate.annotations.Where;


/**
 * material
 *
 * @author BugRui EchoCow Japoul
 * @date 2019年11月4日 下午5:22:07
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@Table(name = "material")
@Entity(name = "material")
@Where(clause = "is_enable = 1")
@EqualsAndHashCode(callSuper = true)
public class Material extends BaseEntity {

  /**
   * 物料编号
   */
  private java.lang.String code;

  /**
   * 物料名称
   */
  private java.lang.String name;

  /**
   * 物料类别id
   */
  private java.lang.Integer typeId;
}
