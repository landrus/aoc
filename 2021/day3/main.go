package main

import (
	"strconv"

	"github.com/landrus/aoc/2021/utils"
)

func energyConsumption(inputFileName string, bitLength int) int {
	bits := make([]map[string]int, bitLength)

	for i := 0; i < bitLength; i++ {
		bits[i] = map[string]int{"0": 0, "1": 0}
	}

	gammaBits := ""
	epsilonBits := ""

	utils.FileLineExecutor(inputFileName, func(line string) error {
		for i, c := range line {
			if c == '0' {
				bits[i]["0"]++
			} else {
				bits[i]["1"]++
			}
		}

		return nil
	})

	for _, bitmap := range bits {
		if bitmap["0"] > bitmap["1"] {
			gammaBits += "0"
			epsilonBits += "1"
		} else {
			gammaBits += "1"
			epsilonBits += "0"
		}
	}

	gammaInt, _ := strconv.ParseInt(gammaBits, 2, 0)
	epsilonInt, _ := strconv.ParseInt(epsilonBits, 2, 0)

	return int(gammaInt * epsilonInt)
}

func oxygenAndCO2(inputFileName string, bitLength int) int {
	oxygen := calculate(utils.Lines(inputFileName), 0, bitLength, true)
	co2 := calculate(utils.Lines(inputFileName), 0, bitLength, false)

	oxygenInt, _ := strconv.ParseInt(oxygen, 2, 0)
	co2Int, _ := strconv.ParseInt(co2, 2, 0)

	return int(oxygenInt * co2Int)
}

func calculate(lines []string, bitPosition int, bitLength int, oxygen bool) string {
	zeros := 0
	ones := 0

	for _, line := range lines {
		bit := line[bitPosition]

		if bit == '0' {
			zeros++
		} else {
			ones++
		}
	}

	var filteredLines []string

	if (oxygen && zeros > ones) || (!oxygen && ones >= zeros) {
		filteredLines = filter(lines, bitPosition, '0')
	} else {
		filteredLines = filter(lines, bitPosition, '1')
	}

	if len(filteredLines) > 1 {
		return calculate(filteredLines, bitPosition+1, bitLength, oxygen)
	} else {
		return filteredLines[0]
	}
}

func filter(lines []string, bitPosition int, filterBit byte) (linesFiltered []string) {
	for _, line := range lines {
		if line[bitPosition] == filterBit {
			linesFiltered = append(linesFiltered, line)
		}
	}

	return linesFiltered
}

func main() {
	println(energyConsumption("day3-input.txt", 12))
	println(oxygenAndCO2("day3-input.txt", 12))
}
