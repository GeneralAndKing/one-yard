package ${PackageName};

import com.bugrui.module.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;

/**
* ${TableName}
*
* @author BugRui
* @date ${.now?datetime}
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "${TableName}")
@Entity(name = "${TableName}")
public class ${ClassName} extends BaseEntity implements Serializable {
<#list Columns as column>

    /**
    * ${column.comment}
    */
    private ${column.type} ${column.name};
</#list>
}
