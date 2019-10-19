import HashTable.HashNode;

/**
 * Hash table data structure.
 * @param <K> key
 * @param <V> value
 */
@SuppressWarnings("unchecked")
public class HashTable<K extends Comparable<K>, V> implements HashTableADT<K, V> {

	/**
	 * Hash node inner class storing a key, value, and pointer to the next node in the chain.
	 */
	@SuppressWarnings("hiding")
	private class HashNode<K, V> {
		private K key;
		private V value;
		private HashNode<K, V> next;

		private HashNode(K key, V value, HashNode<K, V> next) {
			this.key = key;
			this.value = value;
			this.next = next;
		}

		private String hashNodeToString() {
			return key + " : " + value;
		}
	}

	// instance variables for hash table
	private double loadFactorThreshold;
	private int numKeys;
	private HashNode<K, V>[] table; // array of header nodes

	/**
	 * Default no-arg constructor.
	 */
	public HashTable() {
		this(5, 0.75);
	}

	/**
	 * Constructor with initial capacity and load factor threshold provided.
	 * @param initialCapacity
	 * @param loadFactorThreshold
	 */
	public HashTable(int initialCapacity, double loadFactorThreshold) {
		this.loadFactorThreshold = loadFactorThreshold;
		this.numKeys = 0;
		// initialize buckets
		table = (HashNode<K, V>[]) new HashNode[initialCapacity];
	}

	// TODO: implement methods according to HashTableADT interface

	/**
	 * Prints the HashTable to standard output.
	 */
	public void printHashTable() {
		System.out.println("HASH TABLE:");
		// print out an array of BucketList<KeyValuePair>
		for (int i = 0; i < table.length; i++) {
			System.out.print(i + ": ");
			HashNode<K, V> currNode = table[i];
			while (currNode != null) {
				System.out.print("(" + currNode.hashNodeToString() + "), ");
				currNode = currNode.next;
			}
			System.out.println();
		}
		System.out.println("numKeys: " + numKeys + "\tload factor: " + 1.0 * this.numKeys/this.table.length);
		System.out.println("--------------------------------------------------");
	}

	/**
	 * Main method.
	 * @param args
	 */
	public static void main(String[] args) {
	}
}
