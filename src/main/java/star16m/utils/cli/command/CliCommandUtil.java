package star16m.utils.cli.command;

import org.apache.commons.cli.Option;

public class CliCommandUtil {

	private CliCommandUtil(){}

	public static Option getOption(String opt, String longOpt, String description, boolean isRequired, boolean hasOptionValue, boolean optionValueIsRequired) {
		Option option = new Option(opt, longOpt, hasOptionValue, description);
		option.setRequired(isRequired);
		option.setOptionalArg(!optionValueIsRequired);
		return option;
	}
}
