package main

import (
	"testing"
)

func TestDay2Part1(t *testing.T) {
	exp := 198
	res := energyConsumption("day3-test.txt", 5)

	if res != exp {
		t.Fatalf("Expected %d but got %d", exp, res)
	}
}

func TestDay2Part2(t *testing.T) {
	exp := 230
	res := oxygenAndCO2("day3-test.txt", 5)

	if res != exp {
		t.Fatalf("Expected %d but got %d", exp, res)
	}
}
