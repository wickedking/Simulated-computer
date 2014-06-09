
/**
 * Grabs ints from the input and writes them to memory for the calculator.
 * @author Mike Westbrook
 *
 */
public class Producer extends Process
{
	private static final int WAIT_ADDRESS = NUM_INSTRUCTIONS / 3;
	private static final int WRITE_ADDRESS = NUM_INSTRUCTIONS - 1;
	private int my_data = 10;

	/**
	 * Grabs ints from the input and writes them to memory for the calculator.
	 * @param the_id
	 * @param the_cpu
	 * @param the_memory_location
	 */
	public Producer(final int the_id, final CPU the_cpu, final int the_memory_location)
	{
		super(the_id, the_cpu, the_memory_location);
	}

	/**
	 * {@inheritDoc}
	 */
	protected Command[] getInstructions()
	{
		final Command wait = new Command(){public void execute(){waitForInput();}};
		final Command write = new Command(){public void execute(){writeToMemory();}};
		final Command[] commands = new Command[NUM_INSTRUCTIONS];
		for (int i = 0; i < NUM_INSTRUCTIONS; i++)
		{
			switch (i)
			{
				case WAIT_ADDRESS:
					commands[i] = wait;
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
	 * Waits for an input from the CPU.
	 */
	private void waitForInput()
	{
		System.out.println("Wait for input");
		try
		{
			my_data  = getCPU().getInput(this);
			setNeed(null);
			setPC(getPC() + 1);
		} catch (NoInputBuffered e)
		{
			setState(State.BLOCKED);
			setNeed(ProcessNeed.IO);
		}
	}
	
	/**
	 * Writes input to memory.
	 */
	private void writeToMemory()
	{
		System.out.println("Writing to memory");
		try
		{
			getCPU().lockMemory(this, getMemoryLocation());
			getCPU().writeMemory(this, getMemoryLocation(), my_data);
			getCPU().unlockMemory(this, getMemoryLocation());
			setNeed(null);
			setPC(0);
		} catch (SegmentationException e)
		{
			e.printStackTrace();
		} catch (MutexLockedException e)
		{
			setState(State.BLOCKED);
			setNeed(ProcessNeed.CPU);
		}
	}
	
}
