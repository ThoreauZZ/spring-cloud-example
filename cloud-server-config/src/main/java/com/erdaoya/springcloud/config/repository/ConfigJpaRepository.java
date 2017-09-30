package com.erdaoya.springcloud.config.repository;

import com.erdaoya.springcloud.config.entity.Config;

import java.sql.SQLException;
import java.util.List;

/**
 * 17/9/30 下午7:45.
 *
 * @author thoreau
 */
public interface ConfigJpaRepository {
    List<Config> findByTableName(String tableName) throws SQLException;
}
