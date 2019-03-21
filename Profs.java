package dinersandthinkers;

import java.util.Random;

public class Profs extends Thread{

    Basket basketOfBibs;
    Basket basketOfForks;
    final int TOTAL_LINES = 200;
    final int TOTAL_NOODLES = 85;
    String name;
    int numberOfLinesLeft = 200;
    int numberOfNoodlesLeft = 85;

    //constructor
    Profs(String name, Basket basketOfBibs, Basket basketOfForks){
        this.name = name;
        this.basketOfBibs = basketOfBibs;
        this.basketOfForks = basketOfForks;
    }//constructor

    @Override
    public void run(){

        while(numberOfNoodlesLeft != 0 || numberOfLinesLeft != 0) {
            Random rand = new Random();
            int miliSleep = rand.nextInt(100); //0 - 99
            professorSleep(miliSleep);

            int numberOfLines = rand.nextInt(16) + 5; //[5,20]

            if(numberOfLines > numberOfLinesLeft){
                numberOfLines = numberOfLinesLeft;
            }//if

            if (numberOfLines > 0) {
                program(numberOfLines);
            }//if
            int numberOfNoodles = rand.nextInt(8) + 2;

            if(numberOfNoodles > numberOfNoodlesLeft){
                numberOfNoodles = numberOfNoodlesLeft;
            }//if

            if(numberOfNoodles > 0){
                System.out.println("Prof " + name + " Is Hungry!");
                int pickupOrder = rand.nextInt(2); //0 or 1
                int putDownOrder = rand.nextInt(2);
                eat(pickupOrder, putDownOrder, numberOfNoodles);
            }//if
        }//while

        System.out.println("===============> Prof " + name + " is FINISHED!" );
    }//run

    public void professorSleep(int sleepTime){
        try {
            sleep(sleepTime);
        } catch(InterruptedException ex) {
            currentThread().interrupt();
        }//catch
        System.out.println("Prof " + name + " Is Sleeping! ");
    }//professorSleep

    public void program(int numberOfLines){
        try {
            sleep(numberOfLines);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }//catch
        numberOfLinesLeft -= numberOfLines;
        System.out.println("Prof " + name + " is writing " + numberOfLines + " lines!  "
                + "Total Written:  " + (TOTAL_LINES - numberOfLinesLeft));
    }//program

    public void eat(int pickupOrder, int putdownOrder, int numberOfNoodles){
        switch (pickupOrder) {
            case 0:
                while (true) {
                    if (basketOfBibs.pickupResource(name)) {
                        if (basketOfForks.pickupResource(name)) {
                            numberOfNoodlesLeft -= numberOfNoodles;
                            System.out.println("Prof " + name + " is Eating " + numberOfNoodles
                                    + " Noodles!  " + (TOTAL_NOODLES - numberOfNoodlesLeft) + " Eaten!");
                            switch (putdownOrder) {
                                case 0:
                                    basketOfBibs.putBackResource(name);
                                    basketOfForks.putBackResource(name);
                                    break;
                                case 1:
                                    basketOfForks.putBackResource(name);
                                    basketOfBibs.putBackResource(name);
                                    break;
                            }//switch
                            return;
                        } else {
                            basketOfBibs.putBackResource(name);
                        }//else
                    }//if
                }
                //while
            case 1:
                while (true) {
                    if (basketOfForks.pickupResource(name)) {
                        if (basketOfBibs.pickupResource(name)) {
                            numberOfNoodlesLeft -= numberOfNoodles;
                            System.out.println("Prof " + name + " is Eating " + numberOfNoodles
                                    + " Noodles!  " + (TOTAL_NOODLES - numberOfNoodlesLeft) + " Eaten!");
                            switch (putdownOrder) {
                                case 0:
                                    basketOfBibs.putBackResource(name);
                                    basketOfForks.putBackResource(name);
                                    break;
                                case 1:
                                    basketOfForks.putBackResource(name);
                                    basketOfBibs.putBackResource(name);
                                    break;
                            }//switch
                            return;
                        } else {
                            basketOfForks.putBackResource(name);
                        }//else
                    }//if
                }
                //while
        }//switch
    }//eat
}//class