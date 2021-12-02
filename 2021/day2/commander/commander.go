package commander

import "github.com/landrus/aoc/2021/day2/sub"

type SimpleCommander struct{}

func (c *SimpleCommander) Forward(pos *sub.Position, amount int) {
	pos.Horizontal += amount
}

func (c *SimpleCommander) Dive(pos *sub.Position, amount int) {
	pos.Depth += amount
}

type ComplexCommander struct {
	aim int
}

func (c *ComplexCommander) Forward(pos *sub.Position, amount int) {
	pos.Horizontal += amount
	pos.Depth += c.aim * amount
}

func (c *ComplexCommander) Dive(pos *sub.Position, amount int) {
	c.aim += amount
}
