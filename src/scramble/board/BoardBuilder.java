package scramble.board;

import java.util.ArrayList;

/**
 * Builds a board from the input.
 */
public class BoardBuilder {

	private String[][] gameBoard;

	/**
	 * Builds a random square board.
	 * 
	 * @param size
	 *            Length of one side of the board.
	 */
	public BoardBuilder(int size) {
		gameBoard = generateRandomBoard(size);
	}

	/**
	 * Builds a square 2D board from this String.
	 * 
	 * @param board
	 *            String that represents a board.
	 */
	public BoardBuilder(String board) {
		gameBoard = stringToBoard(board);
	}

	/**
	 * Builds a board from a 2D String array.
	 * 
	 * @param board
	 *            The Boggle board
	 */
	public BoardBuilder(String[][] board) {
		if (board != null) {
			gameBoard = board;
		} else {
			System.err.println("Board cannot be null!");
			System.exit(1);
		}
	}

	/**
	 * Returns the board for use by the main class, represented as an ArrayList
	 * of BoardNodes.
	 * 
	 * @return Board as ArrayList<BoardNode>
	 */
	public ArrayList<BoardNode> generate() {
		return build(gameBoard);
	}

	/**
	 * Returns the board as a 2D String array, for use by the DictionaryBuilder.
	 * 
	 * @return 2D String board.
	 */
	public String[][] getBoardArray() {
		return gameBoard;
	}

	/**
	 * Prints the board.
	 */
	public void printBoard() {
		for (int i = 0; i < gameBoard.length; i++) {
			for (String c : gameBoard[i]) {
				System.out.print(c + " ");
			}
			System.out.println();
		}
	}

	private String[][] generateRandomBoard(int size) {
		if (size > 0) {
			/*
			 * I store the possible characters in a String because this way I
			 * can easily "weight" certain characters by adding multiples of
			 * them to the String.
			 */
			String possibles = "abcdefghijklmnopqrstuvwxyz";

			String[][] board = new String[size][size];
			for (int i = 0; i < board.length; i++) {
				for (int j = 0; j < board[i].length; j++) {
					// generate random char, add it to the board as a String
					char random = possibles
							.charAt((int) (Math.random() * possibles.length()));
					board[i][j] = Character.toString(random);
				}
			}
			return board;
		} else {
			System.err.println("Size must be greater than zero!");
			System.exit(1);
		}

		return null;
	}

	private String[][] stringToBoard(String board) {
		if (board != null) {
			String[][] parsed = null;

			int root = (int) Math.sqrt(board.length());
			if ((root * root) != board.length()) {
				System.err.println("String must have an integer square root!");
				System.exit(1);
			} else {
				parsed = new String[root][root];
				for (int i = 0; i < board.length(); i++) {
					int x, y;
					// calculate the x,y index of this character
					x = (int) (i / root);
					y = i % root;
					// add it to the board
					parsed[x][y] = Character.toString(board.charAt(i));
				}
			}
			return parsed;
		} else {
			System.err.println("Board must not be null!");
			System.exit(1);
		}

		return null;
	}

	private ArrayList<BoardNode> build(String[][] board) {
		return buildNodeList(board);
	}

	private ArrayList<BoardNode> buildNodeList(String[][] board) {
		// turns each String into a BoardNode
		BoardNode[][] nodes = convertToNodes(board);

		// Connects each node together
		connectNodes(nodes);

		// adds them to the arraylist
		return addToArrayList(nodes);
	}

	private BoardNode[][] convertToNodes(String[][] board) {
		BoardNode[][] nodes = new BoardNode[board.length][board[0].length];
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				// creates the BoardNode
				nodes[i][j] = new BoardNode(board[i][j]);
			}
		}
		return nodes;
	}

	private void connectNodes(BoardNode[][] board) {
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[0].length; j++) {
				// for each node, connect it to its surrounding nodes
				connectToSurroundingNodes(board, i, j);
			}
		}
	}

	private void connectToSurroundingNodes(BoardNode[][] board, int x, int y) {
		for (int i = x - 1; i <= x + 1; i++) {
			for (int j = y - 1; j <= y + 1; j++) {
				if (!((i == x) && (j == y))) {
					if (isValidIndex(board, i, j)) {
						// if it's not < 0 or >= length, connect the nodes
						board[x][y].connect(board[i][j]);
					}
				}
			}
		}
	}

	private boolean isValidIndex(BoardNode[][] board, int i, int j) {
		if ((i < 0) || (i >= board.length)) {
			return false;
		}
		if ((j < 0) || (j >= board[0].length)) {
			return false;
		}
		return true;
	}

	private ArrayList<BoardNode> addToArrayList(BoardNode[][] board) {
		ArrayList<BoardNode> nodeList = new ArrayList<>(board.length
				* board[0].length);

		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[0].length; j++) {
				nodeList.add(board[i][j]);
			}
		}

		return nodeList;
	}
}