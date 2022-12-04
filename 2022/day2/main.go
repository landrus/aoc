package main

import (
	"strings"

	"github.com/landrus/aoc/utils"
)

func rockPaperScissorsScore(inputFileName string) int {
	score := 0

	utils.FileLineExecutor(inputFileName, func(line string) error {
		inputs := strings.Split(line, " ")
		score += valuePlayer(inputs[1])
		score += winScore(inputs[0], inputs[1])

		return nil
	})

	return score
}

func valuePlayer(play string) int {
	switch play {
	case "A", "X":
		return 1
	case "B", "Y":
		return 2
	case "C", "Z":
		return 3
	}

	return 0
}

func winScore(play1 string, play2 string) int {
	switch play1 {
	case "A":
		switch play2 {
		case "X":
			return 3
		case "Y":
			return 6
		case "Z":
			return 0
		}
	case "B":
		switch play2 {
		case "X":
			return 0
		case "Y":
			return 3
		case "Z":
			return 6
		}
	case "C":
		switch play2 {
		case "X":
			return 6
		case "Y":
			return 0
		case "Z":
			return 3
		}
	}

	return 0
}

func rockPaperScissorsScore2(inputFileName string) int {
	score := 0

	utils.FileLineExecutor(inputFileName, func(line string) error {
		inputs := strings.Split(line, " ")
		translatedInput := translatePlayerInput(inputs[0], inputs[1])
		score += valuePlayer(translatedInput)
		score += winScore(inputs[0], translatedInput)

		return nil
	})

	return score
}

func translatePlayerInput(play1 string, strategy string) string {
	switch play1 {
	case "A":
		switch strategy {
		case "X":
			return "Z"
		case "Y":
			return "X"
		case "Z":
			return "Y"
		}
	case "B":
		return strategy
	case "C":
		switch strategy {
		case "X":
			return "Y"
		case "Y":
			return "Z"
		case "Z":
			return "X"
		}
	}

	return ""
}

func main() {
	println(rockPaperScissorsScore("day2-input.txt"))
	println(rockPaperScissorsScore2("day2-input.txt"))
}
