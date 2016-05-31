package hr.marin.filesearch;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * A utility class containing various methods used when analyzing documents.
 * 
 * @author Marin
 *
 */
public class Utilities {
	/**
	 * Retrieves a set of stop words from the given file. The words in the file
	 * must be separated by a new line.
	 * 
	 * @param file
	 *            The file containing the stop words.
	 * @param charset
	 *            The charset used to read the file.
	 * @return The created stop words set.
	 * @throws IOException
	 *             If the reading was unsuccessful.
	 */
	public static Set<String> getStopWordsSet(Path file, Charset charset) throws IOException {
		List<String> lines = Files.readAllLines(file, charset);

		Set<String> stopWordsSet = new LinkedHashSet<String>(lines);
		stopWordsSet.add("a");

		return stopWordsSet;
	}

	/**
	 * The method visits all the readable files in the directory structure of
	 * the given path and for every file found creates a
	 * {@link DocumentDictionary} using the given stop words set.
	 * 
	 * @param dir
	 *            The root directory of the directory structure that is visited.
	 * @param stopWordsSet
	 *            The set of all the stop words (words that get omitted from the
	 *            dictionary).
	 * @param charset
	 *            The charset used to read the file.
	 * @return A list of all the created {@link DocumentDictionary} objects.
	 * @throws IOException
	 *             If there was an error while visiting the given directory
	 *             structure.
	 */
	public static List<DocumentDictionary> createDocumentDictionaries(Path dir, Set<String> stopWordsSet,
			Charset charset) throws IOException {
		DocumentVisitor visitor = new DocumentVisitor();
		Files.walkFileTree(dir, visitor);

		List<DocumentDictionary> docDicts = new LinkedList<>();

		for (Path path : visitor.getList()) {
			docDicts.add(DocumentDictionary.fromDocument(path, stopWordsSet, charset));
		}

		return docDicts;
	}

	/**
	 * Method calculates the tf-idf (term frequency-inverse document frequency)
	 * of a word in a document using the given number of occurrences of that
	 * word in a file, the number of all the documents and the number of
	 * documents containing that word.
	 * 
	 * @param occurences
	 *            The number of occurrences of a word in a file
	 * @param allDocuments
	 *            The number of all the documents
	 * @param documentsWithWord
	 *            The number of documents containing that word
	 * @return The calculated tf-idf number
	 */
	public static double tfIdf(int occurences, int allDocuments, int documentsWithWord) {
		return occurences * Math.log(allDocuments / documentsWithWord);
	}
}
