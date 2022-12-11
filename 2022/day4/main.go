package main

import (
	"strconv"
	"strings"

	"github.com/landrus/aoc/utils"
)

type Range struct {
	start  int
	end    int
	length int
}

func fullyContainedPairs(inputFileName string) int {
	pairs := 0

	utils.FileLineExecutor(inputFileName, func(line string) error {
		tasks := strings.Split(line, ",")

		if fullyContains(getRange(tasks[0]), getRange(tasks[1])) {
			pairs++
		}

		return nil
	})

	return pairs
}

func getRange(tasks string) Range {
	borders := strings.Split(tasks, "-")
	start, _ := strconv.Atoi(borders[0])
	end, _ := strconv.Atoi(borders[1])

	return Range{start, end, end - start + 1}
}

func fullyContains(r1 Range, r2 Range) bool {
	if r1.length > r2.length {
		return r2.start >= r1.start && r2.end <= r1.end
	} else {
		return r1.start >= r2.start && r1.end <= r2.end
	}
}

func tasksOverlap(inputFileName string) int {
	overlaps := 0

	utils.FileLineExecutor(inputFileName, func(line string) error {
		tasks := strings.Split(line, ",")

		if overlap(getRange(tasks[0]), getRange(tasks[1])) {
			overlaps++
		}

		return nil
	})

	return overlaps
}

func overlap(r1 Range, r2 Range) bool {
	return inside(r2.start, r1) || inside(r2.end, r1) || inside(r1.start, r2) || inside(r1.end, r2)
}

func inside(i int, r Range) bool {
	return i >= r.start && i <= r.end
}

func main() {
	println(fullyContainedPairs("day4-input.txt"))
	println(tasksOverlap("day4-input.txt"))
}
