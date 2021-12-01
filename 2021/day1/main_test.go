package main

import (
	"testing"
)

func TestDay1Part1(t *testing.T) {
	testInputScanner := scannerForFile("day1-test.txt")

	exp := 7
	res := depthIncreases(testInputScanner)

	if res != exp {
		t.Fatalf("Expected %d but got %d", exp, res)
	}
}

func TestDay1Part2(t *testing.T) {
	testInputScanner := scannerForFile("day1-test.txt")

	exp := 5
	res := depthIncreasesWindow(testInputScanner)

	if res != exp {
		t.Fatalf("Expected %d but got %d", exp, res)
	}
}
