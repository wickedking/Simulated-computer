/**
 * This class is used to update the Mutex states.
 * @author Cody Shafer, Mike Westbrook, Brandon Martin, and Jesse Kitterman
 */
public class MutexUpdate
{
	/**
	 * Used to flag a locked thread.
	 */
	private final boolean my_is_locked;
	
	/**
	 * The current process.
	 */
	private final Process my_process;
	
	/**
	 * Constructor to set the current state and process.
	 * 
	 * @param the_is_locked flag for state
	 * @param the_process the current process
	 */
	public MutexUpdate(final boolean the_is_locked, final Process the_process)
	{
		my_is_locked = the_is_locked;
		my_process = the_process;
	}
	
	/**
	 * Accessor for the flag.
	 * @return flag for status of the process.
	 */
	public boolean isLocked()
	{
		return my_is_locked;
	}
	
	/**
	 * Accessor for the process.
	 * @return the current process
	 */
	public Process getProcess()
	{
		return my_process;
	}
}
