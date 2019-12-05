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
    
    private Set<Point> intersections = new TreeSet<>((p1, p2) -> p1.manhattenDistance() - p2.manhattenDistance());

    public CircuitBoard(Path inputFile) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile.toFile()))) {
        	line1 = Stream.of(reader.readLine().split(",")).collect(Collectors.toList());
        	line2 = Stream.of(reader.readLine().split(",")).collect(Collectors.toList());
        	
        	assert line1.size() == line2.size();
        }
    }

    public int shortestManhattenDistance() {
    	parseLines(line1, lines1);
    	parseLines(line2, lines2);
    	calculateIntersections();
    	
    	Point shortest = intersections.stream().findFirst().get();
    	
    	return shortest.manhattenDistance();
    }
    
    private Point start;
    
    private void parseLines(List<String> input, List<Line> parsed) {
    	start = new Point(0, 0);
    	
    	input.forEach(section -> {
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
    		start = line.endPoint;
    		parsed.add(line);
    	});
    }
    
    private void calculateIntersections() {
    	lines1.forEach(line1 -> {
    		lines2.forEach(line2 -> {
    			Point intersection = line1.intersect(line2);
    			
    			if (intersection != null && !(intersection.x == 0 && intersection.y == 0)) {
    				intersections.add(intersection);
    			}
    		});
    	});
    }

    public int shortestSteps() {
    	int shortestDistance = -1;
    	
    	for (Point point : intersections) {
    		int distance1 = walkLine(lines1, point);
    		int distance2 = walkLine(lines2, point);
    		
    		if (shortestDistance == -1 || (distance1 + distance2) < shortestDistance) {
    			shortestDistance = distance1 + distance2;
    		}
		}
    	
    	return shortestDistance;
    }
    
    private int walkLine(List<Line> lines, Point intersection) {
    	int distance = 0;
    	
    	for (Line line : lines) {
    		int steps = line.intersect(intersection);
    		
    		if (steps == -1) {
    			distance += line.length;
    		} else {
    			distance += steps;
    			break;
    		}
    	};
    	
    	return distance;
    }
    
    private static class Point {
    	
    	public final int x;
    	public final int y;
    	
    	public Point(int x, int y) {
    		this.x = x;
    		this.y = y;
    	}
    	
    	public int manhattenDistance() {
    		return Math.abs(x) + Math.abs(y);
    	}
    	
    }
    
    private static class Line {
    	
    	private static enum Alignment { HORIZONTAL, VERTICAL; }
    	
    	public final int x1;
    	public final int x2;
    	public final int y1;
    	public final int y2;
    	public final int length;

    	public final boolean forward;
    	public final Point endPoint;
    	public final Alignment alignment;
    	
    	public Line(Point start, Alignment alignment, int amount, boolean forward) {
    		this.forward = forward;
    		this.alignment = alignment;
    		int endX, endY;
			length = amount;
    		
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
    	
    	public int intersect(Point p) {
    		int distance = -1;
    		
    		if (alignment == Alignment.HORIZONTAL) {
    			if (p.y == y1) {
    				if (p.x >= x1 && p.x <= x2) {
    					if (forward) {
    						distance = p.x - x1;
    					} else {
    						distance = x2 - p.x;
    					}
    				}
    			}
    		} else {
    			if (p.x == x1) {
    				if (p.y >= y1 && p.y <= y2) {
    					if (forward) {
    						distance = p.y - y1;
    					} else {
    						distance = y2 - p.y;
    					}
    				}
    			}
    		}
    		
    		return distance;
    	}
    }

    public static void main(String[] args) throws IOException, URISyntaxException {
        URL inputUrl = CircuitBoard.class.getResource("/day3-input.txt");
        Path inputPath = Paths.get(inputUrl.toURI());
        CircuitBoard calculator = new CircuitBoard(inputPath);
        System.out.printf("Part 1 answer: %s", calculator.shortestManhattenDistance());
        System.out.printf("\nPart 2 answer: %s", calculator.shortestSteps());
    }

}
