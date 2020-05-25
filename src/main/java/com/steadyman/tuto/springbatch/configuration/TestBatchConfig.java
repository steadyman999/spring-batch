package com.steadyman.tuto.springbatch.configuration;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableAutoConfiguration
@EnableJpaRepositories(basePackages = {"com.steadyman.tuto.springbatch.repository"})
@EntityScan(basePackages = {"com.steadyman.tuto.springbatch.entity"})
public class TestBatchConfig {
}
