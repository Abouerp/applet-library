package com.abouerp.library.applet.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


/**
 * @author Abouerp
 */
@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@EnableJpaRepositories(basePackages = "com.abouerp.library.applet.repository")
public class DatabaseConfiguration {

    /**
     * jpa 自动设置字段
     */
    @Bean
    public AuditorAware<String> auditorAware() {
        return new UserAuditorConfig();
    }
}
