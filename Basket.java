package dinersandthinkers;

/**
 * Basket.java
 * Justin Ikenouye & Chase Larocque
 * AUCSC 350 - Programming Assignment 3
 *
 * Class represents a basket of one type of resource that will be shared
 * in each of the professor threads.
 *
 * Class contains these functions:
 * - Basket(String)
 *   Constructor. Creates the instance of the basket with the name of the
 *   resource and the number of resources available.
 *
 * - pickupResource(String)
 *   Function will check to see if current prof can pickup the resource, if not
 *   prof will wait() and try again. If the second attempt is unsuccessful,
 *   prof will give up.
 *
 * - putBackResource(String)
 *   Function will put the resource by incrementing the number of
 *   resources available. Function will also notifyAll()
 *   so that another prof can grab the resource.
 *
 */
public class Basket {

    private int numberOfResources;
    private final String items;
    public final Object lock1 = new Object();

    /**
     * Constructor - Basket(String, int)
     * @param items
     * @param numberOfResources
     *
     * Initializes the instance of the basket.
     */
    Basket(String items, int numberOfResources) {
        this.numberOfResources = numberOfResources;
        this.items = items;
    }//constructor


    /**
     * pickupResource(String)
     * @param profName
     * @return
     *
     * Function checks to see if there is any available resources and if so,
     * decrements the number of resources available. If no resources available, wait for
     * at most 2 seconds, or until a notify() call wakes up the function and checks
     * to see if it can grab the resource again. if not, return false. If the function
     * can grab the resource, return true.
     */
    boolean pickupResource(String profName) {
        synchronized (lock1) {
            if (numberOfResources > 0) {
                System.out.println("=== Prof " + profName + " received " + items + ".");
                numberOfResources--;
                return true;
            } else {
                System.out.println("=== Prof " + profName + " is waiting on " + items + ".");
                try {
                    lock1.wait(2000); //wait for maximum of 2 seconds
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }//catch
                if(numberOfResources > 0){
                    System.out.println("=== Prof " + profName + " received " + items + ".");
                    numberOfResources--;
                    return true;
                }else{
                    return false;
                }//else
            }//else
        }//synchronized
    }//pickupFork

    /**
     * putBackResource(String)
     * @param profName
     *
     * Function simulates putting the resource back in the basket by
     * incrementing the number of resources availabe. Function also notifies
     * the other threads that may be waiting for the resource so they can
     * attempt and grab it.
     */
    void putBackResource(String profName){
        System.out.println("=== Prof " + profName + " Returned " + items + ". ");
        synchronized (lock1){
            numberOfResources++;
            lock1.notify();
        }//synchronized
    }//putBackFork
}//Basket.java
