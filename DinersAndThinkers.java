/**
 * Justin Ikenouye & Chase Larocque
 * AUCSC 350 - Programming Assignment 2
 * March 29/2019
 *
 * Program simulates a situation in which multiple professors perform
 * 4 difference steps in sequence (sleep, code, profess hunger, eat and repeat). The
 * "eat" phase will require access to shared resources (with each resource culminating in
 * a separate basket of goods).
 *
 * This class, DinersAndThinkers.java will contain main where the program starts execution.
 * Required are two other classes, Profs.java and Basket.java in order to simulate the
 * professors and the basket(s) of goods.
 */
package dinersandthinkers;

import java.util.Scanner;

/**
 * DinersAndThinkers.java
 *
 * Class handles asking the user for the number of profs, names of said profs
 * and the amount of bibs and forks the profs will have access to during the eat phase.
 * Class will also create the professors as separate threads and create baskets for
 * each item.
 *
 * Class only contains the main(String[]) function, which is used to grab user input,
 * create the baskets for each the bibs and forks and create the threads for each
 * prof.
 */
public class DinersAndThinkers {

    /**
     * main(String[])
     * @param args -> none.
     *
     * Function grabs - from the user - the number of professors, their names,
     * the number of bibs and the number of forks. The function will then create
     * a thread for each of the profs.
     */
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

        Basket basketOfBibs = new Basket("Bib", numberOfBibs);
        Basket basketOfForks = new Basket ("Fork", numberOfForks);


        for(int i = 0; i < numberOfProfs; i++){
            profThreads[i] = new Profs(profNames[i], basketOfBibs, basketOfForks);
            profThreads[i].start();
        }//for

        for(int i = 0; i < profThreads.length; i++) {
            try {
                profThreads[i].join();
            } catch (Exception e) {
            }//catch
        }//for
    }//main(String[])
}//DinersAndThinkers.java
