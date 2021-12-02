package main

import (
	"strconv"

	"github.com/landrus/aoc/2021/utils"
)

func depthIncreases(inputFileName string) int {
	increments := -1
	previous := -1

	utils.FileLineExecutor(inputFileName, func(line string) {
		depth, _ := strconv.Atoi(line)

		if depth > previous {
			increments++
		}

		previous = depth
	})

	return increments
}

func depthIncreasesWindow(inputFileName string) int {
	increments := -1
	previousSum := 0

	var window [3]int
	pos := 0
	lineCounter := 0

	utils.FileLineExecutor(inputFileName, func(line string) {
		depth, _ := strconv.Atoi(line)

		pos = lineCounter % 3
		window[pos] = depth
		sum := window[0] + window[1] + window[2]

		if lineCounter >= 2 && (sum > previousSum) {
			increments++
		}

		lineCounter++
		previousSum = sum
	})

	return increments
}

func main() {
	println(depthIncreases("day1-input.txt"))
	println(depthIncreasesWindow("day1-input.txt"))
}
