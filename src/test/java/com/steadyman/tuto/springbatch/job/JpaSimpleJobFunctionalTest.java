package com.steadyman.tuto.springbatch.job;

import com.steadyman.tuto.springbatch.configuration.TestBatchConfig;
import com.steadyman.tuto.springbatch.repository.EmployeeRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBatchTest
@SpringBootTest
@ContextConfiguration(classes = {JpaSimpleJobConfiguration.class, TestBatchConfig.class})
@ActiveProfiles("test")
public class JpaSimpleJobFunctionalTest {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    public void personJobTest() throws Exception {
        JobExecution result = jobLauncherTestUtils.launchJob();
        Assert.assertEquals(result.getStatus(), BatchStatus.COMPLETED);
        employeeRepository.findAll().stream()
                .forEach(employee -> Assert.assertEquals(employee.getDepartmentId().longValue(), 4L));
    }
}
