package com.steadyman.tuto.springbatch.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class JobLauncherController {
    private final JobLauncher jobLauncher;
    private final Job job;

    public JobLauncherController(JobLauncher jobLauncher, @Qualifier("scopeJob") Job job) {
        this.jobLauncher = jobLauncher;
        this.job = job;
    }

    @GetMapping("/job/test")
    public String handle(@RequestParam("requestDate") String requestDate) {
        try {
            JobParameters jobParameters = new JobParametersBuilder()
                    .addString("requestDate", requestDate)
                    .toJobParameters();
            jobLauncher.run(job, jobParameters);
            return "OK";
        } catch (Exception ex) {
            log.error("Error = {}", ex.getMessage());
            return ex.getMessage();
        }
    }

}
