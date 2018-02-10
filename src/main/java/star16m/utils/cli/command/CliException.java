package star16m.utils.cli.command;

import java.io.IOException;

public class CliException extends Exception {

	public CliException(IOException e) {
		super(e);
	}

	private static final long serialVersionUID = 4293054230227073542L;

}
