package ch.landrus.aoc2019.day3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CircuitBoard {

    private List<String> line1;
    private List<String> line2;
    
    private List<Line> lines1 = new ArrayList<>();
    private List<Line> lines2 = new ArrayList<>();
    
    private Set<Point> intersections = new TreeSet<>((p1, p2) -> {
		int absX = Math.abs(p1.x - p2.x);
		int absY = Math.abs(p1.y- p2.y);
		return absX + absY;
	});

    public CircuitBoard(Path inputFile) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile.toFile()))) {
        	line1 = Stream.of(reader.readLine().split(",")).collect(Collectors.toList());
        	line2 = Stream.of(reader.readLine().split(",")).collect(Collectors.toList());
        	
        	assert line1.size() == line2.size();
        }
    }

    public int manhattenDistance() {
    	parseLines(line1, lines1);
    	parseLines(line2, lines2);
    	calculateIntersections();
    	
    	Point shortest = intersections.stream().findFirst().get();
    	
    	return Math.abs(shortest.x) + Math.abs(shortest.y);
    }
    
    private Point start;
    
    private void parseLines(List<String> input, List<Line> parsed) {
    	start = new Point(0, 0);
    	
    	input.stream().forEach(section -> {
    		char action = section.charAt(0);
    		boolean forward;
    		Line.Alignment alignment;
    		
    		if (action == 'L' || action == 'R') {
    			alignment = Line.Alignment.HORIZONTAL;
    			forward = action == 'R';
    		} else {
    			alignment = Line.Alignment.VERTICAL;
    			forward = action == 'U';
    		}
    		
    		int amount = Integer.parseInt(section.substring(1));
    		Line line = new Line(start, alignment, amount, forward);
    		start = line.getEndPoint();
    		parsed.add(line);
    	});
    }
    
    private void calculateIntersections() {
    	lines1.stream().forEach(line1 -> {
    		lines2.stream().forEach(line2 -> {
    			Point intersection = line1.intersect(line1);
    			
    			if (intersection != null && !(intersection.x == 0 && intersection.y == 0)) {
    				intersections.add(intersection);
    			}
    		});
    	});
    }

    public int findNounVerb() {
    	return -1;
    }

    public static void main(String[] args) throws IOException, URISyntaxException {
        URL inputUrl = CircuitBoard.class.getResource("/day3-input.txt");
        Path inputPath = Paths.get(inputUrl.toURI());
        CircuitBoard calculator = new CircuitBoard(inputPath);
        System.out.printf("Part 1 answer: %s", calculator.manhattenDistance());
        System.out.printf("\nPart 2 answer: %s", calculator.findNounVerb());
    }
    
    private static class Point {
    	
    	public final int x;
    	public final int y;
    	
    	public Point(int x, int y) {
    		this.x = x;
    		this.y = y;
    	}
    	
    }
    
    private static class Line {
    	
    	private static enum Alignment { HORIZONTAL, VERTICAL; }
    	
    	public final int x1;
    	public final int x2;
    	public final int y1;
    	public final int y2;
    	public final Alignment alignment;
    	public final Point endPoint;
    	
    	public Line(Point start, Alignment alignment, int amount, boolean forward) {
    		this.alignment = alignment;
    		int endX, endY;
    		
    		if (alignment == Alignment.HORIZONTAL) {
    			if (forward) {
    	    		this.x1 = start.x;
    				this.x2 = start.x + amount;
    				endX = x2;
    			} else {
    				this.x1 = start.x - amount;
    				this.x2 = start.x;
    				endX = x1;
    			}
    			
    			this.y1 = start.y;
    			this.y2 = start.y;
    			endY = start.y;
    		} else {
    			this.x1 = start.x;
    			this.x2 = start.x;
    			endX = start.x;
    			
    			if (forward) {
    	    		this.y1 = start.y;
    				this.y2 = start.y + amount;
    				endY = y2;
    			} else {
    				this.y1 = start.y - amount;
    				this.y2 = start.y;
    				endY = y1;
    			}
    		}
    		
    		endPoint = new Point(endX, endY);
    	}
    	
    	public Point getEndPoint() {
    		return endPoint;
    	}
    	
    	public Point intersect(Line other) {
    		int x;
    		int y;
    		
    		if (alignment == Alignment.HORIZONTAL) {
    			if (other.x1 >= x1 && other.x1 <= x2) {
    				x = other.x1;
    			} else {
    				return null;
    			}
    			
    			if (y1 >= other.y1 && y1 <= other.y2) {
    				y = y1;
    			} else {
    				return null;
    			}
    		} else {
    			if (other.y1 >= y1 && other.y1 <= y2) {
    				y = other.y1;
    			} else {
    				return null;
    			}
    			
    			if (x1 >= other.x1 && x1 <= other.x2) {
    				x = x1;
    			} else {
    				return null;
    			}
    		}

			return new Point(x, y);
    	}
    	
    }

}
