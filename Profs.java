package dinersandthinkers;

import java.util.Random;

/**
 * Profs.java
 * Justin Ikenouye & Chase Larocque
 * AUCSC 350 - Programming Assignment 3
 *
 * Class extends Thread and mimics a professor. Class will handle
 * the steps that the professor must go through (sleep, code, hunger, eat)
 * and repeat those steps until the professor has coded all the required lines
 * and ate all the required noodles.
 *
 * Class contains these methods:
 * - run()
 *   Function handles the logic of the professors by cycling through the
 *   required stages (sleep, program, hunger, eat)
 *
 * - professorSleep(int)
 *   Function takes the parameter an calls sleep on that parameter.
 *
 * - program(int)
 *   Function takes the paramter and both sleeps for that amount of time and
 *   decrements the remaining number of lines by the parameter.
 *
 * - eat(int)
 *   Function takes handles the logic for the professor to (randomly) pickup both a fork
 *   and a bib, eat a random amount, and (randomly) put down the fork and bib.
 */
public class Profs extends Thread{
    //instance variables
    Basket basketOfBibs;
    Basket basketOfForks;
    String profName;
    final int TOTAL_LINES = 200;
    final int TOTAL_NOODLES = 85;
    int numberOfLinesLeft = TOTAL_LINES;
    int numberOfNoodlesLeft = TOTAL_NOODLES;

    /**
     * Constructor - Profs(String, Basket, Basket)
     * @param profName
     * @param basketOfBibs
     * @param basketOfForks
     *
     * Creates instances for each prof with their name, and the baskets for
     * each bibs and forks.
     */
    Profs(String profName, Basket basketOfBibs, Basket basketOfForks){
        this.profName = profName;
        this.basketOfBibs = basketOfBibs;
        this.basketOfForks = basketOfForks;
    }//constructor

    @Override
    /**
     * run()
     *
     * Function handles the main logic of the program. This Function will cycle
     * each professor between the sleep, code, hunger, eat stages.
     */
    public void run(){

        while(numberOfNoodlesLeft != 0 || numberOfLinesLeft != 0) {
            Random rand = new Random();

            //sleep for a random number ([0,100)) milliseconds
            int miliSleep = rand.nextInt(100); //[0, 100)
            professorSleep(miliSleep);

            //pick random number ([5,20]) of lines for the professor to code
            int numberOfLines = rand.nextInt(16) + 5; //[5,20]
            if(numberOfLines > numberOfLinesLeft){
                numberOfLines = numberOfLinesLeft;
            }//if

            //only code if the professor has lines left to code
            if (numberOfLines > 0) {
                program(numberOfLines);
            }//if

            //pick random number ([2,10]) of noodles for the professor to eat
            int numberOfNoodles = rand.nextInt(8) + 2;
            if(numberOfNoodles > numberOfNoodlesLeft){
                numberOfNoodles = numberOfNoodlesLeft;
            }//if

            //only enter hunger and eat phase if prof has more noodles to eat
            if(numberOfNoodles > 0){
                //enter hunger phase
                System.out.println("Prof " + profName + " Is Hungry!");
                eat(numberOfNoodles);
            }//if
        }//while
        System.out.println("===============> Prof " + profName + " is FINISHED!" );
    }//run

    /**
     * professorSleep(int)
     * @param sleepTime -> a randomized int to represent the time the professor to sleep
     *
     * Function simply calls sleep() for a random amount of time and prints a message
     * that the professor is sleeping.
     */
    public void professorSleep(int sleepTime){
        try {
            sleep(sleepTime);
        } catch(InterruptedException ex) {
            currentThread().interrupt();
        }//catch
        System.out.println("Prof " + profName + " Is Sleeping! ");
    }//professorSleep

    /**
     * program(int)
     * @param numberOfLines -> a randomized int to represent the lines the professor should code
     *
     * Function calls sleep() for the same amount of milliseconds as the number of lines the
     * professor is to code. The function then reduces the total amount of lines left by
     * numberOfLines and prints a message.
     */
    public void program(int numberOfLines){
        try {
            sleep(numberOfLines);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }//catch
        numberOfLinesLeft -= numberOfLines;
        System.out.println("Prof " + profName + " is writing " + numberOfLines + " lines!  "
                + "Total Written:  " + (TOTAL_LINES - numberOfLinesLeft));
    }//program

    /**
     * eat(int)
     * @param numberOfNoodles -> random number of noodles to eat at this phase
     *
     * Function handles the logic to handle the professor trying to
     * grab both a fork and a bib in order to eat noodles.
     * Use of nested switch statements to simulate the fact that the
     * pickup order and the putdown order is to be random.
     */
    public void eat(int numberOfNoodles){
        Random rand = new Random();

        while (true) {
            //random order each time the prof reenters the eat stage
            int pickupOrder = (rand.nextInt(10000) % 2); //0 or 1
            int putdownOrder = (rand.nextInt(10000) % 2);

            switch (pickupOrder) {
                case 0:
                    //if professor is unable to grab the bib, return to the beginning of the
                    //while loop and randomly pick first resource ot grab again.
                   if (basketOfBibs.pickupResource(profName)) {
                        //if professor has bib but fails at grabbing the fork, put back the bib
                        //so another professor may grab it.
                        if (basketOfForks.pickupResource(profName)) {
                            numberOfNoodlesLeft -= numberOfNoodles;
                            System.out.println("Prof " + profName + " is Eating " + numberOfNoodles
                                    + " Noodles!  " + (TOTAL_NOODLES - numberOfNoodlesLeft) + " Eaten!");
                            switch (putdownOrder) {
                                //random put-down order
                                case 0:
                                    basketOfBibs.putBackResource(profName);
                                    basketOfForks.putBackResource(profName);
                                    break;
                                case 1:
                                    basketOfForks.putBackResource(profName);
                                    basketOfBibs.putBackResource(profName);
                                    break;
                            }//switch
                            return; //finish eating stage after eating
                        //else happens if prof can't grab second item after grabbing first
                        } else {
                            basketOfBibs.putBackResource(profName);
                            break; //break out of case
                        }//else
                    }//if
                    break; //break out of case
                case 1:
                    //if professor is unable to grab the fork, return to the beginning of the
                    //while loop and randomly pick the first resource to grab again.
                    if (basketOfForks.pickupResource(profName)) {
                        //if professor has fork but fails at grabbing the but, put back the fork
                        //so another professor may grab it.
                        if (basketOfBibs.pickupResource(profName)) {
                            numberOfNoodlesLeft -= numberOfNoodles;
                            System.out.println("Prof " + profName + " is Eating " + numberOfNoodles
                                    + " Noodles!  " + (TOTAL_NOODLES - numberOfNoodlesLeft) + " Eaten!");
                            switch (putdownOrder) {
                                //random put-down order
                                case 0:
                                    basketOfBibs.putBackResource(profName);
                                    basketOfForks.putBackResource(profName);
                                    break;
                                case 1:
                                    basketOfForks.putBackResource(profName);
                                    basketOfBibs.putBackResource(profName);
                                    break;
                            }//switch
                            return; //finish eating stage after eating
                        } else {
                            basketOfForks.putBackResource(profName);
                            break; //break out of case
                        }//else

                    }//if
                    break; //break out of case
                }//switch

        }//while(true)

    }//eat(int)
}//Profs.java