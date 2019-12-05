package ch.landrus.aoc2019.day4;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class PasswordValidatorTest {
	
	private final PasswordValidator validator = new PasswordValidator();

	@Test
	void testhasSameAdjacentDigitsNoLargeGroup() {
		assertTrue(validator.hasSameAdjacentDigitsNoLargeGroup("112233"));
		assertFalse(validator.hasSameAdjacentDigitsNoLargeGroup("123444"));
		assertTrue(validator.hasSameAdjacentDigitsNoLargeGroup("111122"));
	}

}
