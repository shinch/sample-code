package com.gmail.shinch.report;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
@EnableCaching
@EntityScan(
        basePackageClasses = {Jsr310JpaConverters.class},
        basePackages = {"com.gmail.shinch.stater.dao.database"})
public class BootStaterApplication {
    public static void main(String[] args) {
        SpringApplication.run(BootStaterApplication.class, args);
    }
}
