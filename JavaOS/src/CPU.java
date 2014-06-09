import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;

/**
 * Tells which process to run and when to 
 * read and write to memory and
 * and when to lock and unlock memory as well.
 * @author Cody Shafer, Mike Westbrook, Brandon Martin, and Jesse Kitterman
 * 
 */ 
public class CPU extends Thread implements Observer {

	/**
	 * Holds a reference to the current thread to be ran.
	 */
	private Process current_process; 

	/**
	 * A reference to the scheduler.
	 */
	private static Scheduler the_scheduler;// = Scheduler.getInstance();
	
	LinkedList<Integer> register = new LinkedList<Integer>();

	/**
	 * Used to start up the CPU and create the scheduler. Could be passed a reference to the scheduler.
	 */
	public CPU(){

	}

//	/**
//	 * Called when the cpu receives an interrupt. Asks the scheduler what
//	 * process is next and performs the switch.
//	 */
//	private void interruptMethod(){
//		//	Process next = the_scheduler.nextProcess();
//		//	current_process.wait();
//		//	current_process = next;
//		//	run();
//		// might not be needed anymore
//	}

	/**
	 * Override of the update method. Is used to be notified when there is an interrupt.
	 * and then ask the scheduler what to do.
	 */
	@Override
	public void update(Observable arg0, Object arg1) {
		Process next = null;
		if(arg0 == null){
			System.out.println("Process Blocked. Switching");
			next = the_scheduler.nextProcess();
		} else if(arg0.getClass().getName().equals("IODevices")) {
			System.out.println("Recieved Input");
			the_scheduler.IOInput();
			System.out.println("Added to register");
			register.add((Integer) arg1);
		} else {
			System.out.println("Timer Fired. Switching process");
			next = the_scheduler.nextProcess();
		}
		//might need to tell scheduler either io or timer
		if(next != null){
			current_process = next;
		} else{
			//do nothing i think.
		}
	}

	/**
	 * Adds a new instance of the scheduler.
	 */
	public void addScheduler(){
		the_scheduler = Scheduler.getInstance();
	}

	/**
	 * Starts the system timer and 
	 * adds observers to watch for changes.
	 */
	public void startTimer(){
		SystemTimer timer = new SystemTimer(this);
		timer.addObserver(this);
		
		IODevices io = new IODevices(this);
		io.addObserver(this);
		io.fireInterrupt();
		SystemTimer.setStarted(true);
		timer.fireInterrupt();
	}

	//upon io, tell scheduler. may get nothing back


	/**
	 * This is used to run the cpu. Runs the current process until interrupted.
	 */
	@Override
	public void run() {		

		if(current_process == null){
			current_process = the_scheduler.nextProcess();
		}
		while(true){ //not interrupted or observable 
			try {
				if(current_process.getState() == Process.State.BLOCKED){
					if(current_process.getNeed() == Process.ProcessNeed.IO){
						the_scheduler.waitIO(current_process);
					}
					update(null, null);
				}else {
					//System.out.println(SharedMemory.getInstance());
					//System.out.println("Instruction");
					current_process.nextInstruction();
					//System.out.println("Its doing things");
				}
			} catch (SegmentationException e) {
				e.printStackTrace();
			
			}
		} 
	}

	/**
	 * *************
	 * System calls*
	 * *************
	 */

	//DOnt need to handle exceptions within reason
	public int readMemory(final Process the_process, final int the_address) throws SegmentationException, MutexLockedException
	{
		//		TODO this is most likely empty memory at the moment.
		return SharedMemory.getInstance().read(the_process, the_address);
	}

	public void writeMemory(final Process the_process, final int the_address, final int the_data) throws SegmentationException, MutexLockedException
	{
		SharedMemory.getInstance().write(the_process, the_address, the_data);
	}

	public void lockMemory(final Process the_process, final int the_address) throws SegmentationException, MutexLockedException
	{
		SharedMemory.getInstance().lock(the_process, the_address);
	}

	public void unlockMemory(final Process the_process, final int the_address) throws SegmentationException, MutexLockedException
	{
		SharedMemory.getInstance().unlock(the_process, the_address);
	}

	public int getInput(final Process the_process) throws NoInputBuffered
	{
		// Should throw some kind of exception so that the process knows to block itself.
		//isn't the noInputBuffered the kind of exception that we need to throw?
		// needs the input to be added to shared memory in order for us to call something
		if(register.isEmpty()){
			throw new NoInputBuffered();
		}
		return register.removeFirst();
	}

}