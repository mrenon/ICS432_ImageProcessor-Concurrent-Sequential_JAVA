/*
 * Mauricio Renon
 * ICS432
 * 
 * Semaphore.java
 * 
 * a class that simulates what the java semaphore does
 *
 */

public class Semaphore {
	private int initialVal;
	
	/*
	 * Semaphore constructor
	 * 
	 * @param arg the initial value of the semaphore
	 */
	public Semaphore(int arg){
		this.initialVal = arg;
	}
	
	/*
	 * acquire the lock
	 * 
	 * @throws InterruptedException
	 */
	public synchronized void P() throws InterruptedException{ //the one that blocks
		while(this.initialVal == 0){
			wait();
		}
		this.initialVal--;
	}
	
	/*
	 * releases the lock and tells threads there are locks available
	 * 
	 */
	public synchronized void V(){ //the one that signals
		this.initialVal++;
		this.notify();
	}
}
