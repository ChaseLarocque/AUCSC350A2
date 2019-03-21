package dinersandthinkers;

public class Basket {

    private int numberOfResources;
    private String items;
    public final Object lock1 = new Object();


    Basket(String items, int numberOfResources) {
        this.numberOfResources = numberOfResources;
        this.items = items;
    }//constructor

    boolean pickupResource(String profName) {
        synchronized (lock1) {
            if (numberOfResources > 0) {
                System.out.println("=== Prof " + profName + " received " + items +".");
                numberOfResources--;
                return true;
            } else {
                System.out.println("=== Prof " + profName + " is waiting on " + items + ".");
                try {
                    lock1.notifyAll();
                    lock1.wait(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }//catch
                return false;
            }//else
        }//synchronized
    }//pickupFork

    void putBackResource(String profName){
        System.out.println("=== Prof " + profName + " Returned " + items + ". ");
        synchronized (lock1){
            numberOfResources++;
            lock1.notifyAll();
        }//synchronized
    }//putBackFork

}//class
