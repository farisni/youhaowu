package com.youhaowu.common.config.plugin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertiesPropertySource;

import java.util.Properties;

/**
 * 插件环境后处理器：无 DB 配置时自动排除 DataSource 自动装配，避免启动报错。
 */
public class PluginEnvironmentPostProcessor implements EnvironmentPostProcessor {

    private static final String DATASOURCE_URL_KEY = "spring.datasource.url";
    private static final String EXCLUDE_KEY = "spring.autoconfigure.exclude";
    private static final String DS_AUTO_CONFIG = "org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration";

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        String datasourceUrl = environment.getProperty(DATASOURCE_URL_KEY);
        if (datasourceUrl == null || datasourceUrl.isBlank()) {
            String existingExcludes = environment.getProperty(EXCLUDE_KEY, "");
            String newExcludes = existingExcludes.isBlank()
                    ? DS_AUTO_CONFIG
                    : existingExcludes + "," + DS_AUTO_CONFIG;

            Properties props = new Properties();
            props.setProperty(EXCLUDE_KEY, newExcludes);
            environment.getPropertySources().addFirst(
                    new PropertiesPropertySource("pluginExcludes", props));
        }
    }
}
