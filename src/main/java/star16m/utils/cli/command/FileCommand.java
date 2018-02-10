package star16m.utils.cli.command;

import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;
import org.springframework.stereotype.Component;

@Component("file")
public class FileCommand implements CliCommand {

	@Override
	public void initOption(final Options options) {
        options.addOption(CliCommandUtil.getOption("d", "dir", "target directory", true, true, true));
        options.addOption(CliCommandUtil.getOption("t", "type", "Print file type. [d|f]", false, true, true));	
	}

	@Override
	public void run(final CommandLine commandLine) throws CliException {
		String dirString = commandLine.getOptionValue("d");
		final boolean hasType = commandLine.hasOption("t");
        final String type = commandLine.getOptionValue("t");
        try {
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
		} catch (IOException e) {
			throw new CliException(e);
		}
	}
}