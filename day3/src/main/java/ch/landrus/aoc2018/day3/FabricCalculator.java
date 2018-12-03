package ch.landrus.aoc2018.day3;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FabricCalculator {
    
    private List<String> input;
    private int perfectFitId = 0;
    private int overlappings = 0;
    private int[][] fabric = new int[1000][1000];
    
    public FabricCalculator(Path inputFile) throws IOException {
    	try (Stream<String> stream = Files.lines(inputFile)) {
        	input = stream.collect(Collectors.toList());
        }
    }
    
    /**
     *      left,top x:y
     * #1 @ 872,519: 18x18
     * #(\d+).@.(\d+),(\d+):.(\d+)x(\d+)
     */
    public int calculateOverlappings() {
    	input.stream().forEach(piece -> {
    		Square s = new Square(piece);
    		
    		for (int x = s.left; x < (s.left + s.x); x++) {
				for (int y = s.top; y < (s.top + s.y); y++) {
					if (fabric[x][y] == 0) {
						fabric[x][y] = 1;
					} else if (fabric[x][y] == 1) {
						fabric[x][y] = 2;
						overlappings++;
					}
				}
			}
    	});
    	
    	return overlappings;
    }
    
    public int perfectFabricId() {
    	input.stream().forEach(piece -> {
    		Square s = new Square(piece);
    		boolean perfectFit = true;
    		
    		for (int x = s.left; x < (s.left + s.x); x++) {
				for (int y = s.top; y < (s.top + s.y); y++) {
					if (fabric[x][y] == 1) {
						fabric[x][y] = 2;
					} else if (fabric[x][y] > 1) {
						perfectFit = false;
					}
				}
			}
    		
    		if (perfectFit ) {
    			perfectFitId = s.id;
    		}
    	});
    	
    	return perfectFitId;
    }
    
    private static class Square {
    	
    	private static final Pattern DEF_PATTERN = Pattern.compile("#(\\d+).@.(\\d+),(\\d+):.(\\d+)x(\\d+)");
    	
    	public final int id;
    	public final int x;
    	public final int y;
    	public final int left;
    	public final int top;
    	
    	public Square(String definition) {
    		Matcher matcher = DEF_PATTERN.matcher(definition);
    		matcher.matches();
    		
    		id = Integer.parseInt(matcher.group(1));
    		left = Integer.parseInt(matcher.group(2));
    		top = Integer.parseInt(matcher.group(3));
    		x = Integer.parseInt(matcher.group(4));
    		y = Integer.parseInt(matcher.group(5));
    	}
    }
    
    public static void main(String[] args) throws IOException, URISyntaxException {
    	URL inputUrl = FabricCalculator.class.getResource("/day3-input.txt");
    	Path inputPath = Paths.get(inputUrl.toURI());
        FabricCalculator calculator = new FabricCalculator(inputPath);
        System.out.printf("Number of overlappings: %s", calculator.calculateOverlappings());
        System.out.printf("\nPerfect fabric ID is: %s", calculator.perfectFabricId());
    }
    
}
