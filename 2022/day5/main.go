package main

import (
	"github.com/landrus/aoc/utils"
)

func fullyContainedPairs(inputFileName string) string {
	utils.FileLineExecutor(inputFileName, func(line string) error {

		return nil
	})

	return ""
}

func tasksOverlap(inputFileName string) int {
	overlaps := 0

	utils.FileLineExecutor(inputFileName, func(line string) error {

		return nil
	})

	return overlaps
}

func main() {
	println(fullyContainedPairs("day5-input.txt"))
	println(tasksOverlap("day5-input.txt"))
}
