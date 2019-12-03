package ch.landrus.aoc2018.day7;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ExecutionOrderAnalyzer {

	private Set<String> ids = new HashSet<>();
    private List<String> input;

    public ExecutionOrderAnalyzer(Path inputFile) throws IOException {
        try(Stream<String> stream = Files.lines(inputFile)) {
            input = stream.collect(Collectors.toList());
        }
    }
    
    public String calculateExecutionOrder() {
    	Node root = buildTree();
    	
    	StringBuilder executionOrder = new StringBuilder();
    	appendSteps(executionOrder, root);
    	
    	return executionOrder.toString();
    }
    
    public int calculateExecutionTime() {
    	Node root = buildTree();
    	
    	return 0;
    }
    
    private void appendSteps(StringBuilder builder, Node node) {
    	for (Node n : node.children) {
    		int index = builder.indexOf(n.id);
    		
    		if (index > -1) {
    			builder.deleteCharAt(index);
    		}
    		
			builder.append(n.id);
			appendSteps(builder, n);
		}
    }
    
    private Node buildTree() {
    	Node root = new Node("root");

    	for (String line : input) {
    		String before = line.substring(5, 6);
    		String after = line.substring(36, 37);
    		
    		ids.add(before);
    		ids.add(after);

    		Node nodeBefore = root.findAncestor(before);

    		if (nodeBefore == null) {
    			nodeBefore = new Node(before);
    			root.addChild(nodeBefore);
    		}

    		Node nodeAfter = root.findAncestor(after);

    		if (nodeAfter == null) {
    			nodeAfter = new Node(after);
    		}

    		nodeBefore.addChild(nodeAfter);
    	}
    	
    	printTree(root, "");
    	
    	return root;
    }
    
    // ├ └ │
    private void printTree(Node node, String ident) {
    	for (int i = 0; i < node.children.size(); i++) {
    		Node child = node.children.get(i);
    		
    		if (i == node.children.size() - 1) {
    			System.out.println(ident + "└" + child.id);
    			printTree(child, ident + " ");
    		} else {
        		System.out.println(ident + "├" + child.id);
        		printTree(child, ident + "│");
    		}
		}
    }

    private static class Node implements Comparable<Node> {

    	private final String id;
    	private Node parent;
        private final List<Node> children = new ArrayList<>();
        
        public Node(String id) {
        	this.id = id;
        }
        
        public void addChild(Node child) {
        	child.removeFromParent();
        	child.parent = this;
        	children.add(child);
        	Collections.sort(children);
        }
        
        public void removeFromParent() {
        	if (parent != null) {
        		parent.children.remove(this);
        	}
        }
        
        public Node findAncestor(String id) {
        	if (id.equals(this.id)) {
        		return this;
        	} else {
        		for (Node node : children) {
					Node ancestor = node.findAncestor(id);
					
					if (ancestor != null) {
						return ancestor;
					}
				}
        		
        		return null;
        	}
        }
        
        public void findAllAncestor(String id, List<Node> nodes) {
        	if (id.equals(this.id) && children.isEmpty()) {
        		nodes.add(this);
        	}
        	
        	for (Node node : children) {
        		node.findAllAncestor(id, nodes);
        	}
        }
        
        @Override
        public int compareTo(Node o) {
        	return id.compareTo(o.id);
        }
        
        @Override
        public String toString() {
        	return id;
        }

    }

    public static void main(String[] args) throws IOException, URISyntaxException {
        long start = System.currentTimeMillis();
        URL inputUrl = ExecutionOrderAnalyzer.class.getResource("/day7-input.txt");
        Path inputPath = Paths.get(inputUrl.toURI());
        ExecutionOrderAnalyzer analyzer = new ExecutionOrderAnalyzer(inputPath);
        System.out.printf("Execution order (m): %s\n", analyzer.calculateExecutionOrder());
        System.out.println("Execution order (c): OUGLTKDJVBRMIXSACWYPEQNHZF");
        //System.out.printf("Execution time: %s\n", analyzer.calculateExecutionTime());
        System.out.println(System.currentTimeMillis() - start);
    }

}
