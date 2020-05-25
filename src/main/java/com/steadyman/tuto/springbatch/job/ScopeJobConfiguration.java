package com.steadyman.tuto.springbatch.job;

import com.steadyman.tuto.springbatch.tasklet.SimpleJobTasklet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class ScopeJobConfiguration {
    public static final String JOB_NAME = "scopeJob";

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final SimpleJobTasklet simpleJobTasklet;

    public ScopeJobConfiguration(JobBuilderFactory jobBuilderFactory,
                                 StepBuilderFactory stepBuilderFactory,
                                 SimpleJobTasklet simpleJobTasklet) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.simpleJobTasklet = simpleJobTasklet;
    }

    @Bean("scopeJob")
    public Job scopeJob() {
        return jobBuilderFactory.get(JOB_NAME)
                .start(scopeStep1(null)) // null을 할당하는 이유는, Job Parameter의 할당이 어플리케이션 실행 시에 하지 않기 때문이다.
                .next(scopeStep2())
                .build();
    }

    @Bean
    @JobScope
    public Step scopeStep1(@Value("#{jobParameters[requestDate]}") String requestDate) {
        return stepBuilderFactory.get("scopeStep1")
                .tasklet((contribution, chunkContext) -> {
                    log.info(">>> This is scopeStep1");
                    log.info(">>> requestDate = {}", requestDate);
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    public Step scopeStep2() {
        return stepBuilderFactory.get("scopeStep2")
                .tasklet(simpleJobTasklet)
                .build();
    }
}
