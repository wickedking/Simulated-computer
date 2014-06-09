import java.util.Observable;
import java.util.Observer;
import java.util.Random;
/**
 * Abstract class for creating a simple process.
 * @author Cody Shafer, Mike Westbrook, Brandon Martin, and Jesse Kitterman
 */
public abstract class Process implements Observer
{
	/**
	 * The number of instructions a process has. Most of them do nothing for this simulator.
	 */
	protected static final int NUM_INSTRUCTIONS = 10;
	/**
	 * This process' memory space. 
	 */
	private final Command[] my_instructions;
	
	/**
	 * Represents the possible states this process can be at any time.
	 */
	public enum State {RUNNING(), BLOCKED(), WAITING()};
	
	/**
	 * If a process is blocked, this will determine what the process is waiting for.
	 */
	public enum ProcessNeed {IO(), CPU()};
	 
	/**
	 * The state this process is in.
	 */
	private State my_state;
	
	/**
	 * What this process is waiting for. Null if nothing.
	 */
	private ProcessNeed my_need;
	
	/**
	 * Keeps track of what instruction to execute.
	 */
	private int my_pc = 0;
	
	private final int my_id;
	
	/**
	 * CPU that this process can make system calls on.
	 */
	private final CPU my_cpu;

	/**
	 * Location in shared memory this process can read/write to.
	 */
	private final int my_memory_location;
	private int my_priority;
	
	/**
	 * Constructs a new process.
	 * 
	 * @param the_id Unique id of this process.
	 * @param the_cpu CPU where system calls can be made.
	 * @param the_memory_location Where this process is able to read/write
	 */
	public Process(final int the_id, final CPU the_cpu, final int the_memory_location)
	{
		this(the_id, the_cpu, the_memory_location, new Random().nextInt(4) + 1);
	}
	
	/**
	 * Constructs a new process with priority in mind.
	 * @param the_id
	 * @param the_cpu
	 * @param the_memory_location
	 * @param the_priority
	 */
	public Process(final int the_id, final CPU the_cpu, final int the_memory_location, final int the_priority)
	{
		super();
		my_id = the_id;
		my_cpu = the_cpu;
		my_memory_location = the_memory_location;
		my_instructions = getInstructions();
		my_state = State.WAITING;
		my_pc = 0;
		my_priority = the_priority;
	}
	
	/**
	 * Performs the next instruction. Implemented commands are responsible for incrementing the PC.
	 * @throws SegmentationException
	 */
	public void nextInstruction() throws SegmentationException
	{
		if (my_state != State.BLOCKED && my_pc < my_instructions.length)
		{
			//my_state = State.RUNNING;
			my_instructions[my_pc].execute();
		}
	}
	
	/**
	 * Called when a mutex that this process is observing changes state. If this
	 * process did not cause the state change then it will block or move to waiting
	 * accordingly.
	 * 
	 * @param the_obs The mutex calling the update.
	 * @param the_update The update object that has the required information.
	 */
	public void update(final Observable the_obs, final Object the_update)
	{
		final MutexUpdate update = (MutexUpdate) the_update;
		if (update.isLocked() && !equals(update.getProcess()))
		{
			my_state = State.BLOCKED;
		}
		else if (!update.isLocked())
		{
			my_state = State.WAITING;
		}
	}

	/**
	 * @return The current state of this process.
	 */
	public State getState()
	{
		return my_state;
	}
	
	/**
	 * Sets the state of this process.
	 * @param the_state The state to set to.
	 */
	public void setState(final State the_state)
	{
		my_state = the_state;
	}
	
	public int getPriority()
	{
		return my_priority;
	}
	
	/**
	 * @return The CPU this process is running on.
	 */
	protected CPU getCPU()
	{
		return my_cpu;
	}
	
	/**
	 * @return The memory location this process can read/write to.
	 */
	protected int getMemoryLocation()
	{
		return my_memory_location;
	}
	
	/**
	 * @return This process' id.
	 */
	public int getId()
	{
		return my_id;
	}
	
	/**
	 * A filler method.
	 */
	protected void doNothing()
	{
		if (getPC() + 1 < my_instructions.length)
		{
			setPC(getPC() + 1);
		}
		else
		{
			setPC(0);
		}
	}
	
	/**
	 * Attempts to lock this process' memory.
	 * @throws SegmentationException
	 * @throws MutexLockedException
	 */
	protected void lockMemory() throws SegmentationException, MutexLockedException
	{
		getCPU().lockMemory(this, getMemoryLocation());
	}
	
	/**
	 * Attempts to unlock this process' memory.
	 * @throws SegmentationException
	 * @throws MutexLockedException
	 */
	protected void unlockMemory() throws SegmentationException, MutexLockedException
	{
		getCPU().unlockMemory(this, getMemoryLocation());
	}
	
	/**
	 * @return This process current PC
	 */
	protected int getPC()
	{
		return my_pc;
	}
	/**
	 * Sets this process' pc to the indicated address.
	 * @param the_pc
	 */
	protected void setPC(final int the_pc)
	{
		my_pc = the_pc;
	}
	
	/**
	 * @return The resource this process is blocking on or null if none.
	 */
	protected ProcessNeed getNeed()
	{
		return my_need;
	}
	
	/**
	 * Sets what this process needs or null if none.
	 * @param the_need
	 */
	protected void setNeed(final ProcessNeed the_need)
	{
		my_need = the_need;
	}
	
	/**
	 * @return Requests input from the cpu.
	 * @throws NoInputBuffered
	 */
	protected int getInput() throws NoInputBuffered
	{
		return getCPU().getInput(this);
	}

	/**
	 * @return An array of commands that this process has.
	 */
	protected abstract Command[] getInstructions();
	
	@Override
	public String toString()
	{
		return "PID : " + Integer.toString(my_id);
	}
	
	public boolean equals(final Object the_other)
	{
		boolean result = the_other == null;
		if (!result && the_other.getClass() == getClass())
		{
			final Process other = (Process) the_other;
			result = getId() == other.getId();
		}
		return result;
	}
	
	public int hashCode()
	{
		return toString().hashCode();
	}
}