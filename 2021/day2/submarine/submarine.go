package submarine

type Submarine struct {
	Position
	Commander
}

type Position struct {
	Depth      int
	Horizontal int
}

type Commander interface {
	Forward(pos *Position, amount int)
	Dive(pos *Position, amount int)
}

func (sub *Submarine) Location() int {
	return sub.Depth * sub.Horizontal
}
