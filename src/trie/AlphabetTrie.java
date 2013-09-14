package trie;

import java.util.ArrayList;

/**
 * Holds a dictionary in a Trie format.
 * 
 * @author gott
 */
public class AlphabetTrie {

	private TrieNode head;

	/**
	 * Creates a blank AlphabetTrie.
	 */
	public AlphabetTrie() {
		head = new TrieNode(' ');
	}

	/**
	 * Finds if a word exists in the Trie.
	 * 
	 * @param fragment
	 *            Word to find
	 * @return Null if word exists, otherwise returns the last TrieNode for the
	 *         word.
	 */
	public TrieNode find(String fragment) {
		if (fragment == null)
			return null;
		else
			return findNode(head, fragment, 0);
	}

	/**
	 * Inputs a word into the Trie.
	 * 
	 * @param word
	 *            Word to insert.
	 */
	public void insert(String word) {
		createNode(head, word, 0);
	}

	private TrieNode findNode(TrieNode current, String word, int currentIndex) {
		if (currentIndex < word.length()) {

			ArrayList<TrieNode> children = current.getChildren();
			char currentChar = word.charAt(currentIndex);

			// finds the index of the next letter
			int index = containsNode(children, currentChar);
			if (index == -1) {
				return null;
			} else {
				currentIndex++;
				// increment index, and search for the next character
				return findNode(children.get(index), word, currentIndex);
			}
		}
		return current;
	}

	private void createNode(TrieNode current, String word, int currentIndex) {

		if (currentIndex < word.length()) {

			ArrayList<TrieNode> children = current.getChildren();
			char currentChar = word.charAt(currentIndex);
			int index = containsNode(children, currentChar);

			TrieNode node;
			if (index == -1) {
				// adds the new TrieNode
				node = new TrieNode(currentChar);
				current.addChild(node);
			} else {
				node = children.get(index);
			}

			currentIndex++;
			createNode(node, word, currentIndex);
		} else {
			// since this is the last letter in the word, indicate that it's a
			// word
			current.makeWord();
		}
	}

	private int containsNode(ArrayList<TrieNode> nodeList, char look) {
		for (int i = 0; i < nodeList.size(); i++) {
			if (nodeList.get(i).getData() == look) {
				return i;
			}
		}
		return -1;
	}
}