import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 
 * @author Brandon Martin 
 *
 */
public class SystemTimer extends Observable {
    
    /**
     * Flag for starting the timer.  Default is false.
     */ 
    protected static  boolean is_started;
    
    /**
     * The timer object
     */
    private Timer my_timer;
    
    /**
     * Initial timer delay
     */
    private int my_initial_delay = 1000;
    
    /**
     * The timer rate in milliseconds
     */
    private int my_time_rate = 3000;
    
    /**
     * CPU object that is initialized in the constructor.
     */
    private CPU my_cpu;
    
    public SystemTimer(final CPU the_cpu){
    	my_cpu = the_cpu;
    }
    
    
    public static void setStarted(boolean is_start) {
        is_started = true;       
    }
    
    /**
     * Fires the interrupt if flag is set to true.
     */
    public void fireInterrupt() {
        
        //makes sure is_started flag is true
        if(is_started) {
        	my_timer = new Timer();
            //sets the timer to schedule a new task every second
            my_timer.schedule(new TimeTask(), my_initial_delay, my_time_rate);
        }  
    }
    /**
     *     Inner class to create a TimerTask that tells Observer items setChanged()
     *     and notifyObservers()
     *     
     *     @author Brandon M Martin
     *
     */
    class TimeTask extends TimerTask {
        /**
         * Runs the TimerTask that tells Observer items setChanged() and notifyObersvers()
         */
        public void run() {
            setChanged();
            notifyObservers();
            my_cpu.interrupt();
        }
    }

}
