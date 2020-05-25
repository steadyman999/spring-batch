package com.steadyman.tuto.springbatch.job;

import com.steadyman.tuto.springbatch.entity.Person;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;

@Slf4j
@Configuration
@EnableBatchProcessing
public class PersonJobConfiguration {

    private final EntityManagerFactory entityManagerFactory;
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    public PersonJobConfiguration(EntityManagerFactory entityManagerFactory,
                                  JobBuilderFactory jobBuilderFactory,
                                  StepBuilderFactory stepBuilderFactory) {
        this.entityManagerFactory = entityManagerFactory;
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
    }

    @Bean
    public Job personJob() {
        return jobBuilderFactory.get("personJob")
                .start(personStep())
                .build();
    }

    @Bean
    @JobScope
    public Step personStep() {
        return stepBuilderFactory.get("personStep")
                .<Person, Person>chunk(1)
                .reader(customItemPersonReader())
                .processor(customItemPersonProcessor())
                .writer(customItemPersonWriter())
                .build();
    }

    @Bean
    public JpaPagingItemReader<Person> customItemPersonReader() {
        return new JpaPagingItemReaderBuilder<Person>()
                .name("customItemPersonReader")
                .entityManagerFactory(entityManagerFactory)
                .pageSize(1)
                .queryString("SELECT p FROM Person p")
                .build();
    }

    @Bean
    public ItemProcessor<Person, Person> customItemPersonProcessor() {
        return person -> Person.from("steadyman", person.getSex(), person.getPhone());
    }

    @Bean
    public ItemWriter<Person> customItemPersonWriter() {
        return persons -> {
            for (Person person : persons) {
                log.info("name = {}, sex = {}, phone = {}", person.getName(), person.getSex(), person.getPhone());
            }
        };
    }

}
