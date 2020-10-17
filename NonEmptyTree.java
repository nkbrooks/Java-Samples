package tree;

import java.util.Collection;

/**
 * This class represents a non-empty search tree. An instance of this class
 * should contain:
 * <ul>
 * <li>A key
 * <li>A value (that the key maps to)
 * <li>A reference to a left Tree that contains key:value pairs such that the
 * keys in the left Tree are less than the key stored in this tree node.
 * <li>A reference to a right Tree that contains key:value pairs such that the
 * keys in the right Tree are greater than the key stored in this tree node.
 * </ul>
 * 
 */
public class NonEmptyTree<K extends Comparable<K>, V> implements Tree<K, V> {

	/* Provide whatever instance variables you need */
	private K key;
	private V value;
	private Tree<K, V> left;
	private Tree<K, V> right;

	/**
	 * Only constructor we need.
	 * 
	 * @param key
	 * @param value
	 * @param left
	 * @param right
	 */
	public NonEmptyTree(K key, V value, Tree<K, V> left, Tree<K, V> right) {
		this.key = key;
		this.value = value;
		this.left = left;
		this.right = right;
	}

	/**
	 * Find the value that this key is bound to in this tree.
	 * 
	 * @param key -- Key to search for
	 * @return -- value associated with the key by this Tree, or null if the key
	 *         does not have an association in this tree.
	 */
	public V search(K key) {

		if (key.compareTo(this.key) == 0) {
			return this.value;
		} else if (this.key.compareTo(key) < 0) {
			return right.search(key);
		} else {
			return left.search(key);
		}

	}

	/**
	 * Insert/update the Tree with a new key:value pair. If the key already exists
	 * in the tree, update the value associated with it. If the key doesn't exist,
	 * insert the new key value pair.
	 * 
	 * This method returns a reference to an Tree that represents the updated value.
	 * In many, but not all cases, the method may just return a reference to this.
	 * This method is annotated @CheckReturnValue because you have to pay attention
	 * to the return value; if you simply invoke insert on a Tree and ignore the
	 * return value, your code is almost certainly wrong.
	 * 
	 * @param key   -- Key
	 * @param value -- Value that the key maps to
	 * @return -- updated tree
	 */
	public NonEmptyTree<K, V> insert(K key, V value) { 
		boolean flag = false;
		if (this.key != null) {
			flag = true;
		}

		if (flag) {
			if (key.compareTo(this.key) == 0) { // From Comparable Interface.
				this.value = value;

			}
			if (this.key.compareTo(key) > 0) {
				this.left = this.left.insert(key, value);
			}
			if (lessThanZero(key)) {
				this.right = this.right.insert(key, value);
			}
		}
			return this;
		
	}

	/**
	 * Delete any binding the key has in this tree. If the key isn't bound, this is
	 * a no-op
	 * 
	 * This method returns a reference to an Tree that represents the updated value.
	 * In many, but not all cases, the method may just return a reference to this.
	 * This method is annotated @CheckReturnValue because you have to pay attention
	 * to the return value; if you simply invoke delete on a Tree and ignore the
	 * return value, your code is almost certainly wrong.
	 * 
	 * @param key -- Key
	 * @return -- updated tree
	 */

	@SuppressWarnings("finally")
	public Tree<K, V> delete(K key) {
		if (this.key.compareTo(key) == 0) {
			try {
				K maximum = left.max();
				tryDelete(key, maximum);

			} catch (TreeIsEmptyException e) {
				try {
					K minimum = right.min();
					this.key = minimum;
					value = right.search(minimum);
					right = right.delete(minimum); // Delete any binding the key has in this tree.
				} catch (TreeIsEmptyException e2) {
					return EmptyTree.getInstance();
				}
			}
		}

		try {
			if (lessThanZero(key)) {
				this.right = right.delete(key);
			} else if (this.key.compareTo(key) > 0) {
				this.left = left.delete(key);

			}
		} finally {
			return this;
		}

	}

	/**
	 * Return the maximum key in the subtree
	 * 
	 * @return maximum key
	 * @throws TreeIsEmptyException if the tree is empty
	 */

	public K max() {
		K ans = this.key;
		try {
			ans = right.max();
		} catch (TreeIsEmptyException e) { // This is a checked exception, used internally by SearchTree nodes, to
											// signal that a tree has no minimum or maximum element.

			return ans;
		}
		return ans;
	}

	/**
	 * Return the minimum key in the subtree
	 * 
	 * @return minimum key
	 * @throws TreeIsEmptyException if the tree is empty
	 */

	public K min() {
		K ans = this.key;
		try {
			ans = left.min();
		} catch (TreeIsEmptyException e) {
			return ans;
		}
		return ans;
	}

	/**
	 * Add all keys bound in this tree to the collection c. The elements can be
	 * added in any order.
	 */
	public int size() {
		return 1 + this.left.size() + this.right.size();

	}

	/**
	 * Add all keys bound in this tree to the collection c. The elements can be
	 * added in any order.
	 */
	public void addKeysToCollection(Collection<K> c) {
		if (c != null) {
			c.add(key);
			this.right.addKeysToCollection(c);

			this.left.addKeysToCollection(c);
		}
	}

	/**
	 * Returns a Tree containing all entries between fromKey and toKey, inclusive.
	 * It may not modify the original tree (a common mistake while implementing this
	 * method).
	 * 
	 * @param fromKey - Lower bound value for keys in subtree
	 * @param toKey   - Upper bound value for keys in subtree
	 * @return Tree containing all entries between fromKey and toKey, inclusive
	 */

	public Tree<K, V> subTree(K fromKey, K toKey) { 
		Tree<K, V> ans = null;
		boolean flag = false;
		if (lessThanZero(fromKey)) {
			flag = true;
		}

		if (flag) {
			ans = this.right.subTree(fromKey, toKey);

			return ans;
		} else if (toKey.compareTo(this.key) < 0) {
			ans = this.left.subTree(fromKey, toKey);

			return ans;
		} else {
			return new NonEmptyTree<K, V>(this.key, this.value, left.subTree(fromKey, toKey),
					right.subTree(fromKey, toKey));
		}
	}

	/**
	 * Returns the height (maximum level) in the tree. A tree with only one entry
	 * has a height of 1.
	 * 
	 * @return height of the tree.
	 */
	public int height() {
		int height = Math.max(left.height(), right.height());
		return height + 1; // Returns the greater of two int values.

	}

	/**
	 * Performs the specified task on the tree using an inorder traversal.
	 * 
	 * @param p object defining task
	 */
	public void inorderTraversal(TraversalTask<K, V> p) {
		if (p != null) {

			left.inorderTraversal(p);
			p.performTask(key, value);
			right.inorderTraversal(p); // Performs the specified task on the tree using an inorder traversal.
		}
	}

	/**
	 * Performs the specified task on the tree using a right tree, root, left tree
	 * traversal.
	 * 
	 * @param p object defining task
	 */

	public void rightRootLeftTraversal(TraversalTask<K, V> p) {

		if (p != null) {
			right.rightRootLeftTraversal(p);
			p.performTask(key, value);
			left.rightRootLeftTraversal(p); // Performs the specified task on the tree using a right tree, root, left
											// tree traversal.
		}
	}

	/*********** private methods *************/

	private boolean lessThanZero(K key) {

		return (this.key.compareTo(key) < 0);
	}

	private void tryDelete(K key, K maximum) {
		this.key = maximum;
		value = left.search(maximum);
		left = left.delete(maximum);
	}

}
