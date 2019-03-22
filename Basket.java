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
 *   prof will wait() and notifyall() so another prof may grab it.
 *
 * - putBackResource(String)
 *   Function will put the resource by incrementing the number of
 *   resources available. Function will also notifyAll()
 *   so that another prof can grab the resource.
 *
 */
public class Basket {

    private int numberOfResources;
    private String items;
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
     * decrements the number of resources available. If no resources available, give up
     * lock and notify other threads so the program keeps moving and the other
     * threads have a chance to grab the resource.
     */
    boolean pickupResource(String profName) {
        synchronized (lock1) {
            //if resource available, claim it
            if (numberOfResources > 0) {
                System.out.println("=== Prof " + profName + " received " + items +".");
                numberOfResources--;
                return true;
            } else {
                System.out.println("=== Prof " + profName + " is waiting on " + items + ".");
                try {
                    //if resource is not available, release the lock with wait() and
                    //notify any other thread waiting so that another thread gets the opportunity
                    //to try before this thread (also keeps the program moving).
                    //(wait keeps the program from writing "prof is waiting on resource" a lot)
                    lock1.notifyAll();
                    lock1.wait(10000); //RELEASE LOCK
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }//catch
                return false;
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
            lock1.notifyAll();
        }//synchronized
    }//putBackFork

}//class
