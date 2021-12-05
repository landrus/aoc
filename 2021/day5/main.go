package main

import (
	"strconv"
	"strings"

	"github.com/landrus/aoc/2021/utils"
)

func markLines(inputFileName string, diagonal bool) int {
	dangerPoints := 0
	board := [1000][1000]int{}

	utils.FileLineExecutor(inputFileName, func(line string) error {
		points := strings.Split(line, " -> ")
		p1 := strings.Split(points[0], ",")
		p2 := strings.Split(points[1], ",")
		x1, _ := strconv.Atoi(p1[0])
		y1, _ := strconv.Atoi(p1[1])
		x2, _ := strconv.Atoi(p2[0])
		y2, _ := strconv.Atoi(p2[1])

		if y1 == y2 {
			for i := min(x1, x2); i <= max(x1, x2); i++ {
				board[y1][i]++
			}
		}
		if x1 == x2 {
			for i := min(y1, y2); i <= max(y1, y2); i++ {
				board[i][x1]++
			}
		}

		if diagonal && (x1-x2 == y1-y2 || x1+x2 == y1+y2 || max(x1, x2)-min(x1, x2) == max(y1, y2)-min(y1, y2)) {
			if x1 < x2 {
				if y1 < y2 {
					j := y1
					for i := x1; i <= x2; i++ {
						board[j][i]++
						j++
					}
				} else {
					j := y1
					for i := x1; i <= x2; i++ {
						board[j][i]++
						j--
					}
				}
			} else {
				if y1 < y2 {
					j := y1
					for i := x1; i >= x2; i-- {
						board[j][i]++
						j++
					}
				} else {
					j := y1
					for i := x1; i >= x2; i-- {
						board[j][i]++
						j--
					}
				}
			}
		}

		return nil
	})

	for _, row := range board {
		for _, i := range row {
			if i > 1 {
				dangerPoints++
			}
		}
	}

	return dangerPoints
}

func min(a int, b int) int {
	if a > b {
		return b
	} else {
		return a
	}
}

func max(a int, b int) int {
	if a > b {
		return a
	} else {
		return b
	}
}

func main() {
	println(markLines("day5-input.txt", false))
	println(markLines("day5-input.txt", true))
}
