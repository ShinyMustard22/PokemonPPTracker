package ppTracker;

import java.util.Scanner;

public class Utility {

    /* Asks user for an integer input, asks again if user does not input an integer,
    or if their input isn't between the min and max values */
    public int askUser(Scanner scanner, String prompt, int minValue, int maxValue) {

        int input;

        while(true) {

            System.out.print(prompt);
            if (scanner.hasNextInt()) {

                input = scanner.nextInt();

                if(input < minValue) {

                    if(input <= 0) {
                        System.out.println("Invalid input.");
                    }

                    else {
                        System.out.println("Input too small.");
                    }

                    System.out.println();
                }

                else if(input > maxValue) {

                    System.out.println("Input too large.");
                    System.out.println();
                }

                else {
                    break;
                }
            }

            else {

                System.out.println("Invalid input.");
                scanner.next();
                System.out.println();
            }
        }

        return input;
    }

    // Asks user for a string input, asks again if user inputs an invalid or previously used input
    public String askUser(Scanner scanner, String prompt, String[] invalidInputs, String[] alreadyUsedInputs) {

        String currentInput;

        while(true) {

            boolean invalidInput = false;
            boolean alreadyUsedInput = false;

            System.out.print(prompt);
            currentInput = scanner.nextLine();

            for(String input : invalidInputs) {

                if(currentInput.equalsIgnoreCase(input)) {

                    invalidInput = true;
                    System.out.println("Invalid input.");
                    System.out.println();
                }
            }

            for(String input : alreadyUsedInputs) {

                if(currentInput.equalsIgnoreCase(input)) {

                    alreadyUsedInput = true;
                    System.out.println("Input already used.");
                    System.out.println();
                }
            }

            if(!invalidInput && !alreadyUsedInput) {
                break;
            }
        }

        return currentInput;
    }
}
