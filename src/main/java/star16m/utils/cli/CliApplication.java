package star16m.utils.cli;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CliApplication {

	public static void main(String[] args) {
		//args = new String[] {"log", "-l", "target/logfile.log"};
		SpringApplication.run(CliApplication.class, args);
	}
}