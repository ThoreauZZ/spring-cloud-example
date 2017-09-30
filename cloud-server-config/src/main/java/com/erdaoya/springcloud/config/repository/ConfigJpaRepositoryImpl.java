package com.erdaoya.springcloud.config.repository;

import com.erdaoya.springcloud.config.entity.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

/**
 * 17/9/30 下午8:28.
 *
 * @author thoreau
 */
@SuppressWarnings({"unchecked", "SpringAutowiredFieldsWarningInspection"})
@Repository
public class ConfigJpaRepositoryImpl implements ConfigJpaRepository {
    @Autowired
    @Qualifier("dataSource")
    private DataSource dataSource;

    @Autowired
    private EntityManager entityManager;

    @Override
    public List<Config> findByTableName(String tableName) throws SQLException {
        String sql = "SELECT `id`,`property`,`value` FROM `%s`";
        String queryFormat = String.format(sql, tableName);
        return entityManager.createNativeQuery(queryFormat, Config.class).getResultList();
    }
}
