package hr.marin.filesearch;

/**
 * <p>
 * The interface that specifies the methods that a class needs to implements in
 * order to be an console command.<br>
 * </p>
 * 
 * @author Marin
 *
 */
public interface ICommand {
	/**
	 * The method that is called when the command is executed. It performs this
	 * commands operation using the given arguments and environment.
	 * 
	 * @param arguments
	 *            The arguments given by the user when calling this command.
	 * @param environment
	 *            The environment where the command executes.
	 * @return The status after the execution of the command.
	 */
	public CommandStatus execute(String arguments, Environment environment);
}
