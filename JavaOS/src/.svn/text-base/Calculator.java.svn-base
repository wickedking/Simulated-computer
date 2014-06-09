/**
 * Creates a new calculator that decrements a memory location by 1.
 * @author Cody Shafer, Mike Westbrook, Brandon Martin, and Jesse Kitterman
 *
 */

public class Calculator extends Process
{
	/**
	 * The addresses of the 'useful' instructions.
	 */
	private static final int GET_ADDRESS = NUM_INSTRUCTIONS / 3;
	private static final int DECREMENT_ADDRESS = NUM_INSTRUCTIONS / (3 / 2);
	private static final int WRITE_ADDRESS = NUM_INSTRUCTIONS - 1;
	
	/**
	 * This process' local memory.
	 */
	private int my_data = 0;

	/**
	 * creates a new calculator that decrements a memory location by 1.
	 * @param the_id
	 * @param the_cpu
	 * @param the_memory_location
	 */
	public Calculator(final int the_id, final CPU the_cpu, final int the_memory_location)
	{
		super(the_id, the_cpu, the_memory_location);
	}
	 
	/**
	 * {@inheritDoc}
	 */
	protected Command[] getInstructions()
	{
		final Command get = new Command(){public void execute(){getIntFromMemory();}};
		final Command decrement = new Command(){public void execute(){decrement();}};
		final Command write = new Command(){public void execute(){writeToOutput();}};
		final Command[] commands = new Command[NUM_INSTRUCTIONS];
		for (int i = 0; i < NUM_INSTRUCTIONS; i++)
		{
			switch (i)
			{
				case GET_ADDRESS:
					commands[i] = get;
					break;
				case DECREMENT_ADDRESS:
					commands[i] = decrement;
					break;
				case WRITE_ADDRESS:
					commands[i] = write;
					break;
				default:
					commands[i] = new Command(){public void execute(){doNothing();}};
					break;
			}
		}
		return commands;
	}
	
	/**
	 * Retrieves an int from memory.
	 */
	private void getIntFromMemory()
	{
		try
		{
//			Once it blocks itself it will recieve two updates before it can run again.
//			one when the producer locks the memory, in which case process remains blocked. And then 
//			again once the producer unlocks, signaling that it has put something in memory.
			
			setNeed(null);
			getCPU().lockMemory(this, this.getMemoryLocation());
			my_data = getCPU().readMemory(this, getMemoryLocation());
			getCPU().unlockMemory(this, getMemoryLocation());
			setPC(getPC() + 1);
//			If the memory is locked it will block itself. 
			if (my_data < 1)
			{
				setState(State.BLOCKED);
				setNeed(ProcessNeed.CPU);
			}
		} catch (SegmentationException e)
		{
			System.out.println("Memory out of range.");
		} catch (MutexLockedException e)
		{
			setState(State.BLOCKED);
			setNeed(ProcessNeed.CPU);
		}
	}
	
	/**
	 * This determines if the number in a memory location is prime.
	 */
	private void decrement()
	{
		my_data--;
		setPC(getPC() + 1);
	}

	/**
	 * Writes the result of the decrement to memory.
	 */
	private void writeToOutput()
	{
		try
		{
			getCPU().lockMemory(this, getMemoryLocation());
			getCPU().writeMemory(this, getMemoryLocation(), my_data);
			getCPU().unlockMemory(this, getMemoryLocation());
			setPC(0);
			setState(State.WAITING);
		} catch (SegmentationException e)
		{
			e.printStackTrace();
		} catch (MutexLockedException e)
		{
			e.printStackTrace();
		}
	}
}
