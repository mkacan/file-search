package hr.marin.filesearch;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * <p>
 * An implementation of the {@link ICommand} interface that represents the query
 * command of the console.<br>
 * The command returns a sorted list of documents that are the most similar to
 * the string given as the argument and whose similarity is greater than 0.
 * </p>
 * 
 * @author Marin
 *
 */
public class QueryCommand implements ICommand {

	/**
	 * The default precision with which double numbers are compared.
	 */
	private static final double PRECISION = 1E-6;

	@Override
	public CommandStatus execute(String arguments, Environment environment) {
		DocumentDictionary queryDictionary = DocumentDictionary.fromString(arguments, environment.getStopWordsSet());
		DocumentVector queryVector = new DocumentVector(queryDictionary, environment.getTotalDictionary());

		List<DocumentVector> docVectors = environment.getVectors();
		List<DocumentInfo> docInfos = new ArrayList<>();

		for (DocumentVector docVector : docVectors) {
			double normProduct = docVector.norm() * queryVector.norm();
			double scalarProduct = docVector.scalarProduct(queryVector);
			if (isZero(normProduct)) {
				docInfos.add(new DocumentInfo(docVector.getDocumentPath(), 0.0));
			} else {
				docInfos.add(new DocumentInfo(docVector.getDocumentPath(), scalarProduct / normProduct));
			}
		}

		Collections.sort(docInfos, (o1, o2) -> Double.valueOf(o2.getSimilarity()).compareTo(o1.getSimilarity()));

		Iterator<DocumentInfo> it = docInfos.iterator();

		int numOfResults = 0;
		DocumentInfo docInfo = null;
		while (it.hasNext()) {
			docInfo = it.next();
			if (numOfResults >= 10 || isZero(docInfo.getSimilarity())) {
				it.remove();
			}
		}

		environment.setResults(docInfos);
		environment.execute("results", null);

		return CommandStatus.CONTINUE;
	}

	/**
	 * Checks whether a number of type double is zero with the default precision.
	 * 
	 * @param num
	 *            The number that is checked for being equal to zero with the
	 *            default precision.
	 * @return True if the number is equal to zero, false otherwise.
	 */
	private static boolean isZero(double num) {
		return Math.abs(num) < PRECISION;
	}

}
