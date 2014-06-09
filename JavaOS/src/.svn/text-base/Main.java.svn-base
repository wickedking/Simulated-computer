import java.util.ArrayList;
import java.util.Scanner;


/**
 * Main program, begins the simple console GUI.
 * @author Cody Shafer, Mike Westbrook, Brandon Martin, and Jesse Kitterman
 *  
 */
public class Main { 

	/**
	 * @param args 
	 * @throws InterruptedException 
	 */
	public static void main(String[] args){
		//call timer to  start
		//ask user which type of scheduler

		CPU cpu = new CPU();
		//cpu done
		//timer is created in the cpu at creation
		//sceduler done at bottom
		//processes is also done at bottom.
		//IO?
		
		
		
		System.out.println("Which type of scheduler would you like to use?");
		System.out.println("(1) Round Robin");
		System.out.println("(2) Priority");
		System.out.println("(3) Lottery");

		Scanner s = new Scanner(System.in);
		int input = s.nextInt();
		Scheduler.State the_state = Scheduler.State.ROUND_ROBIN;
		if(input == 1) {
			the_state = Scheduler.State.ROUND_ROBIN;
		} else if(input == 2) {
			the_state = Scheduler.State.PRIORITY;
		}else if(input == 3) {
			the_state = Scheduler.State.LOTTO;
		}
		
		System.out.println("How many Procedure/consumer pairs would you like?\n");
		int pair = s.nextInt();
		s.close();

		Process[] procedures = new Process[pair];
		Process[] consumers = new Process[pair];
		int id = 0;
		for(int i = 0; i < pair; i++){
			procedures[i] = new Producer(id, cpu, i);
			id++;
			consumers[i] = new Calculator(id, cpu, i);
			id++;
		}
		
		SharedMemory.getInstance(procedures, consumers);
		ArrayList<Process> processes = new ArrayList<Process>();
		for(int i = 0; i < procedures.length; i++){
			processes.add(procedures[i]);
			processes.add(consumers[i]);
		}

		Process idle = new Idle(id, cpu, 1000);
		idle.setState(Process.State.WAITING);
		processes.add(idle);
		
		Scheduler.getInstance(processes, the_state);
		cpu.addScheduler();
		cpu.startTimer();
		cpu.start();


	}




}
