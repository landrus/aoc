package main

import (
	"strconv"
	"strings"

	"github.com/landrus/aoc/2021/day2/commander"
	"github.com/landrus/aoc/2021/day2/submarine"
	"github.com/landrus/aoc/2021/utils"
)

func pathing(inputFileName string, commander submarine.Commander) int {
	sub := submarine.Submarine{
		Commander: commander,
	}

	utils.FileLineExecutor(inputFileName, func(line string) {
		actions := strings.Fields(line)
		amount, _ := strconv.Atoi(actions[1])

		if actions[0] == "forward" {
			sub.Forward(&sub.Position, amount)
		} else {
			if actions[0] == "up" {
				amount *= -1
			}

			sub.Dive(&sub.Position, amount)
		}
	})

	return sub.Location()
}

func main() {
	println(pathing("day2-input.txt", new(commander.Simple)))
	println(pathing("day2-input.txt", new(commander.Complex)))
}
