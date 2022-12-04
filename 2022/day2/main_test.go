package main

import (
	"testing"
)

func TestDay2Part1(t *testing.T) {
	exp := 15
	res := rockPaperScissorsScore("day2-test.txt")

	if res != exp {
		t.Fatalf("Expected %d but got %d", exp, res)
	}
}

func TestDay2Part2(t *testing.T) {
	exp := 12
	res := rockPaperScissorsScore2("day2-test.txt")

	if res != exp {
		t.Fatalf("Expected %d but got %d", exp, res)
	}
}
