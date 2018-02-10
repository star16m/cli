package star16m.utils.cli.command;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CliCommandImpl implements CommandLineRunner {
	@Autowired
	private ApplicationContext applicationContext;
    private Options options = new Options();
    private DefaultParser parser = new DefaultParser();
    private HelpFormatter formatter = new HelpFormatter();

    private void printHelp() {
    	printHelp("cli");
    }
    private void printHelp(String message) {
    	String banner = "===================================================";
        formatter.printHelp(message, banner, options, banner);
    }

    @Override
    public void run(String... args) throws Exception {
    	options = new Options();
        parser = new DefaultParser();
        formatter = new HelpFormatter();
        CliCommand command = null;
        String commandName = null;
        commandName = args[0];
        String[] commandArguments = Arrays.copyOfRange(args, 1, args.length);
        try {
        	// 1. find sub command
        	command = applicationContext.getBean(commandName, CliCommand.class);
        	
        	// 2. initialize option as sub command
        	command.initOption(this.options);
        } catch (Throwable e) {
        	
        	if (command == null) {
        		// not found sub command
        		Map<String, CliCommand> definedCommandMap = applicationContext.getBeansOfType(CliCommand.class);
        		printHelp(String.format("Please specify command. [%s]", definedCommandMap.keySet().stream().collect(Collectors.joining(", "))));
        		System.exit(1);
        	}
        	
        	printHelp();
        	System.exit(1);
        }
        try {
        	final CommandLine cl = parser.parse(options, commandArguments);
            Collection<Option> definedOptions = options.getOptions();
            if (!definedOptions.stream().allMatch(o->cl.hasOption(o.getOpt()))) {
            	log.error("Please specify required option.");
            }
            // default option [help]
            if (cl.hasOption("h")) {
            	printHelp();
            	return;
            }
            command.run(cl);
        } catch (ParseException e) {
            // default option [help]
            if (Arrays.asList(commandArguments).stream().anyMatch(o->o.equalsIgnoreCase("-h"))) {
            	printHelp();
            	return;
            }
            printHelp("Can't parse arguments.");
            System.exit(1);
        }

    }
}
