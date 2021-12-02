package main

import (
	"testing"

	"github.com/landrus/aoc/2021/day2/commander"
)

func TestDay2Part1(t *testing.T) {
	exp := 150
	res := pathing("day2-test.txt", &commander.SimpleCommander{})

	if res != exp {
		t.Fatalf("Expected %d but got %d", exp, res)
	}
}

func TestDay2Part2(t *testing.T) {
	exp := 900
	res := pathing("day2-test.txt", &commander.ComplexCommander{})

	if res != exp {
		t.Fatalf("Expected %d but got %d", exp, res)
	}
}
