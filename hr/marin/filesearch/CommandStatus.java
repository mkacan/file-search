package hr.marin.filesearch;

/**
 * <p>
 * An enumeration containing the status returned by every command after it has
 * been executed. Used by the console to decide whether to continue executing
 * commands or to stop.
 * </p>
 * 
 * @author Marin
 *
 */
public enum CommandStatus {
	/**
	 * If the console should continue to execute commands after the execution of the current command
	 */
	CONTINUE, 
	/**
	 * If the console should stop executing commands after the execution of the current command
	 */
	STOP;
}
