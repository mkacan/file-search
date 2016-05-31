package hr.marin.filesearch;

/**
 * <p>
 * An implementation of the {@link ICommand} interface that represents the exit
 * command of the console.
 * </p>
 * 
 * @author Marin
 *
 */
public class ExitCommand implements ICommand {

	@Override
	public CommandStatus execute(String arguments, Environment environment) {
		return CommandStatus.STOP;
	}

}
