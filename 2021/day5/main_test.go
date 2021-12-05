package main

import (
	"testing"
)

func TestDay2Part1(t *testing.T) {
	exp := 5
	res := markLines("day5-test.txt", false)

	if res != exp {
		t.Fatalf("Expected %d but got %d", exp, res)
	}
}

func TestDay2Part2(t *testing.T) {
	exp := 12
	res := markLines("day5-test.txt", true)

	if res != exp {
		t.Fatalf("Expected %d but got %d", exp, res)
	}
}
