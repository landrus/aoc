package main

import (
	"bufio"
	"os"
	"strconv"
)

func depthIncreases(inputScanner *bufio.Scanner) int {
	increments := -1
	previous := -1

	for inputScanner.Scan() {
		line := inputScanner.Text()
		depth, _ := strconv.Atoi(line)

		if depth > previous {
			increments++
		}

		previous = depth
	}

	return increments
}

func depthIncreasesWindow(inputScanner *bufio.Scanner) int {
	increments := -1
	previousSum := 0

	var window [3]int
	pos := 0
	lineCounter := 0

	for inputScanner.Scan() {
		line := inputScanner.Text()
		depth, _ := strconv.Atoi(line)

		pos = lineCounter % 3
		window[pos] = depth
		sum := window[0] + window[1] + window[2]

		if lineCounter >= 2 && (sum > previousSum) {
			increments++
		}

		lineCounter++
		previousSum = sum
	}

	return increments
}

func main() {
	input := scannerForFile("day1-input.txt")
	println(depthIncreases(input))

	input = scannerForFile("day1-input.txt")
	println(depthIncreasesWindow(input))
}

func scannerForFile(fileName string) *bufio.Scanner {
	file, err := os.Open(fileName)

	if err != nil {
		panic(err)
	}

	reader := bufio.NewReader(file)

	return bufio.NewScanner(reader)
}
