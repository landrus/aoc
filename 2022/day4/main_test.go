package main

import (
	"testing"
)

func TestDay4Part1(t *testing.T) {
	exp := 2
	res := fullyContainedPairs("day4-test.txt")

	if res != exp {
		t.Fatalf("Expected %d but got %d", exp, res)
	}
}

func TestDay4Part2(t *testing.T) {
	exp := 4
	res := tasksOverlap("day4-test.txt")

	if res != exp {
		t.Fatalf("Expected %d but got %d", exp, res)
	}
}
