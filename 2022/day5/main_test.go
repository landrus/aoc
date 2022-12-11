package main

import (
	"strings"
	"testing"
)

func TestDay5Part1(t *testing.T) {
	exp := "CMZ"
	res := fullyContainedPairs("day5-test.txt")

	if strings.Compare(exp, res) == 0 {
		t.Fatalf("Expected %s but got %s", exp, res)
	}
}

func TestDay5Part2(t *testing.T) {
	exp := 4
	res := tasksOverlap("day5-test.txt")

	if res != exp {
		t.Fatalf("Expected %d but got %d", exp, res)
	}
}
