package main

import (
	"testing"
)

func TestDay1Part1(t *testing.T) {
	exp := 7
	res := depthIncreases("day1-test.txt")

	if res != exp {
		t.Fatalf("Expected %d but got %d", exp, res)
	}
}

func TestDay1Part2(t *testing.T) {
	exp := 5
	res := depthIncreasesWindow("day1-test.txt")

	if res != exp {
		t.Fatalf("Expected %d but got %d", exp, res)
	}
}
