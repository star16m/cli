package star16m.utils.cli.command;

import java.io.File;
import java.nio.file.Paths;

import javax.annotation.PostConstruct;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DemoCommand implements CommandLineRunner {
	private Options options = new Options();
	private DefaultParser parser = new DefaultParser();
	private HelpFormatter formatter = new HelpFormatter();
	@PostConstruct
	public void init() {
        options = new Options();
        parser = new DefaultParser();
        formatter = new HelpFormatter();

        options.addOption("h", "help", false, "Print help message.");
        options.addOption("d", "dir", true, "target directory");
        options.addOption("t", "type", true, "Print file type. [d|f]");
	}
	private void printHelp() {
		System.out.println("===================================================");
        formatter.printHelp("clifind [-t <type[f|d]>]", options);
        System.out.println("===================================================");
	}
	@Override
	public void run(String... args) throws Exception {
        
		CommandLine cl = null;
        try {
        	cl = parser.parse(options, args);
        } catch (ParseException e) {
        	printHelp();
        	System.exit(1);
        }
        if (cl.hasOption("h")) {
        	printHelp();
            return;
        }
        
        String dirString = cl.getOptionValue("d");
        if (!cl.hasOption("d") || StringUtils.isEmpty(dirString)) {
        	printHelp();
        	return;
        }
        String type = cl.getOptionValue("t");
        
        File rootFile = Paths.get(dirString).toFile();
        System.out.println(rootFile.getAbsolutePath());
        System.out.println(type);
	}

}
