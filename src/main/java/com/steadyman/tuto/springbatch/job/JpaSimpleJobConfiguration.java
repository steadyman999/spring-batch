package com.steadyman.tuto.springbatch.job;

import com.steadyman.tuto.springbatch.entity.Employee;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;

@Slf4j
@Configuration
@EnableBatchProcessing
public class JpaSimpleJobConfiguration {
    private final EntityManagerFactory entityManagerFactory;
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    public JpaSimpleJobConfiguration(EntityManagerFactory entityManagerFactory,
                                     JobBuilderFactory jobBuilderFactory,
                                     StepBuilderFactory stepBuilderFactory) {
        this.entityManagerFactory = entityManagerFactory;
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
    }

    @Bean
    public Job jpaSimpleJob() {
        return jobBuilderFactory.get("jpaSimpleJob")
                .start(jpaSimpleStep())
                .build();
    }

    private Step jpaSimpleStep() {
        return stepBuilderFactory.get("jpaSimpleStep")
                .<Employee, Employee>chunk(1)
                .reader(employeeReader())
                .writer(employeeWriter())
                .build();
    }

    private JpaPagingItemReader<Employee> employeeReader() {
        return new JpaPagingItemReaderBuilder<Employee>()
                .name("employeeReader")
                .entityManagerFactory(entityManagerFactory)
                .pageSize(1)
                .queryString("select e from Employee e")
                .build();
    }

    private ItemWriter<Employee> employeeWriter() {
        return employees -> {
            for (Employee employee : employees) {
                log.info("seq = {}, name = {}, salary = {}", employee.getSeq(), employee.getName(), employee.getSalary());
            }
        };
    }

}
