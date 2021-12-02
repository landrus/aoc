package main

import (
	"strconv"
	"strings"

	"github.com/landrus/aoc/2021/day2/commander"
	"github.com/landrus/aoc/2021/day2/sub"
	"github.com/landrus/aoc/2021/utils"
)

func pathing(inputFileName string, commander sub.Commander) int {
	sub := sub.Submarine{
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

	return sub.GetPosition()
}

func main() {
	println(pathing("day2-input.txt", &commander.SimpleCommander{}))
	println(pathing("day2-input.txt", &commander.ComplexCommander{}))
}
