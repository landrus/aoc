package commander

import "github.com/landrus/aoc/2021/day2/submarine"

type Simple struct{}

func (c *Simple) Forward(pos *submarine.Position, amount int) {
	pos.Horizontal += amount
}

func (c *Simple) Dive(pos *submarine.Position, amount int) {
	pos.Depth += amount
}

type Complex struct {
	aim int
}

func (c *Complex) Forward(pos *submarine.Position, amount int) {
	pos.Horizontal += amount
	pos.Depth += c.aim * amount
}

func (c *Complex) Dive(pos *submarine.Position, amount int) {
	c.aim += amount
}
