package star16m.utils.cli;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CliApplication {

	public static void main(String[] args) {
		args = new String[] {"file", "-d", ".", "-t", "d"};
		SpringApplication.run(CliApplication.class, args);
	}
}