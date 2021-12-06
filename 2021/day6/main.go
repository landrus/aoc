package main

import (
	"strconv"
	"strings"

	"github.com/landrus/aoc/2021/utils"
)

func calculateFish(inputFileName string, days int) int {
	allStates := [9]int{}

	utils.FileLineExecutor(inputFileName, func(line string) error {
		initStateStrings := strings.Split(line, ",")

		for _, stateString := range initStateStrings {
			initState, _ := strconv.Atoi(stateString)
			allStates[initState]++
		}

		return nil
	})

	for day := 0; day < days; day++ {
		currentState := [9]int{}

		for i := 0; i < 8; i++ {
			currentState[i] = allStates[i+1]
		}

		currentState[6] += allStates[0]
		currentState[8] += allStates[0]

		allStates = currentState
	}

	return sum(allStates)
}

func sum(array [9]int) (sum int) {
	for _, i := range array {
		sum += i
	}

	return sum
}

func main() {
	println(calculateFish("day6-input.txt", 80))
	println(calculateFish("day6-input.txt", 256))
}
