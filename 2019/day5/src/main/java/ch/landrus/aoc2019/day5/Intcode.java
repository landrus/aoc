package ch.landrus.aoc2019.day5;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Intcode {

    private List<Integer> input;

    public Intcode(Path inputFile) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile.toFile()))) {
            input = Stream.of(reader.readLine().split(",")).map(Integer::parseInt).collect(Collectors.toList());
        }
    }

    public int process(int noun, int verb) {
        List<Integer> opCodeList = new ArrayList<>(input);

        opCodeList.set(1, noun);
        opCodeList.set(2, verb);
        int startPos = 0;
        boolean continueExec = true;

        do {
            OpCode code = readOpCode(startPos, opCodeList);
            continueExec = executeOpCode(code, opCodeList);
            startPos += code.instruction.instructionCount;
        } while (continueExec);

        return opCodeList.get(0);
    }

    private OpCode readOpCode(int startPos, List<Integer> opCodeList) {
        OpCode code = new OpCode();
        code.instruction = OpCode.Type.valueOf(opCodeList.get(startPos).intValue());

        for (int i = 0; i < code.instruction.instructionCount; i++) {
            code.parameters.add(opCodeList.get(startPos + i + 1));
        }

        return code;
    }

    private boolean executeOpCode(OpCode code, List<Integer> opCodeList) {
        List<Integer> params = code.parameters;

        switch (code.instruction) {
            case ADD:
                opCodeList.set(params.get(2), params.get(0) + params.get(1));
                return true;
            case MULIPLY:
                opCodeList.set(params.get(2), params.get(0) * params.get(1));
                return true;
            case STORE:
                opCodeList.set(params.get(1), params.get(0));
                return true;
            case PRINT:
                System.out.println(opCodeList.get(params.get(0)));
                return true;
            case STOP:
                return false;
            case ERROR:
                throw new RuntimeException("Wrong opcode");
        }

        return false;
    }

    public int findNounVerb() {
        for (int noun = 0; noun < 100; noun++) {
            for (int verb = 0; verb < 100; verb++) {
                int result = process(noun, verb);

                if (result == 19690720) {
                    return (100 * noun) + verb;
                }
            }
        }

        return -1;
    }

    private static class OpCode {

        private static enum Type {

            ADD(4), MULIPLY(4), STORE(3), PRINT(2), STOP(1), ERROR(0);

            private final int instructionCount;

            private Type(int instructionCode) {
                this.instructionCount = instructionCode;
            }

            public static Type valueOf(int t) {
                if (t == 1) {
                    return Type.ADD;
                } else if (t == 2) {
                    return Type.MULIPLY;
                } else if (t == 3) {
                    return Type.STORE;
                } else if (t == 4) {
                    return Type.PRINT;
                } else if (t == 99) {
                    return Type.STOP;
                } else {
                    return Type.ERROR;
                }
            }

        }

        public Type instruction;
        public List<Integer> parameters = new ArrayList<>();

    }

    public static void main(String[] args) throws IOException, URISyntaxException {
        URL inputUrl = Intcode.class.getResource("/day5-input.txt");
        Path inputPath = Paths.get(inputUrl.toURI());
        Intcode intProcessor = new Intcode(inputPath);
        System.out.printf("Part 1 answer: %s", intProcessor.process(12, 2));
        System.out.printf("\nPart 2 answer: %s", intProcessor.findNounVerb());
    }

}
