package ${PackageName}

import in.gaks.oneyard.base.BaseEntity;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
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
