package main

import (
	"testing"
)

func TestDay2Part1(t *testing.T) {
	exp := 5934
	res := calculateFish("day6-test.txt", 80)

	if res != exp {
		t.Fatalf("Expected %d but got %d", exp, res)
	}
}

func TestDay2Part2(t *testing.T) {
	exp := 26984457539
	res := calculateFish("day6-test.txt", 256)

	if res != exp {
		t.Fatalf("Expected %d but got %d", exp, res)
	}
}
