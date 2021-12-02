package sub

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

func (sub *Submarine) GetPosition() int {
	return sub.Depth * sub.Horizontal
}
