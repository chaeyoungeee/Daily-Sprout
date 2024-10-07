package ds.batchmodule.scheduler;

import java.time.LocalDateTime;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class JobScheduler {

    private final JobLauncher jobLauncher;
    private final JobRegistry jobRegistry;

    protected abstract String getJobName();

    protected JobParameters getJobParameters() {
        String time = LocalDateTime.now().toString();
        return new JobParametersBuilder().addString("time", time).toJobParameters();
    }

    protected void run() {
        try {
            Job job = jobRegistry.getJob(getJobName());
            jobLauncher.run(job, getJobParameters());
        } catch (NoSuchJobException | JobInstanceAlreadyCompleteException | JobExecutionAlreadyRunningException |
                 JobParametersInvalidException | JobRestartException e) {
            throw new RuntimeException(e);
        }
    };
}