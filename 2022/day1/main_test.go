package main

import (
	"testing"
)

func TestDay1Part1(t *testing.T) {
	exp := 24000
	res := highestCalories("day1-test.txt")

	if res != exp {
		t.Fatalf("Expected %d but got %d", exp, res)
	}
}

func TestDay1Part2(t *testing.T) {
	exp := 45000
	res := threeTopCalories("day1-test.txt")

	if res != exp {
		t.Fatalf("Expected %d but got %d", exp, res)
	}
}
