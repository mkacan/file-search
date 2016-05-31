package hr.marin.filesearch;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 * The class represents an environment for the commands of the document analyzer
 * program {@link Konzola} to execute in.
 * </p>
 * 
 * @author Marin
 *
 */
public class Environment {
	/**
	 * The dictionary of all the words in all the documents that are analyzed
	 */
	private TotalDictionary totalDictionary;
	/**
	 * The list of vectors representing all the documents that are analyzed
	 */
	private List<DocumentVector> vectors;
	/**
	 * The set of all the stop words (words that are omitted from the
	 * dictionary)
	 */
	private Set<String> stopWordsSet;
	/**
	 * A list of results of the last query operation
	 */
	private List<DocumentInfo> results;
	/**
	 * A map that pairs command names with their {@link ICommand}
	 * implementations.
	 */
	private Map<String, ICommand> commands;

	/**
	 * Creates a new {@link Environment} object with the given total dictionary,
	 * list of document dictionaries and set of stow ords.
	 * 
	 * @param totalDictionary
	 *            The dictionary of all the words in all the documents that are
	 *            analyzed
	 * @param docDictList
	 *            A list of {@link DocumentDictionary} objects of all the
	 *            documents that are analyzed
	 * @param stopWords
	 *            The set of all the stop words (words that are omitted from the
	 *            dictionary)
	 */
	public Environment(TotalDictionary totalDictionary, List<DocumentDictionary> docDictList, Set<String> stopWords) {
		this.totalDictionary = totalDictionary;
		this.vectors = docDictsToDocVectors(docDictList, totalDictionary);
		this.stopWordsSet = stopWords;
		initCommands();
	}

	/**
	 * Method that initialized the commands map.
	 */
	private void initCommands() {
		commands = new HashMap<>();
		commands.put("query", new QueryCommand());
		commands.put("results", new ResultsCommand());
		commands.put("type", new TypeCommand());
		commands.put("exit", new ExitCommand());
	}

	/**
	 * Methods creates a list of docVectors using the given list of document
	 * dictionaries and the total dictionary.
	 * 
	 * @param docDicts
	 *            A list of {@link DocumentDictionary} objects of all the
	 *            documents that are analyzed
	 * @param totalDict
	 *            The dictionary of all the words in all the documents that are
	 *            analyzed
	 * @return A list of vectors representing all the documents that are
	 *         analyzed
	 */
	private List<DocumentVector> docDictsToDocVectors(List<DocumentDictionary> docDicts, TotalDictionary totalDict) {
		List<DocumentVector> docVectors = new LinkedList<>();

		for (DocumentDictionary docDict : docDicts) {
			docVectors.add(new DocumentVector(docDict, totalDict));
		}

		return docVectors;
	}

	/**
	 * Method executes the command with the given command name using the given
	 * command arguments.
	 * 
	 * @param commandName
	 *            The name of the command that is to be executed
	 * @param commandArguments
	 *            The arguments of the command that is to be executed
	 * @return The status after the command execution
	 */
	public CommandStatus execute(String commandName, String commandArguments) {
		ICommand command = commands.get(commandName);
		if (command == null) {
			System.out.println("Ne postoji naredba: " + commandName);
			return CommandStatus.CONTINUE;
		}

		return command.execute(commandArguments, this);
	}

	/**
	 * Gets the list of results of the last query operation.
	 * 
	 * @return The list of results of the last query operation
	 */
	public List<DocumentInfo> getResults() {
		return results;
	}

	/**
	 * Sets the results of the last query operation.
	 * 
	 * @param results
	 *            The new results of the last query operation
	 */
	public void setResults(List<DocumentInfo> results) {
		this.results = results;
	}

	/**
	 * Gets the total dictionary used by this environment.
	 * 
	 * @return The total dictionary used by this environment
	 */
	public TotalDictionary getTotalDictionary() {
		return totalDictionary;
	}

	/**
	 * Gets the all the document vectors used in this environment.
	 * 
	 * @return A list of document vectors used in this environment
	 */
	public List<DocumentVector> getVectors() {
		return vectors;
	}

	/**
	 * Gets all the stop words used by this environment.
	 * 
	 * @return A set of all the stop words used by this environment
	 */
	public Set<String> getStopWordsSet() {
		return stopWordsSet;
	}
}
