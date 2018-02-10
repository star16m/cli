package star16m.utils.cli.command;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;

public interface CliCommand {

	public void initOption(final Options options);
	public void run(final CommandLine commandLine) throws CliException;
}
