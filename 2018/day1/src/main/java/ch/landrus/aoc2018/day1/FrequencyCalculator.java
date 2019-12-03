package ch.landrus.aoc2018.day1;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FrequencyCalculator {

    private List<Integer> input;

    public FrequencyCalculator(Path inputFile) throws IOException {
        try(Stream<String> stream = Files.lines(inputFile)) {
            input = stream.mapToInt(f -> Integer.parseInt(f)).boxed().collect(Collectors.toList());
        }
    }

    public int calculateFrequencePart1() {
        return input.stream().mapToInt(i -> i).sum();
    }

    public int calculateFrequencePart2() {
        int frequency = 0;
        Set<Integer> visitedFrequencies = new HashSet<>();

        while (true) {
            for (Integer change : input) {
                frequency += change;

                if (!visitedFrequencies.contains(frequency)) {
                    visitedFrequencies.add(frequency);
                } else {
                    return frequency;
                }
            }
        }
    }

    public static void main(String[] args) throws IOException, URISyntaxException {
        URL inputUrl = FrequencyCalculator.class.getResource("/day1-input.txt");
        Path inputPath = Paths.get(inputUrl.toURI());
        FrequencyCalculator calculator = new FrequencyCalculator(inputPath);
        System.out.printf("Part 1 answer: %s", calculator.calculateFrequencePart1());
        System.out.printf("\nPart 2 answer: %s", calculator.calculateFrequencePart2());
    }

}
