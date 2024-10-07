package ds.batchmodule.scheduler;

import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class RenewHabitsJobScheduler extends JobScheduler {

    public RenewHabitsJobScheduler(JobLauncher jobLauncher, JobRegistry jobRegistry) {
        super(jobLauncher, jobRegistry);
    }

    @Override
    protected String getJobName() {
        return "renewHabitsJob";
    }

    @Scheduled(cron = "0/10 * * * * *") // @Scheduled(cron = "0 0 0 * * ?", zone = "Asia/Seoul") 매일 자정
    public void runJob() {
        run();
    }
}
