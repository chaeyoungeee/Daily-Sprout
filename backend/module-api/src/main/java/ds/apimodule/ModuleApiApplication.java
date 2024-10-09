package ds.apimodule;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"ds.coremodule", "ds.commonmodule", "ds.apimodule"})
@EnableJpaAuditing
@EntityScan(basePackages = "ds.coremodule")
@EnableJpaRepositories(basePackages = "ds.coremodule")
public class ModuleApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ModuleApiApplication.class, args);
	}
}
