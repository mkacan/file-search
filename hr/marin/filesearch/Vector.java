package hr.marin.filesearch;

import java.util.Arrays;

/**
 * Class represents an implementation of the {@link AbstractVector} class.
 * 
 * @author Marin
 *
 */
public class Vector extends AbstractVector {
	/**
	 * An array of vector elements.
	 */
	private double[] elements;
	/**
	 * Number of elements of the vector.
	 */
	private int dimension;
	/**
	 * True if vector elements cannot be changed, false otherwise.
	 */
	private boolean readOnly;

	/**
	 * Creates a new vector with the given size and all elements initialized to 0.
	 * 
	 * @param dimension The dimension of the new vector.
	 */
	public Vector(int dimension) {
		this(false, true, new double[dimension]);
	}
	
	/**
	 * Creates a new vector with the given elements.
	 * 
	 * @param elements
	 *            Elements that the new vector will contain.
	 */
	public Vector(double[] elements) {
		this(false, false, elements);
	}

	/**
	 * Creates a new vector with the given elements.
	 * 
	 * @param readOnly
	 *            True if vector elements should not be able to be changed,
	 *            false otherwise.
	 * @param useGivenArray
	 *            True if the vector should just take the reference of the given
	 *            array, false if the vector should make its own copy of the
	 *            array.
	 * @param elements
	 *            Elements that the new vector will contain.
	 */
	public Vector(boolean readOnly, boolean useGivenArray, double[] elements) {
		this.readOnly = readOnly;

		if (useGivenArray) {
			this.elements = elements;
		} else {
			this.elements = Arrays.copyOf(elements, elements.length);
		}

		this.dimension = elements.length;
	}

	@Override
	public double get(int index) {
		return elements[index];
	}

	@Override
	public AbstractVector set(int index, double value) {
		if (readOnly) {
			throw new RuntimeException("Vector is read-only.");
		}

		elements[index] = value;

		return this;
	}

	@Override
	public int getDimension() {
		return dimension;
	}

	@Override
	public AbstractVector copy() {
		return new Vector(readOnly, false, elements);
	}

	/**
	 * /** Parses a String and creates a new Vector object. In the String, the
	 * elements in the should be separated by spaces.
	 * 
	 * @param elementString
	 *            String that is parsed to create the vector.
	 * @return The new Matrix created from the String.
	 * @throws IllegalArgumentException
	 *             If the given String is not parsable.
	 */
	public static Vector parseSimple(String elementString) {
		String[] split = elementString.trim().split(" +");
		double[] elements = new double[split.length];

		try {
			for (int i = 0; i < elements.length; i++) {
				elements[i] = Double.parseDouble(split[i]);
			}
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Given string contains non-parsable elements.");
		}

		return new Vector(false, true, elements);
	}

}
