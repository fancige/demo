package com.fancige.bean;

/**
 * <p>
 * This class is used to store some histories represented by an URL name.
 * </p>
 * <p>
 * This size of the histories is immuable, therefore the the oldest history
 * would be replaced by the second oldest history if you add a new history when
 * full.
 * </p>
 * 
 * @author fancige
 * 
 */
public class History {
	private String[] histories;
	/**
	 * Points to the current position used to store the next history.
	 */
	private int point;
	private int size;

	public History() {
		init(10);
	}

	public History(int size) {
		init(size);
	}

	private void init(int size) {
		histories = new String[size];
		point = 0;
		this.size = size;
	}

	public int size() {
		return size;
	}

	public void add(String url) {
		histories[point++] = url;

		if (size == point)
			point = 0;
	}

	/**
	 * Gets the most recent history. This method is equals to get(0);
	 * 
	 * @return An url representing the most recent history or null if there is
	 *         no this history.
	 * @see #get(int)
	 */
	public String get() {
		return get(0);
	}

	/**
	 * <p>
	 * Gets a history.
	 * </p>
	 * <p>
	 * The offset parameter specifies the offset position to the most recent
	 * history, if the value is 0, then you will get the most recent history
	 * </p>
	 * <p>
	 * The valid value: > 0 && < the size of histories - 1.
	 * </p>
	 * 
	 * @param offset
	 *            the offset position to the most recent one.
	 * @return A url representing the history.
	 */
	public String get(int offset) {
		if (offset > size - 1 || offset < 0) {
			throw new ArrayIndexOutOfBoundsException();
		} else {
			int index = point - 1 - offset;
			if (index < 0)
				index += size;
			return histories[index];
		}
	}

	public void clear() {
		init(size);
	}

	@Override
	public String toString() {
		String str = "[";
		for (int i = 0; i < size; i++) {
			str = str + histories[i] + "  ";
		}
		str = str + "]";
		return str;
	}
}
