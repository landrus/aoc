package main

import (
	"sort"
	"strconv"

	"github.com/landrus/aoc/utils"
)

func highestCalories(inputFileName string) int {
	highest := 0
	current := 0

	utils.FileLineExecutor(inputFileName, func(line string) error {
		calories, _ := strconv.Atoi(line)

		if calories > 0 {
			current += calories
		} else {
			if current > highest {
				highest = current
			}

			current = 0
		}

		return nil
	})

	return highest
}

func threeTopCalories(inputFileName string) int {
	current := 0
	var allCalories []int

	utils.FileLineExecutor(inputFileName, func(line string) error {
		calories, _ := strconv.Atoi(line)

		if calories > 0 {
			current += calories
		} else {
			allCalories = append(allCalories, current)
			current = 0
		}

		return nil
	})

	if current > 0 {
		allCalories = append(allCalories, current)
	}

	sort.Sort(sort.Reverse(sort.IntSlice(allCalories)))

	return allCalories[0] + allCalories[1] + allCalories[2]
}

func main() {
	println(highestCalories("day1-input.txt"))
	println(threeTopCalories("day1-input.txt"))
}
