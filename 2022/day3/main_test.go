package main

import (
	"testing"
)

func TestDay3Part1(t *testing.T) {
	exp := 157
	res := organizeRucksack("day3-test.txt")

	if res != exp {
		t.Fatalf("Expected %d but got %d", exp, res)
	}
}

func TestDay3Part2(t *testing.T) {
	exp := 70
	res := prioritiesRucksackGroup("day3-test.txt")

	if res != exp {
		t.Fatalf("Expected %d but got %d", exp, res)
	}
}
