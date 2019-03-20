package dinersandthinkers;

public class Basket{

    private int numberOfForks;
    private int numberOfBibs;

    Basket(int numberOfForks, int numberOfBibs){
        this.numberOfBibs = numberOfBibs;
        this.numberOfForks = numberOfForks;
    }

    int pickupFork(String profName){
        System.out.println("=== Prof " + profName + "Is waiting for fork.");
            synchronized (this){
                long endTime = System.currentTimeMillis() + 10000; //wait 10 seconds
                while ((System.currentTimeMillis() < endTime)){
                    if(numberOfForks > 0 ){
                        System.out.println("=== Prof " + profName + " received fork.");
                        numberOfForks--;
                        return 1;
                    }//if
                }//while
            }//synchronized
        return -1;
    }

    int pickupBib(String profName){
        System.out.println("=== Prof " + profName + "Is waiting for Bib");
        synchronized (this){
            long endTime = System.currentTimeMillis() + 10000;
            while ((System.currentTimeMillis() < endTime)){
                if(numberOfBibs > 0 ){
                    System.out.println("=== Prof " + profName + " received bib.");
                    numberOfBibs--;
                    return 1;
                }//if
            }//while
        }//synchronized
        return -1;
    }

    void putBackFork(String profName){
        System.out.println("Prof " + profName + "Returned Fork.");
        synchronized (this){
            numberOfForks++;
        }
    }

    void putBackBib(String profName){
        System.out.println("Prof " + profName + "Returned Bib.");
        synchronized (this){
            numberOfForks++;
        }
    }

}
