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
		score += prioritiesRucksack(allItems[:rucksackLength/2], allItems[rucksackLength/2:rucksackLength])

		return nil
	})

	return score
}

func prioritiesRucksack(items1 []string, items2 []string) int {
	set1 := hashset.New(convertStringSlice(items1)...)
	set2 := hashset.New(convertStringSlice(items2)...)
	common := set1.Intersection(set2)
	s := fmt.Sprint(common.Values()[0])
	return convertIntValue(s)
}

func convertStringSlice(s []string) []interface{} {
	b := make([]interface{}, len(s))
	for i := range s {
		b[i] = s[i]
	}
	return b
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

func prioritiesRucksackGroup(inputFileName string) int {
	score := 0
	lines := utils.Lines(inputFileName)

	for i := 0; i < len(lines); i += 3 {
		set1 := hashset.New(convertStringSlice(strings.Split(lines[i], ""))...)
		set2 := hashset.New(convertStringSlice(strings.Split(lines[i+1], ""))...)
		set3 := hashset.New(convertStringSlice(strings.Split(lines[i+2], ""))...)

		common := set1.Intersection(set2).Intersection(set3)
		s := fmt.Sprint(common.Values()[0])
		score += convertIntValue(s)
	}

	return score
}

func main() {
	println(organizeRucksack("day3-input.txt"))
	println(prioritiesRucksackGroup("day3-input.txt"))
}
