package hr.marin.filesearch;

/**
 * <p>
 * A class that contains utility methods for separating a given command into the
 * command name string and the command arguments string.
 * </p>
 * 
 * @author Marin
 */
public class CommandParser {
	/**
	 * Gets the name of the given command.
	 * 
	 * @param line
	 *            The line containing the command.
	 * @return The name of the contained command.
	 */
	public static String getCommandName(String line) {
		String trimmed = line.trim();
		int indexOfSpace = trimmed.indexOf(' ');

		return (indexOfSpace == -1) ? trimmed : trimmed.substring(0, indexOfSpace);
	}

	/**
	 * Gets the arguments of the given command.
	 * 
	 * @param line
	 *            The line containing the command.
	 * @return The arguments of the contained command.
	 */
	public static String getCommandArguments(String line) {
		String trimmed = line.trim();
		int indexOfSpace = trimmed.indexOf(' ');

		return (indexOfSpace == -1) ? "" : trimmed.substring(indexOfSpace + 1).trim();
	}
}
