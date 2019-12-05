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
        try(BufferedReader reader = new BufferedReader(new FileReader(inputFile.toFile()))) {
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
    		startPos += code.instruction.getInstructionCount();
    	} while (continueExec);

    	return opCodeList.get(0);
    }

    private OpCode readOpCode(int startPos, List<Integer> opCodeList) {
    	OpCode code = new OpCode();
    	code.instruction = OpCode.valueOf(opCodeList.get(startPos).intValue());
    	code.parameter1 = opCodeList.get(startPos + 1);
    	code.parameter2 = opCodeList.get(startPos + 2);
    	code.output = opCodeList.get(startPos + 3);
    	
    	return code;
    }
    
    private boolean executeOpCode(OpCode code, List<Integer> opCodeList) {
    	switch(code.instruction) {
    	case ADD:
    		opCodeList.set(code.output, opCodeList.get(code.parameter1) + opCodeList.get(code.parameter2));
    		return true;
    	case MULIPLY:
    		opCodeList.set(code.output, opCodeList.get(code.parameter1) * opCodeList.get(code.parameter2));
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
        	    	return 100 * noun + verb;
        		}
        	}
    	}
    	
    	return -1;
    }

    public static void main(String[] args) throws IOException, URISyntaxException {
        URL inputUrl = Intcode.class.getResource("/day2-input.txt");
        Path inputPath = Paths.get(inputUrl.toURI());
        Intcode calculator = new Intcode(inputPath);
        System.out.printf("Part 1 answer: %s", calculator.process(12, 2));
        System.out.printf("\nPart 2 answer: %s", calculator.findNounVerb());
    }
    
    private static class OpCode {
    	
    	private static enum Type {
    		ADD(4), MULIPLY(4), STOP(1), ERROR(0);
    		
    		private final int instructionCount;
    		
    		private Type(int instructionCode) {
    			this.instructionCount = instructionCode;
    		}
    		
    		public int getInstructionCount() {
				return instructionCount;
			}
    	}
    	
    	public Type instruction;
    	public int parameter1;
    	public int parameter2;
    	public int output;
    	
    	public static Type valueOf(int t) {
    		if (t == 1) {
    			return Type.ADD;
    		} else if (t == 2) {
    			return Type.MULIPLY;
    		} else if (t == 99) {
    			return Type.STOP;
    		} else {
    			return Type.ERROR;
    		}
    	}
    	
    }

}
