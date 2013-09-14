package scramble.dict;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import trie.AlphabetTrie;

/**
 * Builds a dictionary for use by Scramble.
 * 
 * @author gott
 */
public class DictionaryBuilder {

	private static AlphabetTrie tree;
	private static HashSet<Character> letters;

	/**
	 * Builds the trie representing the valid words in this dictionary.
	 * 
	 * @param path
	 *            Path to the dictionary text file.
	 * @param nodes
	 *            The playing board.
	 * @return AlphabetTrie for the dictionary.
	 * @throws FileNotFoundException
	 *             If the dictionary file doesn't exist.
	 * @throws IOException
	 *             If the dictionary file is unable to be read.
	 */
	public static AlphabetTrie buildTree(String path, String[][] nodes)
			throws FileNotFoundException, IOException {
		letters = new HashSet<>();
		tree = new AlphabetTrie();

		/*
		 * Adds letters from the nodes to a Set. This allows the Dictionary to
		 * automatically discard words that contain letters not in the board,
		 * since it can never reach those.
		 */
		buildLetters(nodes);

		try (BufferedReader br = new BufferedReader(new FileReader(path))) {
			String input;
			while ((input = br.readLine()) != null) {
				// if the input is valid and if the first character is NOT upper
				// case (i.e. is not a proper noun)
				if ((input.length() > 0)
						&& (Character.isLowerCase(input.charAt(0)))) {

					// check if it contains all letters
					if (containsAllLettersInWord(input)) {
						tree.insert(input);
					}
				}
			}
		}
		return tree;
	}

	private static void buildLetters(String[][] nodes) {
		for (int i = 0; i < nodes.length; i++) {
			for (int j = 0; j < nodes[i].length; j++) {
				for (char c : nodes[i][j].toCharArray()) {
					letters.add(c);
				}
			}
		}
	}

	private static boolean containsAllLettersInWord(String word) {
		for (char c : word.toCharArray()) {
			if (!letters.contains(c)) {
				return false;
			}
		}
		return true;
	}
}