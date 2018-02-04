package star16m.utils.cli.command;

import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;

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
public class FileCommand implements CommandLineRunner {
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
    	printHelp(null);
    }
    private void printHelp(String message) {
        System.out.println("===================================================");
        if (message != null) {System.out.println(message);}
        formatter.printHelp("cli [-t <type[f|d]>]", options);
        System.out.println("===================================================");
    }

    @Override
    public void run(String... args) throws Exception {

        CommandLine cl = null;
        try {
            cl = parser.parse(options, args);
        } catch (ParseException e) {
            printHelp("Can't parse arguments.");
            System.exit(1);
        }
        if (cl.hasOption("h")) {
            printHelp();
            return;
        }
        String dirString = cl.getOptionValue("d");
        if (!cl.hasOption("d") || StringUtils.isEmpty(dirString)) {
            printHelp("Please specify directory.");
            return;
        }
        final boolean hasType = cl.hasOption("t");
        final String type = cl.getOptionValue("t");
        if (hasType && (!type.equalsIgnoreCase("d") && !type.equalsIgnoreCase("f"))) {
        	printHelp("Please specify type [d|f].");
        	return;
        }
        Files.walk(Paths.get(dirString), FileVisitOption.FOLLOW_LINKS)
        .sorted(Comparator.reverseOrder()) // as default
        .filter((f)->((hasType && type.equalsIgnoreCase("d") && f.toFile().isDirectory()) || (hasType && type.equalsIgnoreCase("f") && f.toFile().isFile())))
        .forEach((f) -> {
    		try {
				System.out.println(f.toFile().getCanonicalPath());
			} catch (IOException e) {
				e.printStackTrace();
			}
        })
        ;
    }
}
