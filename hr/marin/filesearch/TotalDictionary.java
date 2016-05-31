package hr.marin.filesearch;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The dictionary containing all the words of all the documents.
 * 
 * @author Marin
 *
 */
public class TotalDictionary {
	/**
	 * A simple data structure containing two pieces of information - the number
	 * of documents containing the word and its index in the total dictionary.
	 * 
	 * @author Marin
	 *
	 */
	private static class WordInfo {
		/**
		 * The number of documents containing the word
		 */
		int numberOfDocuments;
		/**
		 * The index of the word in the dictionary
		 */
		int index;

		/**
		 * Creates a new {@link WordInfo} object with the given parameters.
		 * 
		 * @param numberOfDocuments
		 *            The number of documents containing the word
		 * @param index
		 *            The index of the word in the dictionary
		 */
		WordInfo(int numberOfDocuments, int index) {
			this.numberOfDocuments = numberOfDocuments;
			this.index = index;
		}
	}

	/**
	 * A map that pairs the words of this dictionary with their {@link WordInfo}
	 * .
	 */
	private Map<String, WordInfo> wordMap;

	/**
	 * Creates a new {@link TotalDictionary} from the given list of
	 * {@link DocumentDictionary} objects.<br>
	 * Each document is represented by a {@link DocumentDictionary} and so the
	 * {@link TotalDictionary} is created as a union of all the
	 * {@link DocumentDictionary} objects.
	 * 
	 * @param docDicts
	 *            A list of all {@link DocumentDictionary} objects.
	 */
	public TotalDictionary(List<DocumentDictionary> docDicts) {
		wordMap = new HashMap<>();
		int index = 0;

		WordInfo wordInfo = null;
		for (DocumentDictionary docDict : docDicts) {
			for (String word : docDict.getWordSet()) {
				wordInfo = wordMap.get(word);

				if (wordInfo != null) {
					wordInfo.numberOfDocuments++;
				} else {
					wordMap.put(word, new WordInfo(1, index));
					index++;
				}
			}
		}
	}

	/**
	 * Gets the number of words in the dictionary.
	 * 
	 * @return The number of words in the dictionary.
	 */
	public int size() {
		return wordMap.size();
	}

	/**
	 * The index of a word in the dictionary.
	 * 
	 * @param word
	 *            The word whose index in the dictionary is to be returned.
	 * @return The idnex of the given word in the dictionary.
	 */
	public int indexOf(String word) {
		WordInfo wordInfo = wordMap.get(word);
		return (wordInfo != null) ? wordInfo.index : -1;
	}

	/**
	 * The number of all the documents that contain the given word.
	 * 
	 * @param word
	 *            The word that all the documents are checked to contain.
	 * @return The number of documents with the given string.
	 */
	public int numberOfDocumentsWith(String word) {
		WordInfo wordInfo = wordMap.get(word);
		return (wordInfo != null) ? wordInfo.numberOfDocuments : 0;
	}
}
