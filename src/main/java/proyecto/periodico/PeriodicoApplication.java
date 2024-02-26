package proyecto.periodico;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
public class PeriodicoApplication {

	public static void main(String[] args) {
		SpringApplication.run(PeriodicoApplication.class, args);
	}

}