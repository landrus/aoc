package utils

import (
	"bufio"
	"fmt"
	"os"
)

func FileLineExecutor(fileName string, lineExecutor func(line string) error) error {
	file, err := os.Open(fileName)

	if err != nil {
		return err
	}

	defer file.Close()

	reader := bufio.NewReader(file)
	scanner := bufio.NewScanner(reader)

	for scanner.Scan() {
		line := scanner.Text()
		err = lineExecutor(line)

		if err != nil {
			fmt.Println(err)
		}
	}

	return nil
}
