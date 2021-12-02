package main

import (
	"strconv"
	"strings"

	"github.com/landrus/aoc/2021/utils"
)

func pathing(inputFileName string) int {
	horizontal := 0
	vertical := 0

	utils.FileLineExecutor(inputFileName, func(line string) {
		actions := strings.Fields(line)
		amount, _ := strconv.Atoi(actions[1])

		if actions[0] == "forward" {
			horizontal += amount
		} else if actions[0] == "up" {
			vertical -= amount
		} else {
			vertical += amount
		}
	})

	return horizontal * vertical
}

func pathing2(inputFileName string) int {
	horizontal := 0
	vertical := 0
	aim := 0

	utils.FileLineExecutor(inputFileName, func(line string) {
		actions := strings.Fields(line)
		amount, _ := strconv.Atoi(actions[1])

		if actions[0] == "forward" {
			horizontal += amount
			vertical += aim * amount
		} else if actions[0] == "up" {
			aim -= amount
		} else {
			aim += amount
		}
	})

	return horizontal * vertical
}

func main() {
	println(pathing("day2-input.txt"))
	println(pathing2("day2-input.txt"))
}
