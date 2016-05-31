package hr.marin.filesearch;

import java.nio.file.Path;

/**
 * A vector representing a single document in the collections of all documents.
 * 
 * @author Marin
 *
 */
public class DocumentVector extends Vector {
	/**
	 * The document represented by this vector.
	 */
	private Path document;

	/**
	 * Creates a new {@link DocumentVector} with the given document dictionary
	 * and the given total dictionary (a dictionary of all the words in all the
	 * documents).
	 * 
	 * @param docDict
	 *            The document dictionary representing a single document.
	 * @param totalDict
	 *            The total dictionary representing all the documents.
	 */
	public DocumentVector(DocumentDictionary docDict, TotalDictionary totalDict) {
		super(totalDict.size());
		this.document = docDict.getDocumentPath();

		int index;
		int totalSize = totalDict.size();
		for (String word : docDict.getWordSet()) {
			index = totalDict.indexOf(word);
			if (index == -1) {
				continue;
			}
			set(index, Utilities.tfIdf(docDict.occurences(word), totalSize, totalDict.numberOfDocumentsWith(word)));
		}
	}

	/**
	 * Gets the path of the document that this vector represents.
	 * 
	 * @return The path of the document that this vector represents.
	 */
	public Path getDocumentPath() {
		return document;
	}

}
