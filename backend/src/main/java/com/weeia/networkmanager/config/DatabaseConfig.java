package com.weeia.networkmanager.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = {"com.weeia.networkmanager.dao"})
public class DatabaseConfig {
}
