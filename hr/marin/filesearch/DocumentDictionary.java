package hr.marin.filesearch;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * The dictionary containing all the words of a single document or a single
 * string.
 * 
 * @author Marin
 *
 */
public class DocumentDictionary {
	/**
	 * A map that pairs the words of this dictionary with their number of
	 * occurrences in the document or string.
	 */
	private Map<String, Integer> wordMap;
	/**
	 * The document from which the dictionary has been generated. Null if the
	 * dictionary has been generated from a string.
	 */
	private Path document;

	/**
	 * Creates an empty new {@link DocumentDictionary}.
	 */
	private DocumentDictionary() {
		wordMap = new HashMap<>();
		document = null;
	}

	/**
	 * Gets the number of occurrences of the given word in the dictionary.
	 * 
	 * @param wordString
	 *            The word whose number of occurrences is to be returned
	 * @return The number of occurrences of the given word
	 */
	public int occurences(String wordString) {
		Integer number = wordMap.get(wordString);
		if (number == null) {
			return 0;
		}
		return number;
	}

	/**
	 * Creates a new {@link DocumentDictionary} from a given string and a set of
	 * stop words. The dictionary will contain all the words in the string
	 * except for the stop words.
	 * 
	 * @param text
	 *            The text containing the words that will be contained in the
	 *            dictionary.
	 * @param stopWords
	 *            The words that will not be put to the dictionary if they are
	 *            found in the text.
	 * @return The created dictionary.
	 */
	public static DocumentDictionary fromString(String text, Set<String> stopWords) {
		DocumentDictionary dict = new DocumentDictionary();

		String[] words = text.trim().toLowerCase().split("\\P{L}+");

		Integer occurences = null;
		for (String word : words) {
			word = word.trim();
			if (stopWords.contains(word)) {
				continue;
			}
			occurences = dict.wordMap.get(word);
			if (occurences != null) {
				dict.wordMap.put(word, occurences + 1);
			} else {
				dict.wordMap.put(word, 1);
			}
		}

		return dict;
	}

	/**
	 * Gets a set of all the words in the {@link Dictionary}.
	 * 
	 * @return A set of all the words in the {@link Dictionary}.
	 */
	public Set<String> getWordSet() {
		return wordMap.keySet();
	}

	/**
	 * Gets the path of the document that was used to create the dictionary.
	 * Returns null if a string was used to create it, instead of a document.
	 * 
	 * @return The path of the document used to create the dictionary, or null
	 *         if document was used.
	 */
	public Path getDocumentPath() {
		return document;
	}

	/**
	 * Creates a new {@link DocumentDictionary} from a given document and a set
	 * of stop words. The dictionary will contain all the words in the document
	 * except for the stop words.
	 * 
	 * @param doc
	 *            The document containing the words that will be contained in
	 *            the dictionary.
	 * @param stopWords
	 *            The words that will not be put to the dictionary if they are
	 *            found in the text.
	 * @param charset
	 *            The charset used to read the document.
	 * @return The created dictionary.
	 * @throws IOException
	 *             If file reading was unsuccessful.
	 */
	public static DocumentDictionary fromDocument(Path doc, Set<String> stopWords, Charset charset) throws IOException {
		checkPath(doc);

		String file = new String(Files.readAllBytes(doc), charset);

		DocumentDictionary dict = fromString(file, stopWords);
		dict.document = doc;

		return dict;
	}

	/**
	 * Method checks whether the given path represents a readable file. Throws
	 * an {@link IllegalArgumentException} if that is not the case, otherwise it
	 * does nothing.
	 * 
	 * @param path
	 *            The path that is checked.
	 * @throws IllegalArgumentException
	 *             If the given path does not represents a readable file.
	 */
	private static void checkPath(Path path) {
		if (path == null) {
			throw new IllegalArgumentException("Path is null.");
		}

		if (!(Files.isRegularFile(path) && Files.isReadable(path))) {
			throw new IllegalArgumentException("Path: " + path + " is not a readable file.");
		}
	}
}
