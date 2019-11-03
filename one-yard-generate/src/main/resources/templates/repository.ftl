package com.bugrui.module.repository;

import com.bugrui.module.BaseRepository;
import com.bugrui.module.entity.${EntityName};
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * @author BugRui
 * @date ${.now?datetime}
 **/
@RepositoryRestResource(path = "${EntityNameLow}")
public interface ${EntityName}Repository extends BaseRepository<${EntityName},Long> {
}
