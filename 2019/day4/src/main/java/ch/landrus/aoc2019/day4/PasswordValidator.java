package ch.landrus.aoc2019.day4;

import java.io.IOException;
import java.net.URISyntaxException;

public class PasswordValidator {

    public int findValidPasswords1() {
        int validPasswords = 0;

        for (int i = 271973; i <= 785961; i++) {
            String password = String.valueOf(i);

            if (checkPassword1(password)) {
                validPasswords++;
            }
        }

        return validPasswords;
    }

    private boolean checkPassword1(String password) {
        if (password.length() != 6)  return false;
        if (!keepsDigitOrder(password)) return false;
        if (!hasSameAdjacentDigits(password)) return false;

        return true;
    }

    private boolean hasSameAdjacentDigits(String password) {
        for (int i = 0; i < (password.length() - 1); i++) {
            char pos1 = password.charAt(i);
            char pos2 = password.charAt(i + 1);

            if (pos1 == pos2) {
                return true;
            }
        }

        return false;
    }

    public int findValidPasswords2() {
    	int validPasswords = 0;

    	for (int i = 271973; i <= 785961; i++) {
    		String password = String.valueOf(i);

    		if (checkPassword2(password)) {
    			validPasswords++;
    		}
    	}

    	return validPasswords;
    }

    private boolean checkPassword2(String password) {
    	if (password.length() != 6)  return false;
        if (!keepsDigitOrder(password)) return false;
        if (!hasSameAdjacentDigitsNoLargeGroup(password)) return false;

        return true;
    }

    protected boolean hasSameAdjacentDigitsNoLargeGroup(String password) {
        for (int i = 0; i < (password.length() - 1); i++) {
            char pos1 = password.charAt(i);
            char pos2 = password.charAt(i + 1);

            if (pos1 == pos2) {
            	boolean found = true;
            	int j = 2;

            	if (i + j < password.length()) {
                    while (password.charAt(i + j) == pos1) {
                    	found = false;
                    	j++;
                    	
                    	if (i + j == password.length()) {
                    		break;
                    	}
                    }
                    
                    i += j - 1;
            	}
            	
            	if (found) {
            		return true;
            	}
            }
        }

        return false;
    }

    private boolean keepsDigitOrder(String password) {
        for (int i = 0; i < (password.length() - 1); i++) {
            char pos1 = password.charAt(i);
            char pos2 = password.charAt(i + 1);

            if (pos1 > pos2) {
                return false;
            }
        }

        return true;
    }

    public static void main(String[] args) throws IOException, URISyntaxException {
        PasswordValidator calculator = new PasswordValidator();
        System.out.printf("Part 1 answer: %s", calculator.findValidPasswords1());
        System.out.printf("\nPart 2 answer: %s", calculator.findValidPasswords2());
    }

}
