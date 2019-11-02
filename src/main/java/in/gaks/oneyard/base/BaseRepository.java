package in.gaks.oneyard.base;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Base Repository.
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2019/11/2 上午9:27
 */
@SuppressWarnings("all")
public interface BaseRepository<T extends BaseEntity, ID> extends JpaRepository<T, ID>,
    JpaSpecificationExecutor<T> {

}
