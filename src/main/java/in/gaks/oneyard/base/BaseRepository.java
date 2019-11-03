package in.gaks.oneyard.base;

import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.transaction.annotation.Transactional;

/**
 * Base Repository.
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2019/11/2 上午9:27
 */
@SuppressWarnings("all")
public interface BaseRepository<T extends BaseEntity, ID> extends JpaRepository<T, ID>,
    JpaSpecificationExecutor<T> {

  /**
   * 修改默认的删除方法.
   *
   * @param id id
   */
  @Override
  default void deleteById(@NotNull ID id) {
    deleteExistById(id);
  }

  /**
   * 删除一个数据.
   *
   * @param id id
   */
  @Modifying
  @RestResource(exported = false)
  @Transactional(rollbackForClassName = "java.lang.Exception")
  @Query(value = "update #{#entityName} set is_enable = false where id = :id", nativeQuery = true)
  void deleteExistById(@NotNull @Param("id") ID id);

  /**
   * 删除多个数据.
   *
   * @param ids ids
   */
  @Modifying
  @RestResource(path = "deleteByIds")
  @Transactional(rollbackForClassName = "java.lang.Exception")
  @Query(value = "update #{#entityName} set is_enable = false where id in (:ids)", nativeQuery = true)
  int deleteExistByIds(@NotNull @Param("ids") List<ID> ids);

  /**
   * 真正删除一个数据.
   *
   * @param id id
   */
  @Modifying
  @RestResource(exported = false)
  @Transactional(rollbackForClassName = "java.lang.Exception")
  @Query(value = "delete from #{#entityName} where id = :id", nativeQuery = true)
  void reallyDeleteExistById(@NotNull @Param("id") ID id);

  /**
   * 通过 id 列表查询.
   *
   * @param ids id 列表
   * @return 结果
   */
  @RestResource(path = "byIds", rel = "byIds")
  @Query(value = "select * from #{#entityName}  where id in (:ids) and is_enable = 1 ", nativeQuery = true)
  List<T> searchAllByIds(@NotNull @Param("ids") List<ID> ids);

  /**
   * 通过 id 列表查询
   *
   * @param ids id 列表
   * @param pageable 分页对象
   * @return 结果
   */
  @RestResource(path = "byIdsPage", rel = "byIdsPage")
  @Query(value = "select * from #{#entityName}  where id in (:ids) and is_enable = 1 ",
      countQuery = "select count(*) from #{#entityName}", nativeQuery = true)
  Page<T> searchAllByIds(@NotNull @Param("ids") List<ID> ids, Pageable pageable);

}
