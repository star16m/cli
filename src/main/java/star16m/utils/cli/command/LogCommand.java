package star16m.utils.cli.command;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;
import org.springframework.stereotype.Component;

@Component("log")
public class LogCommand implements CliCommand {

	@Override
	public void initOption(Options options) {
		options.addOption(CliCommandUtil.getOption("l", "logfile", "target log file", true, true, true));
	}

	@Override
	public void run(CommandLine commandLine) throws Throwable {
		String logfile = commandLine.getOptionValue("l");
		Path log = Paths.get(logfile);
		System.out.println(log.toFile().exists());
		Files.createFile(log);
		System.out.println(log.toFile().exists());
		
	}

}
