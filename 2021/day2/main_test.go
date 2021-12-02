package main

import (
	"testing"

	"github.com/landrus/aoc/2021/utils"
)

func TestDay2Part1(t *testing.T) {
	testInputScanner := utils.ScannerForFile("day2-test.txt")

	exp := 150
	res := pathing(testInputScanner)

	if res != exp {
		t.Fatalf("Expected %d but got %d", exp, res)
	}
}

func TestDay2Part2(t *testing.T) {
	testInputScanner := utils.ScannerForFile("day2-test.txt")

	exp := 900
	res := pathing2(testInputScanner)

	if res != exp {
		t.Fatalf("Expected %d but got %d", exp, res)
	}
}
