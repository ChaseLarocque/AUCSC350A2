

package dinersandthinkers;

import java.util.Scanner;

public class DinersAndThinkers {

    public static void main(String[] args) {
        Scanner userInput = new Scanner(System.in);

        System.out.println("Enter Number of Profs:");
        int numberOfProfs = userInput.nextInt();
        String[] profNames = new String[numberOfProfs];

        for(int i = 1; i <= numberOfProfs; i++){
            System.out.println("Enter Name For Prof " + i + ":" );
            profNames[i - 1] = userInput.next();
        }//for

        System.out.println("Enter Number Of Bibs:");
        int numberOfBibs = userInput.nextInt();
        System.out.println("Enter Number Of Forks:");
        int numberOfForks = userInput.nextInt();

        //for loops to create profs
        //for loops to join for profs to finish

    }
}
