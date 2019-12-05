package ch.landrus.aoc2019.day4;

import java.io.IOException;
import java.net.URISyntaxException;

public class PasswordValidator {

    public int findValidPasswords() {
        int validPasswords = 0;

        for (int i = 271973; i <= 785961; i++) {
            String password = String.valueOf(i);

            if (checkPassword(password)) {
                validPasswords++;
            }
        }

        return validPasswords;
    }

    private boolean checkPassword(String password) {
        if (password.length() != 6) {
            return false;
        }
        if (!hasSameAdjacentDigits(password)) {
            return false;
        }
        if (!keepsDigitOrder(password)) {
            return false;
        }

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

    public int calculateCompleteFuel() {
        return -1;
    }

    public static void main(String[] args) throws IOException, URISyntaxException {
        PasswordValidator calculator = new PasswordValidator();
        System.out.printf("Part 1 answer: %s", calculator.findValidPasswords());
        System.out.printf("\nPart 2 answer: %s", calculator.calculateCompleteFuel());
    }

}
