package hr.marin.filesearch;

import java.util.List;

/**
 * <p>
 * An implementation of the {@link ICommand} interface that represents the
 * results command of the console.<br>
 * The command displays all the results of the query command that had to have
 * been executed before this command. If no query command was executed before,
 * this command will do nothing.
 * </p>
 * 
 * @author Marin
 *
 */
public class ResultsCommand implements ICommand {

	@Override
	public CommandStatus execute(String arguments, Environment environment) {
		List<DocumentInfo> results = environment.getResults();
		if (results == null) {
			System.out.println("Ovu narebu nema smisla pozivati ako nije prethodno pozvan query (nema rezultata).");
			return CommandStatus.CONTINUE;
		}

		for (int i = 0, size = results.size(); i < size; i++) {
			System.out.format("[%2d] (%.4f) " + results.get(i).getPath().toAbsolutePath() + "%n", i, results.get(i)
					.getSimilarity());
		}

		return CommandStatus.CONTINUE;
	}

}
