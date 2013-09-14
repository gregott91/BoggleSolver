/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package scramble;

import trie.AlphabetTrie;
import trie.TrieNode;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

import scramble.board.*;

import scramble.dict.DictionaryBuilder;

/**
 * Working class for the Boggle Solver. Can solve any 4x4 board in 3-10ms, plus
 * around another 100ms to build the board and dictionary. Java heap space
 * allows for a board of maximum size around 1700x1700, which it can solve in
 * around a minute
 * 
 * @author gott
 */
public class Scramble {

	private AlphabetTrie tree = new AlphabetTrie();
	private ArrayList<BoardNode> nodeList = new ArrayList<>();
	private int minWordLength = 3;

	/**
	 * Creates the solver with the board held in the BoardBuilder, and this path
	 * to the dictionary.
	 * 
	 * @param bldr
	 *            BoardBuilder for this board.
	 * @param dictPath
	 *            Path to the dictionary text file.
	 */
	public Scramble(BoardBuilder bldr, String dictPath) {
		// generate the board
		nodeList = bldr.generate();

		// build the tree
		try {
			tree = DictionaryBuilder.buildTree(dictPath, bldr.getBoardArray());
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Unscrambles the board to find all the possible words within.
	 * 
	 * @return Array of all possible results.
	 */
	public String[] unscramble() {
		// gets all the potential words
		Set<String> words = getPotentialWords();

		// adds them into the array
		String[] correctWords = new String[words.size()];
		return words.toArray(correctWords);
	}

	private Set<String> getPotentialWords() {
		// sorts by size, then alphabetically
		TreeSet<String> words = new TreeSet<>(new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				int dif = o2.length() - o1.length();
				return dif == 0 ? o1.compareTo(o2) : dif;
			}
		});

		/*
		 * This is, I think, a good spot to explain why I'm using this BoardNode
		 * system, rather than simply using the 2D array of Strings. Well, it's
		 * a little bit more overhead at the start, but it saves the hassle of
		 * checking every time to see if a chosen index is valid, which
		 * (especially when you're dealing with a large board) is a huge time
		 * saver)
		 */
		for (BoardNode n : nodeList) {
			addWords(n, "", words);
		}
		return words;
	}

	private void addWords(BoardNode node, String word, Set<String> words) {
		// indicate this node has been used
		node.setUsed(true);

		// append the data from that node onto the completed word
		word += node.getData();

		// check if the word exists
		TrieNode foundWord = tree.find(word);
		if (foundWord != null) {
			if ((word.length() >= minWordLength) && foundWord.isWord()) {
				words.add(word);
			}

			for (BoardNode n : node.getConnectedNodes()) {
				if (!n.isUsed()) {
					addWords(n, word, words);
				}
			}
		}
		node.setUsed(false);
	}

	/*
	 * Should really only be used for testing.
	 */
	public static void main(String[] args) {
		long start = System.currentTimeMillis();

		BoardBuilder bldr = new BoardBuilder(5);

		Scramble test = new Scramble(bldr, "dictionary.txt");
		long endDict = System.currentTimeMillis();
		long end, startParse = System.currentTimeMillis();
		String[] words = test.unscramble();
		end = System.currentTimeMillis();

		bldr.printBoard();

		int maxPrint = 15;
		int printAmount = maxPrint >= words.length ? words.length : maxPrint;

		for (int i = 0; i < printAmount; i++) {
			System.out.println((i + 1) + ": " + words[i]);
		}

		System.out.println("Total word count: " + words.length);
		System.out.println("Total time taken: " + (end - start)
				+ "ms, time to build board: " + (endDict - start) + "ms, "
				+ (end - startParse) + "ms to unscramble.");
	}
}