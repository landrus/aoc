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

	utils.FileLineExecutor(inputFileName, func(line string) error {
		actions := strings.Fields(line)
		amount, err := strconv.Atoi(actions[1])

		if err != nil {
			return err
		}

		switch actions[0] {
		case "forward":
			sub.Forward(&sub.Position, amount)
		case "up":
			sub.Dive(&sub.Position, amount*-1)
		case "down":
			sub.Dive(&sub.Position, amount)
		}

		return nil
	})

	return sub.Location()
}

func main() {
	println(pathing("day2-input.txt", new(commander.Simple)))
	println(pathing("day2-input.txt", new(commander.Complex)))
}
