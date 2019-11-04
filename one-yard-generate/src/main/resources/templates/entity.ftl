package ${PackageName}

import in.gaks.oneyard.base.BaseEntity;
import lombok.experimental.Accessors;
import lombok.*;
import javax.persistence.*;
import org.hibernate.annotations.Where;


/**
* ${TableName}.
* @author BugRui EchoCow Japoul
* @date ${.now?datetime}
*
*/
@Data
@NoArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@Table(name = "${TableName}")
@Entity(name = "${TableName}")
@Where(clause = "is_enable = 1")
@EqualsAndHashCode(callSuper = true)
public class ${ClassName} extends BaseEntity {
<#list Columns as column>
  /**
  * ${column.comment}.
  */
  private ${column.type} ${column.name};

</#list>
}
