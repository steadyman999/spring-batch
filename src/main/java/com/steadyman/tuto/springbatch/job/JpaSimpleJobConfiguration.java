package com.steadyman.tuto.springbatch.job;

import com.steadyman.tuto.springbatch.entity.Employee;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;

@Slf4j
@Configuration
@EnableBatchProcessing
public class JpaSimpleJobConfiguration {
    private final int CHUNK_SIZE = 1;

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
                .<Employee, Employee>chunk(CHUNK_SIZE)
                .reader(employeeReader())
                .processor(employeeProcessor())
                .writer(employeeJpaWriter())
//                .writer(itemWriter())
                .build();
    }

    private JpaPagingItemReader<? extends Employee> employeeReader() {
        return new JpaPagingItemReaderBuilder<Employee>()
                .name("employeeReader")
                .entityManagerFactory(entityManagerFactory)
                .pageSize(CHUNK_SIZE)
                .queryString("select e from Employee e")
                .build();
    }

    private ItemProcessor<Employee, Employee> employeeProcessor() {
        return employee -> {
            employee.setDepartmentId(4L);
            return employee;
        };
    }

    private ItemWriter<Employee> itemWriter() {
        return employees -> {
            employees.stream().forEach(employee -> log.info("departmentId = {}, name = {}", employee.getDepartmentId(), employee.getName()));
        };
    }

    private JpaItemWriter<Employee> employeeJpaWriter() {
        return new JpaItemWriterBuilder<Employee>()
                .entityManagerFactory(entityManagerFactory)
                .build();
    }

}
