package ch.landrus.aoc2018.day6;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LocationAnalyzer {

    private int xMax;
    private int yMax;
    private List<Point> input;

    public LocationAnalyzer(Path inputFile) throws IOException {
        try(Stream<String> stream = Files.lines(inputFile)) {
            input = stream.map(s -> new Point(s)).collect(Collectors.toList());
        }

        this.xMax = input.stream().max((p1, p2) -> p1.x - p2.x).get().x;
        this.yMax = input.stream().max((p1, p2) -> p1.y - p2.y).get().y;
    }

    public int biggestArea() {
        int[][] field = new int[xMax + 1][yMax + 1];
        Map<Integer, Integer> regions = new HashMap<>();

        for (int x = 0; x <= xMax; x++) {
            for (int y = 0; y <= yMax; y++) {
                int id = 0;
                int shortestDistance = 1000;

                for (Point p : input) {
                    int distance = distance(x, y, p);

                    if (distance < shortestDistance) {
                        id = p.id;
                        shortestDistance = distance;
                    } else if (distance == shortestDistance) {
                        id = -1;
                    }
                }

                field[x][y] = id;
                Integer count = regions.get(id);

                if (count == null) {
                    count = new Integer(1);
                } else {
                    count = count + 1;
                }

                regions.put(id, count);
            }
        }

        for (int x = 0; x <= xMax; x++) {
            regions.remove(field[x][0]);
            regions.remove(field[x][yMax]);
        }

        for (int y = 0; y <= yMax; y++) {
            regions.remove(field[0][y]);
            regions.remove(field[xMax][y]);
        }

        regions = regions.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())).collect(
                Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                    (oldValue, newValue) -> oldValue,
                    LinkedHashMap::new));

        return regions.values().iterator().next();
    }

    public int regionCluster() {
        int[][] field = new int[xMax + 1][yMax + 1];

        for (int x = 0; x <= xMax; x++) {
            for (int y = 0; y <= yMax; y++) {
                int combinedDistance = 0;

                for (Point p : input) {
                    combinedDistance += distance(x, y, p);
                }

                field[x][y] = combinedDistance;
            }
        }

        int region = 0;

        for (int x = 0; x <= xMax; x++) {
            for (int y = 0; y <= yMax; y++) {
                if (field[x][y] < 10000) {
                    region++;
                }
            }
        }

        return region;
    }

    private int distance(int x, int y, Point p) {
        return Math.abs(x - p.x) + Math.abs(y - p.y);
    }

    private static class Point {

        private static char id_counter = 1;

        private final int id;
        private final int x;
        private final int y;

        public Point(String line) {
            this.id = id_counter++;
            String[] split = line.split(", ");
            this.x = Integer.parseInt(split[0]);
            this.y = Integer.parseInt(split[1]);
        }

    }

    public static void main(String[] args) throws IOException, URISyntaxException {
        long start = System.currentTimeMillis();
        URL inputUrl = LocationAnalyzer.class.getResource("/day6-input.txt");
        Path inputPath = Paths.get(inputUrl.toURI());
        LocationAnalyzer analyzer = new LocationAnalyzer(inputPath);
        System.out.printf("Biggest area: %s\n", analyzer.biggestArea());
        System.out.printf("Size of region cluster is: %s\n", analyzer.regionCluster());
        System.out.println(System.currentTimeMillis() - start);
    }

}
