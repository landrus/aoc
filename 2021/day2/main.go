package main

import (
	"bufio"
	"strconv"
	"strings"

	"github.com/landrus/aoc/2021/utils"
)

func pathing(inputScanner *bufio.Scanner) int {
	horizontal := 0
	vertical := 0

	for inputScanner.Scan() {
		line := inputScanner.Text()
		actions := strings.Fields(line)
		amount, _ := strconv.Atoi(actions[1])

		if actions[0] == "forward" {
			horizontal += amount
		} else if actions[0] == "up" {
			vertical -= amount
		} else {
			vertical += amount
		}
	}

	return horizontal * vertical
}

func pathing2(inputScanner *bufio.Scanner) int {
	horizontal := 0
	vertical := 0
	aim := 0

	for inputScanner.Scan() {
		line := inputScanner.Text()
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
	}

	return horizontal * vertical
}

func main() {
	input := utils.ScannerForFile("day2-input.txt")
	println(pathing(input))
	input = utils.ScannerForFile("day2-input.txt")
	println(pathing2(input))
}
