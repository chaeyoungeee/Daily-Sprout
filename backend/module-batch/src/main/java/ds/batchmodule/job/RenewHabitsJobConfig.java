package ds.batchmodule.job;

import ds.batchmodule.mapper.ActiveHabitsRowMapper;
import ds.batchmodule.vo.AchievementHabitsVO;
import ds.batchmodule.vo.ActiveHabitsVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.batch.item.support.SynchronizedItemStreamReader;
import org.springframework.batch.item.support.builder.SynchronizedItemStreamReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class RenewHabitsJobConfig {

    private final String NAME = "renewHabits";

    private final JobRepository jobRepository;
    private final PlatformTransactionManager tx;
    private final DataSource dataSource;

    private int chunkSize;
    private int poolSize;

    @Value("${spring.batch.chunk_size}")
    public void setChunkSize(int chunkSize) {
        this.chunkSize = chunkSize;
    }

    @Value("${spring.batch.pool_size}")
    public void setPoolSize(int poolSize) {
        this.poolSize = poolSize;
    }

    @Bean
    public TaskExecutor executor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(poolSize);
        executor.setMaxPoolSize(poolSize * 2);
        executor.setThreadNamePrefix("multi-thread-");
        executor.setWaitForTasksToCompleteOnShutdown(Boolean.TRUE);
        executor.initialize();

        return executor;
    }

    @Bean
    public Job renewHabitsJob(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        String JOB_NAME = NAME + "Job";

        return new JobBuilder(JOB_NAME, jobRepository)
            .incrementer(new RunIdIncrementer())
            .start(renewHabitsStep())
            .build();
    }

    @Bean
    @JobScope
    public Step renewHabitsStep() {
        String STEP_NAME = NAME + "Step";

        return new StepBuilder(STEP_NAME, jobRepository)
            .<ActiveHabitsVO, AchievementHabitsVO>chunk(chunkSize, tx) // reader에서 읽은 데이터 타입, writer에서 받을 데이터 타입
            .reader(activeHabitsReader(null))
            .processor(activeHabitsProcessor(null))
            .writer(renewHabitsWriter())
            .taskExecutor(executor())
            .build();
    }

    @Bean
    @StepScope
    public SynchronizedItemStreamReader<ActiveHabitsVO> activeHabitsReader(@Value("#{jobParameters['time']}") String time) {
        String READER_NAME = "ActiveHabitsReader";

        LocalDate date = LocalDateTime.parse(time).toLocalDate();
        DayOfWeek dayOfWeek = date.getDayOfWeek();

        // String sql = "SELECT h.habit_id, hd.day_week " +
        //     "FROM habit h " +
        //     "INNER JOIN habit_day hd ON h.habit_id = hd.habit_id " +
        //     "WHERE h.status = 'ACTIVE' AND " +
        //     "hd.day_week = '" + dayOfWeek.toString() + "'";

        String sql = "SELECT h.habit_id, hd.day_week " +
            "FROM habit h " +
            "INNER JOIN habit_day hd ON h.habit_id = hd.habit_id " +
            "WHERE h.status = 'ACTIVE'";

        JdbcCursorItemReader<ActiveHabitsVO> itemReader = new JdbcCursorItemReaderBuilder<ActiveHabitsVO>()
            .name(READER_NAME)
            .fetchSize(chunkSize)
            .dataSource(dataSource)
            .rowMapper(new ActiveHabitsRowMapper())
            .sql(sql)
            .saveState(false)
            .build();

        return new SynchronizedItemStreamReaderBuilder<ActiveHabitsVO>()
            .delegate(itemReader)
            .build();
    }

    @Bean
    @StepScope
    public ItemProcessor<ActiveHabitsVO, AchievementHabitsVO> activeHabitsProcessor(@Value("#{jobParameters['time']}") String time) {
        return activeHabitsVO -> {

            LocalDate date = LocalDateTime.parse(time).toLocalDate();

            switch (activeHabitsVO.dayWeek()) {
                case "MONDAY":
                    date = date.with(DayOfWeek.MONDAY);
                    break;
                case "TUESDAY":
                    date = date.with(DayOfWeek.TUESDAY);
                    break;
                case "WEDNESDAY":
                    date = date.with(DayOfWeek.WEDNESDAY);
                    break;
                case "THURSDAY":
                    date = date.with(DayOfWeek.THURSDAY);
                    break;
                case "FRIDAY":
                    date = date.with(DayOfWeek.FRIDAY);
                    break;
                case "SATURDAY":
                    date = date.with(DayOfWeek.SATURDAY);
                    break;
                case "SUNDAY":
                    date = date.with(DayOfWeek.SUNDAY);
                    break;
            }

            return new AchievementHabitsVO(activeHabitsVO.habitId(), date);
        };
    }

    @Bean
    @StepScope
    public JdbcBatchItemWriter<AchievementHabitsVO> renewHabitsWriter() {
        String sql = "INSERT INTO achievement (habit_id, habit_date, created_at, updated_at, status) " +
            "VALUES (:habitId, :habitDate, now(), now(), 'NOT_ACHIEVED')";

        return new JdbcBatchItemWriterBuilder<AchievementHabitsVO>()
            .dataSource(dataSource)
            .sql(sql)
            .beanMapped()
            .build();
    }

}
