package utils

import (
	"bufio"
	"os"
)

func FileLineExecutor(fileName string, lineExecutor func(line string)) error {
	file, err := os.Open(fileName)

	if err != nil {
		return err
	}

	defer file.Close()

	reader := bufio.NewReader(file)
	scanner := bufio.NewScanner(reader)

	for scanner.Scan() {
		line := scanner.Text()
		lineExecutor(line)
	}

	return nil
}
