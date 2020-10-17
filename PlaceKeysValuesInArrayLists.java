package tree;

import java.util.*;

/**
 * This task places key/values in two arrays in the order in which the
 * key/values are seen during the traversal. If no keys/values are found the
 * ArrayList will be empty (constructor creates two empty ArrayLists).
 *
 * @param <K>
 * @param <V>
 */

public class PlaceKeysValuesInArrayLists<K, V> implements TraversalTask<K, V> {
	private ArrayList<K> keyValues;

	private ArrayList<V> values;

	/**
	 * Creates two ArrayList objects: one for the keys and one for the values.
	 */

	public PlaceKeysValuesInArrayLists() {

		keyValues = new ArrayList<K>();
		values = new ArrayList<V>();
	}

	/**
	 * Adds key/value to the corresponding ArrayLists.
	 */
	public void performTask(K key, V value) {

		keyValues.add(key);

		values.add(value);

	}

	/**
	 * Returns reference to ArrayList holding keys.
	 * 
	 * @return ArrayList
	 */
	public ArrayList<K> getKeys() {
		// throw new UnsupportedOperationException("You must implement this method.");
		return keyValues;

	}

	/**
	 * Returns reference to ArrayList holiding values.
	 * 
	 * @return ArrayList
	 */
	public ArrayList<V> getValues() {
		// throw new UnsupportedOperationException("You must implement this method.");
		return values;

	}
}
