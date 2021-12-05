package main

import (
	"testing"
)

func TestDay2Part1(t *testing.T) {
	exp := 4512
	res := playBingo("day4-test.txt", true)

	if res != exp {
		t.Fatalf("Expected %d but got %d", exp, res)
	}
}

func TestDay2Part2(t *testing.T) {
	exp := 1924
	res := playBingo("day4-test.txt", false)

	if res != exp {
		t.Fatalf("Expected %d but got %d", exp, res)
	}
}
