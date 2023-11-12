package com.example.expensetracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.solr.repository.config.EnableSolrRepositories;

@SpringBootApplication
@EntityScan(basePackages = "com.example.expensetracker.entity")
@ComponentScan(basePackages = "com.example.expensetracker")
@EnableCaching
@EnableSolrRepositories
@EnableConfigurationProperties
public class ExpenseTrackerApplication {

  public static void main(String[] args) {
    SpringApplication.run(ExpenseTrackerApplication.class, args);
  }

}
