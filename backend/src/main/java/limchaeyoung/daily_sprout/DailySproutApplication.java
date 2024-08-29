package limchaeyoung.daily_sprout;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class DailySproutApplication {

	public static void main(String[] args) {
		SpringApplication.run(DailySproutApplication.class, args);
	}

}
