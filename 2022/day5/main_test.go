package main

import (
	"strings"
	"testing"
)

func TestDay5Part1(t *testing.T) {
	exp := "CMZ"
	res := stackMover9000("day5-test.txt")

	if strings.Compare(exp, res) != 0 {
		t.Fatalf("Expected %s but got %s", exp, res)
	}
}

func TestDay5Part2(t *testing.T) {
	exp := "MCD"
	res := stackMover9001("day5-test.txt")

	if strings.Compare(exp, res) != 0 {
		t.Fatalf("Expected %s but got %s", exp, res)
	}
}
