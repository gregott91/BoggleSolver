package scramble.board;

import java.util.ArrayList;

/**
 * Holds information on each node in the original board.
 * 
 * @author gott
 */
public class BoardNode {

	private String data;
	private boolean used;
	private ArrayList<BoardNode> connectedNodes = new ArrayList<>();

	/**
	 * Creates a BoardNode for this character.
	 * 
	 * @param data
	 */
	public BoardNode(char data) {
		this.data = Character.toString(data);
	}

	/**
	 * Creates a BoardNode for this String.
	 * 
	 * @param data
	 */
	public BoardNode(String data) {
		this.data = data;
	}

	/**
	 * Connects this node to another.
	 * 
	 * @param node
	 *            Node to connect to.
	 */
	public void connect(BoardNode node) {
		connectedNodes.add(node);
	}

	/**
	 * Gets the String for this index on the board.
	 * 
	 * @return String value for this node.
	 */
	public String getData() {
		return data;
	}

	/**
	 * Gets the nodes connected to this.
	 * 
	 * @return ArrayList of connected nodes.
	 */
	public ArrayList<BoardNode> getConnectedNodes() {
		return connectedNodes;
	}

	/**
	 * Sets whether this BoardNode has been used or not.
	 * 
	 * @param used
	 *            Boolean
	 */
	public void setUsed(boolean used) {
		this.used = used;
	}

	/**
	 * Indicates if this Node has been used already, when searching for
	 * potential new letters.
	 * 
	 * @return Whether it's been used or not
	 */
	public boolean isUsed() {
		return used;
	}

	@Override
	public String toString() {
		String rep = data + ":";
		for (BoardNode n : connectedNodes) {
			rep += n.getData() + ",";
		}
		return rep.substring(0, rep.length() - 1);
	}
}