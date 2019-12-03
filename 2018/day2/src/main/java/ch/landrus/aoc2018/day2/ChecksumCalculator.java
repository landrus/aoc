package ch.landrus.aoc2018.day2;

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

public class ChecksumCalculator {

    private List<String> input;

    public ChecksumCalculator(Path inputFile) throws IOException {
        try(Stream<String> stream = Files.lines(inputFile)) {
            input = stream.collect(Collectors.toList());
        }
    }

    public int calculateChecksum() {
        int boxIdsTwoLetter = 0;
        int boxIdsThreeLetter = 0;

        for (String boxId : input) {
            Map<Character, Integer> characterCount = characterCount(boxId);

            boolean threeLetterFound = false;

            for (Integer count : characterCount.values()) {
                if ((count == 3) && !threeLetterFound) {
                    boxIdsThreeLetter++;
                    threeLetterFound = true;
                }

                if (count == 2) {
                    boxIdsTwoLetter++;
                    break;
                }
            }
        }

        return boxIdsTwoLetter * boxIdsThreeLetter;
    }

    private Map<Character, Integer> characterCount(String boxId) {
        Map<Character, Integer> characterCount = new HashMap<>();

        for (char c : boxId.toCharArray()) {
            if (characterCount.containsKey(c)) {
                characterCount.put(c, characterCount.get(c) + 1);
            } else {
                characterCount.put(c, 1);
            }
        }

        return characterCount.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())).collect(
                Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                    (oldValue, newValue) -> oldValue,
                    LinkedHashMap::new));
    }

    public String commonLetters() {
        for (int i = 0; i < input.size(); i++) {
            String boxId = input.get(i);

            for (int j = i; j < input.size(); j++) {
                String secondBoxId = input.get(j);
                StringBuffer common = new StringBuffer();
                int distance = distance(boxId, secondBoxId, common);

                if (distance == 1) {
                    return common.toString();
                }
            }
        }

        return "";
    }

    private int distance(String boxIdLhs, String boxIdRhs, StringBuffer common) {
        int distance = 0;

        for (int i = 0; i < boxIdLhs.length(); i++) {
            if (boxIdLhs.charAt(i) == boxIdRhs.charAt(i)) {
                common.append(boxIdLhs.charAt(i));
            } else {
                if (distance++ > 1) {
                    break;
                }
            }
        }

        return distance;
    }

    public static void main(String[] args) throws IOException, URISyntaxException {
        URL inputUrl = ChecksumCalculator.class.getResource("/day2-input.txt");
        Path inputPath = Paths.get(inputUrl.toURI());
        ChecksumCalculator calculator = new ChecksumCalculator(inputPath);
        System.out.printf("Checksum is: %s\n", calculator.calculateChecksum());
        System.out.printf("Common letters are: %s", calculator.commonLetters());
    }

}
