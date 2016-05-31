package hr.marin.filesearch;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.LinkedList;
import java.util.List;

/**
 * <p>
 * An implementation of {@link FileVisitor} adds all the paths of all the files
 * in the visited directory structure to a list that can then be retrieved from
 * the object.
 * </p>
 * 
 * @author Marin
 *
 */
public class DocumentVisitor implements FileVisitor<Path> {
	/**
	 * A list of the paths of all the document in the visited directory
	 * structure
	 */
	private List<Path> documents;

	/**
	 * Creates a new empty {@link DocumentVisitor}.
	 */
	public DocumentVisitor() {
		documents = new LinkedList<Path>();
	}

	/**
	 * Retrieves the list of paths of all the documents in the visited directory
	 * structure.
	 * 
	 * @return The list of paths of all the documents in the visited directory
	 *         structure
	 */
	public List<Path> getList() {
		return documents;
	}

	@Override
	public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
		return FileVisitResult.CONTINUE;
	}

	@Override
	public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
		if (Files.isReadable(file)) {
			documents.add(file);
		}
		return FileVisitResult.CONTINUE;
	}

	@Override
	public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
		return FileVisitResult.CONTINUE;
	}

	@Override
	public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
		return FileVisitResult.CONTINUE;
	}
}
