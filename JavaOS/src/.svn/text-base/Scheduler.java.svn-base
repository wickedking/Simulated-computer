import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * 
 * 
 * @author Brandon Martin
 * 
 */
public class Scheduler { 

	/**
	 * Enum for the current scheduler state
	 * @author Brandon
	 *
	 */
	public enum State {ROUND_ROBIN(), LOTTO(), PRIORITY()};

	/**
	 * the current scheduler state
	 */
	private State my_state;

	/**
	 * Priority level one list
	 */
	List<Process> priority_one = new ArrayList<Process>();

	/**
	 * Priority level two list
	 */
	List<Process> priority_two = new ArrayList<Process>();

	/**
	 * Priority level three list
	 */
	List<Process> priority_three = new ArrayList<Process>();

	/**
	 * Priority level four list
	 */
	List<Process> priority_four = new ArrayList<Process>();

	/**
	 * The running order for the process. 
	 */
	List<Process> processes;


	/**
	 * The current position in the list used to gather the next process.
	 */
	int the_current_process;

	/**
	 * A queue used to keep track of who gets the io in what order.
	 */
	LinkedList<Integer> io_queue = new LinkedList<Integer>();

	/**
	 * Reference to itself for singleton.
	 */
	private static Scheduler ME = null;


	/**
	 * Constructor for singleton scheduler. Takes in the list of process to scheduler
	 * @param the_process The list of processes.
	 */
	public Scheduler(List<Process> the_process, State the_state){
		processes = the_process;
		my_state = the_state;

		the_current_process = 0;

		switch (my_state) {
		case PRIORITY:
			priority();
			break;

		}
	}


	/**
	 * Used to return the next process the current state rules. Default is round robin
	 * @return The next process. 
	 */
	public Process nextProcess(){
		processes.get(processes.size() - 1).setState(Process.State.WAITING);
		Process next;
		Process old = processes.get(the_current_process);
		if(processes.get(the_current_process).getState() == Process.State.RUNNING) {
			processes.get(the_current_process).setState(Process.State.WAITING);        
		}
		switch (my_state) {
		case ROUND_ROBIN:
			next =  nextRobin();
			break;
		case LOTTO:
			next =  nextLotto();
			break;
		case PRIORITY:
			next =  nextPriority();
			break;
		default:
			next = nextRobin();
			break;
		}   
		if(old.getState() != Process.State.BLOCKED){
			old.setState(Process.State.WAITING);
		}
		next.setState(Process.State.RUNNING);
		System.out.println("Now running process: " + next.getId());
		for(int i = 0; i < processes.size(); i++){
			System.out.println("Process id: " + processes.get(i) + " State: " + processes.get(i).getState());
		}
		
		return next;

	}

	/**
	 * Implements the round robin type scheduling
	 * 
	 * @return next process
	 */
	private Process nextRobin(){
		the_current_process +=1;
		if(the_current_process >= processes.size()){
			the_current_process = 0;
		}
		Process next = processes.get(the_current_process);
		while(next.getState() != Process.State.WAITING){
			the_current_process +=1;
			if(the_current_process >= processes.size()){
				the_current_process = 0;
			}

			next = processes.get(the_current_process);
		}
		return next;
	}

	/**
	 * Implements the lottery type scheduler
	 * @return next process
	 */
	private Process nextLotto(){
		Random rand = new Random();
		int i = rand.nextInt(processes.size());
		while(processes.get(i).getState() == Process.State.BLOCKED) {

			i = rand.nextInt(processes.size());
		}
		return processes.get(i);
	}


	/**
	 * Get Instance for singleton. Can be called after the intial getInstance overloaded is called
	 * @return The reference to the scheduler
	 */
	public static Scheduler getInstance(){
		if(ME == null){
			return getInstance(new ArrayList<Process>(), State.ROUND_ROBIN);
		} 
		return ME;
	}

	/**
	 * Overloaded getInstance method used upon first creation
	 * @param the_process The processes needed to be scheduled
	 * @return The reference to the scheduler
	 */
	public static Scheduler getInstance(List<Process> the_process, State the_state){
		if(ME == null){
			ME = new Scheduler(the_process, the_state);
		} 
		return ME;
	}

	/**
	 * Used to add a process to 
	 * @param the_process
	 */
	public void waitIO(Process the_process){
		io_queue.add(processes.indexOf(the_process));
	}


	/**
	 * Implements the priority scheduler type.  Need to call prioritySelect() to pick
	 * a process.  This method only sets up the priorities.
	 */
	private void priority() {

		for( Process p : processes) {
			if(p.getPriority() == 4) {
				priority_four.add(p);
			}
			if(p.getPriority() == 3) {
				priority_three.add(p);
			}
			if(p.getPriority() == 2) {
				priority_two.add(p);
			}
			if(p.getPriority() == 1) {
				priority_one.add(p);
			}

		}
	}

	/**
	 * Selects a process based on priorty level.  Make sure priority() is called first.
	 * @return the selected process using priority schedule; null if none
	 */
	public Process nextPriority() {
		//process to return
		Process the_selected_process = null;


		for(int i = 0; i < priority_four.size(); i++) {
			if(priority_four.get(i).getState() != Process.State.BLOCKED ) {
				the_selected_process = priority_four.get(i);
				return the_selected_process;
			}   
		}

		for(int i = 0; i < priority_three.size(); i++) {
			if(priority_three.get(i).getState() != Process.State.BLOCKED ) {
				the_selected_process = priority_three.get(i);
				return the_selected_process;
			}   
		}

		for(int i = 0; i < priority_two.size(); i++) {
			if(priority_two.get(i).getState() != Process.State.BLOCKED ) {
				the_selected_process = priority_two.get(i);
				return the_selected_process;
			}   
		}

		for(int i = 0; i < priority_one.size(); i++) {
			if(priority_one.get(i).getState() != Process.State.BLOCKED ) {
				the_selected_process = priority_one.get(i);
				return the_selected_process;
			}   
		}

		//null if no processes in any priority list
		return null;
	}

	public void IOInput(){
		if(!io_queue.isEmpty()){
			int index = io_queue.removeFirst();
			processes.get(index).setState(Process.State.WAITING);
			processes.get(index).setNeed(null);
		}
	}

}
