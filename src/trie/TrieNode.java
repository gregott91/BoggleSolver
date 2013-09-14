package trie;

import java.util.ArrayList;

/**
 * Holds data for one character in this level of the trie.
 * 
 * @author gott
 */
public class TrieNode {

	private ArrayList<TrieNode> children = new ArrayList<>();
	private boolean isWord = false;
	private char data;

	public TrieNode(char data) {
		this.data = data;
	}

	public void addChild(TrieNode node) {
		children.add(node);
	}

	public ArrayList<TrieNode> getChildren() {
		return children;
	}

	public char getData() {
		return data;
	}

	public void makeWord() {
		isWord = true;
	}

	public boolean isWord() {
		return isWord;
	}
}