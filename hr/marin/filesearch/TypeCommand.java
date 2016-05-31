package hr.marin.filesearch;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * <p>
 * An implementation of the {@link ICommand} interface that represents the type
 * command of the console.<br>
 * The command writes out the document stored at the index specified in the
 * arguments, in the results of the query command that had to have been executed
 * before this command.<br>
 * If no query command was executed before, this command will do nothing.
 * </p>
 * 
 * @author Marin
 */
public class TypeCommand implements ICommand {

	/**
	 * The defined constant representing the dashed line used to mark the
	 * beginning and the end of a file that is written to the screen.
	 */
	private static final String DASHED_LINE = "----------------------------------------------------------";

	@Override
	public CommandStatus execute(String arguments, Environment environment) {
		// TODO
		List<DocumentInfo> results = environment.getResults();

		if (results == null) {
			System.out.println("Ovu narebu nema smisla pozivati ako nije prethodno pozvan query (nema rezultata).");
			return CommandStatus.CONTINUE;
		}

		Integer index = null;
		try {
			index = Integer.parseInt(arguments.trim());
		} catch (NumberFormatException e) {
			System.out.println("Argument naredbe nije ispravan cijeli broj: " + arguments);
			return CommandStatus.CONTINUE;
		}

		if (index < 0 || index >= results.size()) {
			System.out.println("Ne postoji rezultat sa indexom " + index);
			return CommandStatus.CONTINUE;
		}

		Path document = results.get(index).getPath().toAbsolutePath();
		System.out.println("Dokument: " + document);

		System.out.println(DASHED_LINE);

		try {
			System.out.println(new String(Files.readAllBytes(document), StandardCharsets.UTF_8));
		} catch (IOException e) {
			System.out.println("Greška prilikom čitanja datoteke: " + document);
			return CommandStatus.CONTINUE;
		}

		System.out.println(DASHED_LINE);

		return CommandStatus.CONTINUE;
	}
}
