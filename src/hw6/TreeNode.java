package hw6;

import java.util.*;

public class TreeNode implements Comparable<TreeNode> {
	private String name;
	private TreeNode parent;
	private TreeSet<TreeNode> children;

	// Constructs a TreeNode with a name
	TreeNode(String name) {
		this.name = name;
		this.children = new TreeSet<>();
	}

	// Creates and returns a TreeNode.
	TreeNode addChild(String childName) {
		TreeNode node = new TreeNode(childName);
		node.parent = this;
		children.add(node);
		return node;
	}

	String getName() {
		return name;
	}

	TreeSet<TreeNode> getChildren() {
		return children;
	}

	TreeNode getParent() {
		return parent;
	}

	// The root has depth zero. All its children have depth 1. All their
	// children have depth 2. Etc.
	int getDepth() {
		int depth = 0;
		TreeNode node = this;
		while (node.parent != null) {
			node = node.parent;
			depth++;
		}
		return depth;
	}

	// Assume this method will never be called on the root node. Any child of
	// the root has
	// rank = Rank.KINGDOM, and so on.
	Rank getRank() {
		Rank[] rank = Rank.values();
		return rank[this.getDepth() - 1];
	}

	// If this node has a child node with the specified name, returns that
	// child node. Otherwise returns null;
	TreeNode getChildWithName(String childName) {
		TreeNode node = this;
		return recurseFind(node, childName);
	}

	// Finds the child node with the specified name
	private TreeNode recurseFind(TreeNode node, String name) {
		if (node.name.equals(name))
			return node;

		for (TreeNode child : node.children) {
			TreeNode target = recurseFind(child, name);
			if (target != null)
				return target;
		}
		return null;
	}

	// Return the hashCode of the name variable.
	public int hashCode() {
		return this.name.hashCode();
	}

	// Just compares this node's name to that node's name.
	public int compareTo(TreeNode that) {
		return this.name.compareTo(that.name);
	}

	// Be compatible and simple.
	// Checks if two nodes are deep equals
	public boolean equals(Object x) {
		TreeNode node = (TreeNode) x;
		return this.compareTo(node) == 0;
	}

	public static void main(String[] args) {
		System.out.println("Will test TreeNode");
		TreeNode n1 = new TreeNode("Darth");
		n1.addChild("Leia");
		TreeNode n2 = n1.getChildWithName("Leia");
		assert n2 != null;
		assert n2.name.equals("Leia");
		assert n2.getParent() == n1;
		assert n1.getDepth() == 0;
		assert n2.getDepth() == 1;
		System.out.println("All ok");
	}
}
