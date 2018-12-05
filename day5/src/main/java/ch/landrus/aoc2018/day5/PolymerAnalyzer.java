package ch.landrus.aoc2018.day5;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PolymerAnalyzer {
    
    private String input;
    
    public PolymerAnalyzer(Path inputFile) throws IOException {
    	try (Stream<String> stream = Files.lines(inputFile)) {
        	input = stream.collect(Collectors.toList()).get(0);
        }
    }
    
    public String reducePolymer() {
    	return reducePolymer(input);
    }
    
    private String reducePolymer(String polymer) {
    	List<String> pairs = createPairs();
    	
    	while (true) {
    		boolean hasPairs = false;
    		
    		for (String pair : pairs) {
    			polymer = polymer.replaceAll(pair, "");
			}
    		
    		for (String pair : pairs) {
				if (polymer.contains(pair)) {
					hasPairs = true;
				}
			}
    		
    		if (!hasPairs) {
    			break;
    		}
    	}
    	
    	return polymer;
    }
    
    public String findOptimumReduction(String polymer) {
    	String best = null;
    	
    	for (int i = 0; i < 26; i++) {
    		char c =  (char)(i + 'a');
    		char cu = Character.toUpperCase(c);
    		String current = polymer.replaceAll("[" + c + cu + "]", "");
    		current = reducePolymer(current);
    		
    		if (best == null) {
    			best = current;
    		} else if (current.length() < best.length()) {
    			best = current;
    		}
		}
    	
    	return best;
    }
    
    private List<String> createPairs() {
    	List<String> pairs = new ArrayList<>();
    	StringBuilder builder = new StringBuilder();
    	
    	for (int i = 0; i < 26; i++) {
    		char c =  (char)(i + 'a');
    		char cu = Character.toUpperCase(c);
    		pairs.add(builder.append(c).append(cu).toString());
    		pairs.add(builder.reverse().toString());
    		builder.setLength(0);
    	}
    	
    	return pairs;
    }
    
    public static void main(String[] args) throws IOException, URISyntaxException {
    	long start = System.currentTimeMillis();
    	URL inputUrl = PolymerAnalyzer.class.getResource("/day5-input.txt");
    	Path inputPath = Paths.get(inputUrl.toURI());
        PolymerAnalyzer analyzer = new PolymerAnalyzer(inputPath);
        String polymer = analyzer.reducePolymer();
        System.out.printf("Polymer length: %s\n", polymer.length());
        System.out.printf("Strategy 2: %s\n", analyzer.findOptimumReduction(polymer).length());
        System.out.println(System.currentTimeMillis() - start);
    }
    
}
