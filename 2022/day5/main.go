package main

import (
	"fmt"
	"regexp"
	"strconv"

	"github.com/golang-collections/collections/stack"
	"github.com/landrus/aoc/utils"
)

type moverFunction func(amount int, from *stack.Stack, to *stack.Stack)

func findEmptyLines(lines []string) int {
	for i := 0; i < len(lines); i++ {
		if lines[i] == "" {
			return i
		}
	}

	return 0
}

func parseStacks(stackLines []string) []*stack.Stack {
	stackCountLine := stackLines[len(stackLines)-1]
	stackCount := int(stackCountLine[len(stackCountLine)-2] - '0')
	var stacks []*stack.Stack

	for i := 0; i < stackCount; i++ {
		stacks = append(stacks, stack.New())
	}

	for i := len(stackLines) - 2; i >= 0; i-- {
		pos := 1

		for j := 0; j < stackCount; j++ {
			element := stackLines[i][pos]

			if element != ' ' {
				stacks[j].Push(string(element))
			}

			pos += 4
		}
	}

	return stacks
}

func performMoves(stacks []*stack.Stack, moveLines []string, mover moverFunction) {
	regexp := regexp.MustCompile(`move (\d*) from (\d) to (\d)`)

	for _, move := range moveLines {
		match := regexp.FindStringSubmatch(move)
		amount, _ := strconv.Atoi(match[1])
		stackNoFrom, _ := strconv.Atoi(match[2])
		stackNoTo, _ := strconv.Atoi(match[3])
		stackFrom := stacks[stackNoFrom-1]
		stackTo := stacks[stackNoTo-1]

		mover(amount, stackFrom, stackTo)
	}
}

func topElements(stacks []*stack.Stack) string {
	result := ""

	for _, stack := range stacks {
		result += fmt.Sprintf("%v", stack.Peek())
	}

	return result
}

func moveStacks9000(amount int, from *stack.Stack, to *stack.Stack) {
	for i := 0; i < amount; i++ {
		to.Push(from.Pop())
	}
}

func moveStacks9001(amount int, from *stack.Stack, to *stack.Stack) {
	moveStack := stack.New()

	for i := 0; i < amount; i++ {
		moveStack.Push(from.Pop())
	}

	for i := 0; i < amount; i++ {
		to.Push(moveStack.Pop())
	}
}

func stackMover9000(inputFileName string) string {
	return stackMover(inputFileName, moveStacks9000)
}

func stackMover9001(inputFileName string) string {
	return stackMover(inputFileName, moveStacks9001)
}

func stackMover(inputFileName string, mover moverFunction) string {
	lines := utils.Lines(inputFileName)
	splitPoint := findEmptyLines(lines)
	stackLines := lines[:splitPoint]
	moveLines := lines[splitPoint+1:]
	stacks := parseStacks(stackLines)
	performMoves(stacks, moveLines, mover)

	return topElements(stacks)
}

func main() {
	println(stackMover9000("day5-input.txt"))
	println(stackMover9001("day5-input.txt"))
}
