package in.gaks.oneyard.model.entity;

import in.gaks.oneyard.base.BaseEntity;
import lombok.experimental.Accessors;
import lombok.*;
import javax.persistence.*;
import org.hibernate.annotations.Where;


/**
 * supplier.
 * @author BugRui EchoCow Japoul
 * @date 2019年11月4日 下午10:17:20
 *
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@Table(name = "supplier")
@Entity(name = "supplier")
@Where(clause = "is_enable = 1")
@EqualsAndHashCode(callSuper = true)
public class Supplier extends BaseEntity {
}