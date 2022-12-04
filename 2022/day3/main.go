package main

import (
	"fmt"
	"strings"

	"github.com/emirpasic/gods/sets/hashset"
	"github.com/landrus/aoc/utils"
)

func organizeRucksack(inputFileName string) int {
	score := 0

	utils.FileLineExecutor(inputFileName, func(line string) error {
		rucksackLength := len(line)
		allItems := strings.Split(line, "")
		set1 := hashset.New(convertStringSlice(allItems[:rucksackLength/2])...)
		set2 := hashset.New(convertStringSlice(allItems[rucksackLength/2 : rucksackLength])...)
		common := set1.Intersection(set2)
		s := fmt.Sprint(common.Values()[0])
		score += convertIntValue(s)

		return nil
	})

	return score
}

func convertIntValue(s string) int {
	char := []rune(s)[0]
	i := int(char)

	if i >= 97 {
		return i - 96
	} else {
		return i - 64 + 26
	}

}

func convertStringSlice(s []string) []interface{} {
	b := make([]interface{}, len(s))
	for i := range s {
		b[i] = s[i]
	}
	return b
}

func rockPaperScissorsScore2(inputFileName string) int {
	score := 0

	utils.FileLineExecutor(inputFileName, func(line string) error {

		return nil
	})

	return score
}

func main() {
	println(organizeRucksack("day3-input.txt"))
	println(rockPaperScissorsScore2("day3-input.txt"))
}
