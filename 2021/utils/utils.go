package utils

import (
	"bufio"
	"os"
)

func FileLineExecutor(fileName string, lineExecutor func(line string)) {
	file, err := os.Open(fileName)

	if err != nil {
		panic(err)
	}

	defer file.Close()

	scanner := ScannerForFile(file)
	ScannerIterator(scanner, lineExecutor)
}

func ScannerForFile(file *os.File) *bufio.Scanner {
	reader := bufio.NewReader(file)

	return bufio.NewScanner(reader)
}

func ScannerIterator(scanner *bufio.Scanner, lineExecutor func(line string)) {
	for scanner.Scan() {
		line := scanner.Text()
		lineExecutor(line)
	}
}
