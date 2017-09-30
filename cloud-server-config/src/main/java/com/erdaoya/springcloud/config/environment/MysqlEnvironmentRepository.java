package com.erdaoya.springcloud.config.environment;

import com.erdaoya.springcloud.config.entity.Config;
import com.erdaoya.springcloud.config.repository.ConfigJpaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.config.environment.Environment;
import org.springframework.cloud.config.environment.PropertySource;
import org.springframework.cloud.config.server.environment.EnvironmentRepository;
import org.springframework.cloud.config.server.environment.SearchPathLocator;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 17/9/30 下午7:38.
 *
 * @author thoreau
 */
@SuppressWarnings({"SpringAutowiredFieldsWarningInspection", "SpringJavaAutowiredMembersInspection"})
@Slf4j
public class MysqlEnvironmentRepository implements EnvironmentRepository,SearchPathLocator {
    private static final String DEFAULT_LABEL = "master";

    @Autowired
    private DataSource dataSource;

    @Autowired
    private ConfigJpaRepository configJpaRepository;

    private String getTableName(String application, String profile, String label) {
        String tableName = application + "-" + profile;
        tableName = tableName.toLowerCase();
        return tableName;
    }
    private String getLocation() {
        try {
            return "mysql-table:"+ this.dataSource.getConnection().getMetaData().getURL();
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public SearchPathLocator.Locations getLocations(String application, String profile, String label) {
        if (label == null) label = DEFAULT_LABEL;
        String[] locations = new String[1];
        locations[0] = this.getLocation();
        return new SearchPathLocator.Locations(application, profile, label, null, locations);
    }

    @Override
    public Environment findOne(String application, String profile, String label) {
        if (label == null) label = DEFAULT_LABEL;
        String tableName = this.getTableName(application, profile, label);
        Map<String, String> values = this.getProperties(tableName);
        PropertySource propertySource = new PropertySource(tableName, values);
        String[] profiles = new String[1];
        profiles[0] = profile;
        Environment env = new Environment(this.getLocation(), profiles, label, null, null);
        env.add(propertySource);
        return env;
    }

    private Map<String, String> getProperties(String tableName) {
        Map<String, String> properties = new HashMap<>();
        try {
            List<Config> springCloudConfigs = this.configJpaRepository.findByTableName(tableName);
            for (Config config : springCloudConfigs) {
                properties.put(config.getProperty(), config.getValue());
            }
        }
        catch(Exception e) {
            log.error("Error getting properties", e);
        }
        return properties;
    }
}
