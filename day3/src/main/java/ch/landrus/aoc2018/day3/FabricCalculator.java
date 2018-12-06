package ch.landrus.aoc2018.day3;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FabricCalculator {

    private List<String> input;
    private int[][] fabric = new int[1000][1000];
    private int overLappings = 0;
    private Set<Integer> perfectClaims = new HashSet<>();

    public FabricCalculator(Path inputFile) throws IOException {
        try(Stream<String> stream = Files.lines(inputFile)) {
            input = stream.collect(Collectors.toList());
        }
    }

    /**
     *      left,top x:y
     * #1 @ 872,519: 18x18
     * #(\d+).@.(\d+),(\d+):.(\d+)x(\d+)
     */
    public void calculateOverlappingsAndClaim() {
        input.stream().forEach(piece -> {
            Square s = new Square(piece);
            perfectClaims.add(s.id);

            for (int x = s.left; x < (s.left + s.x); x++) {
                for (int y = s.top; y < (s.top + s.y); y++) {
                    if (fabric[x][y] == 0) {
                        fabric[x][y] = s.id;
                    } else if (fabric[x][y] > 0) {
                        overLappings++;
                        perfectClaims.remove(s.id);
                        perfectClaims.remove(fabric[x][y]);
                        fabric[x][y] = -1;
                    } else {
                        perfectClaims.remove(s.id);
                    }
                }
            }
        });
    }

    public int getOverlappings() {
        return overLappings;
    }

    public int getPerfectFitId() {
        return perfectClaims.iterator().next();
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
        long start = System.currentTimeMillis();
        URL inputUrl = FabricCalculator.class.getResource("/day3-input.txt");
        Path inputPath = Paths.get(inputUrl.toURI());
        FabricCalculator calculator = new FabricCalculator(inputPath);
        calculator.calculateOverlappingsAndClaim();
        System.out.printf("Number of overlappings: %s\n", calculator.getOverlappings());
        System.out.printf("Perfect fabric ID is: %s\n", calculator.getPerfectFitId());
        System.out.println(System.currentTimeMillis() - start);
    }

}
