package hr.marin.filesearch;

import java.nio.file.Path;

/**
 * A simple data structure containing two pieces of information - the path of a
 * document and its calculated similarity (to another document).
 * 
 * @author Marin
 *
 */
public class DocumentInfo {
	/**
	 * The path of the document
	 */
	private Path path;
	/**
	 * The calculated similarity of the document
	 */
	private double similarity;

	/**
	 * Creates a new {@link DocumentInfo} object with the given arguments.
	 * @param path The path of the document
	 * @param similarity The calculated similarity of the document
	 */
	public DocumentInfo(Path path, double similarity) {
		super();
		this.path = path;
		this.similarity = similarity;
	}

	/**
	 * Gets the path of the document.
	 * @return The path of the document
	 */
	public Path getPath() {
		return path;
	}

	/**
	 * Gets the calculated similarity of the document.
	 * @return The calculated similarity of the document
	 */
	public double getSimilarity() {
		return similarity;
	}
}
