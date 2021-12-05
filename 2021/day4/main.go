package main

import (
	"strconv"
	"strings"

	"github.com/landrus/aoc/2021/utils"
)

func playBingo(inputFileName string, first bool) int {
	numbers, boards := readBingoInput(inputFileName)
	markedBoardsCount := 0
	markedBoards := []int{}
	boardSum := 0

	for _, number := range numbers {
		for i := range boards {
			mark(number, &boards[i], false)

			if checkForBingo(boards[i]) && !contains(markedBoards, i) {
				markedBoards = append(markedBoards, i)
				markedBoardsCount++
				boardSum = number * sumUnmarked(boards[i])

				if first || markedBoardsCount == len(boards) {
					return boardSum
				} else {
					mark(number, &boards[i], true)
				}
			}
		}
	}

	return boardSum
}

func contains(slice []int, i int) bool {
	for _, v := range slice {
		if v == i {
			return true
		}
	}

	return false
}

func mark(number int, board *[5][5]int, complete bool) {
	for i, row := range board {
		for j, col := range row {
			if complete {
				board[i][j] = -1
			} else if col == number {
				board[i][j] = -1
			}
		}
	}
}

func checkForBingo(board [5][5]int) bool {
	for i := 0; i < 5; i++ {
		sum := board[i][0] + board[i][1] + board[i][2] + board[i][3] + board[i][4]

		if sum == -5 {
			return true
		}
	}

	for i := 0; i < 5; i++ {
		sum := board[0][i] + board[1][i] + board[2][i] + board[3][i] + board[4][i]

		if sum == -5 {
			return true
		}
	}

	return false
}

func sumUnmarked(board [5][5]int) (sum int) {
	for _, row := range board {
		for _, col := range row {
			if col > -1 {
				sum += col
			}
		}
	}

	return sum
}

func readBingoInput(inputFileName string) (numbers []int, boards [][5][5]int) {
	linecounter := 0
	boardLineCounter := 0
	currentBoard := [5][5]int{}

	utils.FileLineExecutor(inputFileName, func(line string) error {
		if linecounter == 0 {
			numbers = sliceAtoi(strings.Split(line, ","))
		} else if len(line) > 0 {
			boardLine := sliceAtoi5(strings.Fields(line))
			currentBoard[boardLineCounter] = boardLine
			boardLineCounter++
		} else {
			if boardLineCounter > 0 {
				boards = append(boards, currentBoard)
			}

			boardLineCounter = 0
		}

		linecounter++

		return nil
	})

	boards = append(boards, currentBoard)

	return numbers, boards
}

func sliceAtoi(strings []string) []int {
	ints := make([]int, 0, len(strings))

	for _, str := range strings {
		i, _ := strconv.Atoi(str)
		ints = append(ints, i)
	}

	return ints
}

func sliceAtoi5(strings []string) (ints [5]int) {
	for i, str := range strings {
		val, _ := strconv.Atoi(str)
		ints[i] = val
	}

	return ints
}

func main() {
	println(playBingo("day4-input.txt", true))
	println(playBingo("day4-input.txt", false))
}
