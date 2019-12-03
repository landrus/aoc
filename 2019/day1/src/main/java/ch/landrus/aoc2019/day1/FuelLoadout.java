package ch.landrus.aoc2019.day1;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FuelLoadout {

    private List<Integer> input;

    public FuelLoadout(Path inputFile) throws IOException {
        try(Stream<String> stream = Files.lines(inputFile)) {
            input = stream.mapToInt(f -> Integer.parseInt(f)).boxed().collect(Collectors.toList());
        }
    }

    public int calculateModulesFuel() {
        return input.stream().mapToInt(mass -> moduleFuel(mass)).sum();
    }
    
    private int moduleFuel(int mass) {
    	return Math.floorDiv(mass, 3) - 2;
    }

    public int calculateCompleteFuel() {
    	return input.stream().mapToInt(mass -> completeFuel(moduleFuel(mass))).sum();
    }

    private int completeFuel(int mass) {
    	int lastAdded = mass;
    	int totalFuel = lastAdded;

    	do {
    		lastAdded = moduleFuel(lastAdded);
    		if (lastAdded > 0) {
    			totalFuel += lastAdded;
    		}
    	} while (lastAdded > 0);

    	return totalFuel;
    }

    public static void main(String[] args) throws IOException, URISyntaxException {
        URL inputUrl = FuelLoadout.class.getResource("/day1-input.txt");
        Path inputPath = Paths.get(inputUrl.toURI());
        FuelLoadout calculator = new FuelLoadout(inputPath);
        System.out.printf("Part 1 answer: %s", calculator.calculateModulesFuel());
        System.out.printf("\nPart 2 answer: %s", calculator.calculateCompleteFuel());
    }

}
