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

public class FabricCalculatorOneIteration {

    private List<String> input;
    private Claim[][] fabric = new Claim[1000][1000];
    private int overLappings = 0;
    private Set<Integer> perfectClaims = new HashSet<>();
    private Set<Integer> overLappingClaims = new HashSet<>();

    public FabricCalculatorOneIteration(Path inputFile) throws IOException {
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

            for (int x = s.left; x < (s.left + s.x); x++) {
                for (int y = s.top; y < (s.top + s.y); y++) {
                    if (fabric[x][y] == null) {
                        fabric[x][y] = new Claim();
                    }

                    if (fabric[x][y].hitCounter == 0) {
                        fabric[x][y].incrementHitCount();
                        fabric[x][y].firstClaimId = s.id;
                        perfectClaims.add(s.id);
                    } else if (fabric[x][y].hitCounter == 1) {
                        fabric[x][y].incrementHitCount();
                        overLappings++;
                        overLappingClaims.add(s.id);
                        overLappingClaims.add(fabric[x][y].firstClaimId);
                    } else {
                        overLappingClaims.add(s.id);
                        overLappingClaims.add(fabric[x][y].firstClaimId);
                    }
                }
            }
        });
    }

    public int getOverlappings() {
        return overLappings;
    }

    public int getPerfectFitId() {
        perfectClaims.removeAll(overLappingClaims);
        
        return perfectClaims.iterator().next();
    }

    private static class Claim {

        public int firstClaimId;
        public int hitCounter;

        public void incrementHitCount() {
            hitCounter++;
        }

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
        URL inputUrl = FabricCalculatorOneIteration.class.getResource("/day3-input.txt");
        Path inputPath = Paths.get(inputUrl.toURI());
        FabricCalculatorOneIteration calculator = new FabricCalculatorOneIteration(inputPath);
        calculator.calculateOverlappingsAndClaim();
        System.out.printf("Number of overlappings: %s", calculator.getOverlappings());
        System.out.printf("\nPerfect fabric ID is: %s", calculator.getPerfectFitId());
    }

}
