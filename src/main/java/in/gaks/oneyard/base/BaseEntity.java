package in.gaks.oneyard.base;

import io.leangen.graphql.annotations.GraphQLQuery;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * 实体抽象.
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2019/11/2 上午8:45
 */
@Data
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@SuppressWarnings("unused")
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {

  /**
   * id 主键.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  /**
   * 名称.
   */
  @Size(max = 55, message = "name 长度不能超过 55")
  private String name;

  /**
   * 排序.
   */
  private Integer sort = 1;

  /**
   * 创建时间.
   */
  @CreatedDate
  @Column(name = "create_time", nullable = false,
      columnDefinition = "datetime not null default now() comment '创建时间'")
  private LocalDateTime createTime;

  /**
   * 创建用户.
   */
  @CreatedBy
  @Column(name = "create_user")
  private String createUser;

  /**
   * 修改时间.
   */
  @LastModifiedDate
  @Column(name = "modify_time", nullable = false,
      columnDefinition = "datetime not null default now() comment '修改时间'")
    private LocalDateTime modifyTime;

  /**
   * 修改用户.
   */
  @LastModifiedBy
  @Column(name = "modify_user")
  private String modifyUser;

  /**
   * 备注.
   */
  @Size(max = 255, message = "remark 长度不能超过 255")
  private String remark;

  /**
   * .是否启用
   */
  private Boolean isEnable = true;


  @GraphQLQuery(name = "id")
  public Long getId() {
    return id;
  }

  @GraphQLQuery(name = "name")
  public String getName() {
    return name;
  }

  @GraphQLQuery(name = "sort")
  public Integer getSort() {
    return sort;
  }

  @GraphQLQuery(name = "createTime")
  public LocalDateTime getCreateTime() {
    return createTime;
  }

  @GraphQLQuery(name = "createUser")
  public String getCreateUser() {
    return createUser;
  }

  @GraphQLQuery(name = "modifyTime")
  public LocalDateTime getModifyTime() {
    return modifyTime;
  }

  @GraphQLQuery(name = "modifyUser")
  public String getModifyUser() {
    return modifyUser;
  }

  @GraphQLQuery(name = "remark")
  public String getRemark() {
    return remark;
  }

  @GraphQLQuery(name = "isEnable")
  public Boolean getEnable() {
    return isEnable;
  }
}
