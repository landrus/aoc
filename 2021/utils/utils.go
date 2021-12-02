package utils

import (
	"bufio"
	"os"
)

func ScannerForFile(fileName string) *bufio.Scanner {
	file, err := os.Open(fileName)

	if err != nil {
		panic(err)
	}

	reader := bufio.NewReader(file)

	return bufio.NewScanner(reader)
}
