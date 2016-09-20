package hw6;

import java.io.*;
import java.util.*;

public class TreeOfLife {
	private TreeNode root;
	private HashMap<String, TreeNode> nameToNode;

	
	//Constructs a TreeOfLife object
	private TreeOfLife() {
		root = new TreeNode("Life");
		nameToNode = new HashMap<>();
	}

	//
	// Constructs a TreeOfLife instance, adding 1 leaf node for every
	// line in the input file.
	//
	public TreeOfLife(File f) throws IOException {
		root = new TreeNode("Life");
		nameToNode = new HashMap<>();

		FileReader fr = new FileReader(f);
		BufferedReader br = new BufferedReader(fr);
		String reading = br.readLine();
		while (reading != null) {
			add(reading);
			reading = br.readLine();
		}
		br.close();
		fr.close();
	}

	//
	// Format: 7 names, separated by commas.
	// Example:
	// Metazoa,Chordata,Ascidiacea,Enterogona,Didemnidae,Didemnum,vexillum
	// (Note: "Metazoa" means animals.)
	// Adds nodes to the Tree
	private void add(String line) {
		String[] names = line.split(",");
		if (names.length != 7) {
			throw new IllegalArgumentException("string array size: " + names.length + " it needs to be 7.");
		}

		TreeNode node = root;

		for (String s : names) {
			TreeNode childNode = node.getChildWithName(s);
			if (childNode != null)
				node = childNode;
			else {
				node = node.addChild(s);
				nameToNode.put(s, node);
			}
		}
	}


	public String toString() {
		StringBuilder sb = new StringBuilder();
		recurseToString(root, sb, "");
		return sb.toString();
	}

	private void recurseToString(TreeNode node, StringBuilder sb, String indent) {
		sb.append("\n");
		sb.append(indent);
		sb.append(node.getName());
		indent += "  ";
		for (TreeNode child : node.getChildren())
			recurseToString(child, sb, indent);
	}

	Map<String, TreeNode> getNameToNodeMap() {
		return nameToNode;
	}

	//
	// The fast way to find a node, given its name.
	//
	TreeNode getNodeForNameFromMap(String name) {
		return nameToNode.get(name);
	}

	//
	// The slow way to find a node, given its name. Recurses through the tree.
	//
	TreeNode getNodeForNameFromTree(String name) {
		return recurseGetNodeForNameFromTree(name, root);
	}

	private TreeNode recurseGetNodeForNameFromTree(String name, TreeNode node) {
		if (node.getName().equals(name))
			return node;

		for (TreeNode childNode : node.getChildren()) {
			TreeNode foundNode = recurseGetNodeForNameFromTree(name, childNode);
			if (foundNode != null)
				return foundNode;
		}

		return null;
	}


	private static String[] TEST_STRINGS = { "A,B,C,D,E,F,G", "A,B,C,D,E,F,GGG", "A,B,C,WWW,XXX,YYY,ZZZ",
			"A,B,C,DD,EE,FF,GGG", "A,B,C,DDD,EEE,FFFF, GGG", "A,BBB,CCCC,DDDD,EE,FF,ZZ" };

	public static void main(String[] args) {
		System.out.println("START");
		
		File f = new File(args[0]);
		try {
			TreeOfLife tree = new TreeOfLife(f);
			System.out.println(tree);
		} catch (IOException x) {
			System.out.println("Trouble");
			System.out.println(x.getMessage());
			System.out.println(x.getStackTrace());
		}

		System.out.println("DONE");
	}
}
