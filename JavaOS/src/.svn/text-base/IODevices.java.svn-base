import java.util.Observable;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * This class simulates an IO thread to
 * randomly interrupt cpu every couple seconds.
 * @author Cody Shafer, Mike Westbrook, Brandon Martin, and Jesse Kitterman
 *
 */
public class IODevices extends Observable {

    
    /**
     * The timer object
     */
    private Timer my_timer;
    
    /**
     * Initial timer delay
     */
    private int my_initial_delay = 2000;
    
    /**
     * The timer rate in milliseconds
     */
    private int my_time_rate = 8000;
    
    private Random rand = new Random();
    
    /**
     * CPU object that is initialized in the constructor.
     */
    private CPU my_cpu;
    
    public IODevices(final CPU the_cpu){
    	my_cpu = the_cpu;
    }
    
    /**
     * Fires the interrupt if flag is set to true.
     */
    public void fireInterrupt() {

    	my_timer = new Timer();
    	//sets the timer to schedule a new task every second
    	my_timer.schedule(new TimeTask(), my_initial_delay, my_time_rate); 
    }
    /**
     *     Inner class to create a TimerTask that tells Observer items setChanged()
     *     and notifyObservers()
     *     
     *     @author Brandon Martin
     *
     */
    class TimeTask extends TimerTask {
        /**
         * Runs the TimerTask that tells Observer items setChanged() and notifyObersvers()
         */
        public void run() {
        	//System.out.println("Timer fired");
        	
            setChanged();
            notifyObservers(rand.nextInt(10) + 1);
            my_cpu.interrupt();
        }
    }

}
