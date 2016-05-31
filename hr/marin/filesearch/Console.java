package hr.marin.filesearch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * This class represents a simple console document analyzing program. The user
 * must provide the path to the root folder that itself contains or whose
 * subdirectories contain documents that will be analyzed.<br>
 * </p>
 * <p>
 * The user can perform queries with strings to find the documents that are the
 * most similar to that string.<br>
 * The user can also write obtained documents to the screen.
 * </p>
 * 
 * @author Marin
 *
 */
public class Conzola {
	/**
	 * The default charset used to read files
	 */
	private static final Charset CHARSET = StandardCharsets.UTF_8;
	/**
	 * The path of the file containing all the stop words for the croatian
	 * language
	 */
	private static final String STOPWORDS_FILE = "hrvatski_stoprijeci.txt";

	/**
	 * This method is called once the program is run.
	 * 
	 * @param args
	 *            Command line arguments. Should have only one: the path to the
	 *            root folder that itself contains or whose subdirectories
	 *            contain documents that will be analyzed.
	 */
	public static void main(String[] args) {
		Path dir = obtainPath(args);

		Set<String> stopWordsSet = null;
		try {
			stopWordsSet = Utilities.getStopWordsSet(Paths.get(STOPWORDS_FILE), CHARSET);
		} catch (IOException e) {
			System.err.println("Failed to read the file: " + STOPWORDS_FILE);
			return;
		}

		List<DocumentDictionary> docDicts = null;
		try {
			docDicts = Utilities.createDocumentDictionaries(dir, stopWordsSet, CHARSET);
		} catch (IOException e) {
			System.err.println("Failed to read a file: " + e.getMessage());
			return;
		}

		TotalDictionary dictionary = new TotalDictionary(docDicts);
		Environment environment = new Environment(dictionary, docDicts, stopWordsSet);

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		System.out.println("Veličina rječnika je " + dictionary.size() + " riječi.");

		while (true) {
			System.out.print("Enter command > ");
			String line = getLine(reader).trim();
			String commandName = CommandParser.getCommandName(line);
			String commandArguments = CommandParser.getCommandArguments(line);

			if (environment.execute(commandName, commandArguments) == CommandStatus.STOP) {
				break;
			}
		}

		try {
			reader.close();
		} catch (IOException ignorable) {
		}
	}

	/**
	 * Checks whether argument given via the command line is a path to an
	 * existing directory and returns that path.
	 * 
	 * @param args
	 *            The command line arguments. Should have only 1 element.
	 * @return The path obtained from the argument.
	 */
	private static Path obtainPath(String[] args) {
		if (args.length != 1) {
			System.err.println("Must provide exactly 1 argument.");
			System.exit(1);
		}

		Path path = null;
		try {
			path = Paths.get(args[0]);
		} catch (InvalidPathException e) {
			System.err.println("The provided argument is not a valid path.");
			System.exit(1);
		}

		if (!Files.isDirectory(path)) {
			System.err.println("The provided argument is not a path to a directory.");
			System.exit(1);
		}

		return path;
	}

	/**
	 * Method used to read a line from a {@link BufferedReader}.
	 * 
	 * @param reader
	 *            A {@link BufferedReader} object that is used to read a line of
	 *            text.
	 * @return The {@link String} containg the line that was read from the
	 *         {@link BufferedReader}.
	 */
	private static String getLine(BufferedReader reader) {
		String line = null;
		try {
			line = reader.readLine();
		} catch (IOException e) {
			System.out.println("Error: Failed to read from keyboard.");
			System.exit(1);
		}

		return line;
	}
}
