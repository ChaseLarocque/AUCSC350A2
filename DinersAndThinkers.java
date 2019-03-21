

package dinersandthinkers;

import java.util.Scanner;

public class DinersAndThinkers {

    public static void main(String[] args) {
        Scanner userInput = new Scanner(System.in);

        System.out.println("Enter Number of Profs:");
        int numberOfProfs = userInput.nextInt();
        String[] profNames = new String[numberOfProfs];
        Profs[] profThreads = new Profs[numberOfProfs];


        for(int i = 1; i <= numberOfProfs; i++){
            System.out.println("Enter Name For Prof " + i + ":" );
            profNames[i - 1] = userInput.next();
        }//for

        System.out.println("Enter Number Of Bibs:");
        int numberOfBibs = userInput.nextInt();
        System.out.println("Enter Number Of Forks:");
        int numberOfForks = userInput.nextInt();

        Basket basketOfBibs = new Basket("Bibs", numberOfBibs);
        Basket basketOfForks = new Basket ("Forks", numberOfForks);


        for(int i = 0; i < numberOfProfs; i++){
            profThreads[i] = new Profs(profNames[i], basketOfBibs, basketOfForks);
            profThreads[i].start();
        }//for

        for(int i = 0; i < profThreads.length; i++) {
            try {
                profThreads[i].join();
            } catch (Exception e) {
            }
        }

    }
}
