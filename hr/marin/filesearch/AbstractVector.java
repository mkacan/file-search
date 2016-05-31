package hr.marin.filesearch;

/**
 * Class represents an abstract vector in linear algebra. It offers various
 * vector operations.
 * 
 * @author Marin
 *
 */
public abstract class AbstractVector {
	/**
	 * Gets the vector component at the given index.
	 * 
	 * @param index
	 *            The index of the vector component.
	 * @return The vector component at the given index.
	 */
	public abstract double get(int index);

	/**
	 * Set the component at the given index to equal the given value.
	 * 
	 * @param index
	 *            The index of the component.
	 * @param value
	 *            The new value of the component.
	 * @return The changed vector.
	 */
	public abstract AbstractVector set(int index, double value);

	/**
	 * Gets the dimension of this vector (the number of components).
	 * 
	 * @return The dimension of this vector.
	 */
	public abstract int getDimension();

	/**
	 * Copies all the components of this vector and returns them in a new
	 * vector.
	 * 
	 * @return A copy of this vector.
	 */
	public abstract AbstractVector copy();

	/**
	 * Multiplies this vector by a scalar and returns the resulting vector.
	 * 
	 * @param byValue
	 *            The value by which the vector is multiplied.
	 * @return The new vector after the multiplication.
	 */
	public AbstractVector scalarMultiply(double byValue) {
		for (int i = this.getDimension() - 1; i >= 0; i--) {
			this.set(i, this.get(i) * byValue);
		}
		return this;
	}

	/**
	 * Calculates and returns the absolute length of this vector.
	 * 
	 * @return The norm (absolute length) of this vector.
	 */
	public double norm() {
		double sumOfSquares = 0.0;
		double component;
		for (int i = this.getDimension() - 1; i >= 0; i--) {
			component = this.get(i);
			sumOfSquares += component * component;
		}

		return Math.sqrt(sumOfSquares);
	}

	/**
	 * Normalizes this vector (divides all components by a constant so the norm
	 * of the vector is equal to 1).
	 * 
	 * @return The normalized vector.
	 */
	public AbstractVector normalize() {
		double norm = this.norm();

		for (int i = this.getDimension() - 1; i >= 0; i--) {
			this.set(i, this.get(i) / norm);
		}

		return this;
	}

	/**
	 * Calculates the scalar product of this vector and the given one and
	 * returns the result.
	 * 
	 * @param other
	 *            The other vector on which the scalar product operation is
	 *            performed
	 * @return The scalar product of this vector and the given one
	 */
	public double scalarProduct(AbstractVector other) {
		if (this.getDimension() != other.getDimension()) {
			throw new IllegalArgumentException();
		}

		double sumOfProducts = 0.0;

		for (int i = this.getDimension() - 1; i >= 0; i--) {
			sumOfProducts += this.get(i) * other.get(i);
		}

		return sumOfProducts;
	}

	/**
	 * Calculates the vector product of this vector and the given one and
	 * returns the result in a new vector.
	 * 
	 * @param other
	 *            The other vector on which the vector product operation is
	 *            performed
	 * @return The vector product of this vector and the given one
	 */
	public AbstractVector nVectorProduct(AbstractVector other) {
		if (this.getDimension() != 3 || other.getDimension() != 3) {
			throw new IllegalArgumentException();
		}

		AbstractVector vectorProduct = this.copy();
		vectorProduct.set(0, this.get(1) * other.get(2) - this.get(2) * other.get(1));
		vectorProduct.set(1, this.get(0) * other.get(2) - this.get(2) * other.get(0));
		vectorProduct.set(2, this.get(0) * other.get(1) - this.get(1) * other.get(0));

		return vectorProduct;
	}

	@Override
	public String toString() {
		return toString(3);
	}

	/**
	 * Returns a formatted String representation of the vector.
	 * 
	 * @param n
	 *            The decimal precision to which the components are written out.
	 * @return The textual representation of this vector.
	 */
	public String toString(int n) {
		int dimension = getDimension();
		StringBuilder builder = new StringBuilder(n * dimension);

		builder.append("[ ");
		for (int i = 0; i < dimension; i++) {
			builder.append(String.format("%." + n + "f ", get(i)));
		}
		builder.append("]");

		return builder.toString();
	}
}
