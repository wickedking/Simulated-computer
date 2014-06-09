import java.util.Observable;

/**
 * A mutex is responsible for one memory location. If a process tries to lock
 * an already locked mutex, that process is blocked. A mutex is created with 
 * already locked with a producer owning the key.
 * @author Cody Shafer, Mike Westbrook, Brandon Martin, and Jesse Kitterman
 * 
 */
public class Mutex extends Observable
{
	/**
	 * The process to be locked.
	 */
	private Process my_locking_process;
	
	/**
	 * Flag to lock a process.
	 */
	private boolean my_is_locked = true;
	
	/**
	 * Constructor for locking a process
	 * @param the_proc
	 */
	public Mutex(final Process the_proc)
	{
		my_locking_process = the_proc;
	}
	
	/**
	 * Locks a process trying to access the mutex
	 * @param the_process
	 * @throws MutexLockedException
	 */
	public void lock(final Process the_process) throws MutexLockedException
	{
		//TODO dont update if already locked
		if (!isLocked())
		{
			my_is_locked = true;
			my_locking_process = the_process;
			notifyProcesses();
		}
		else if (!hasLock(the_process))
		{
			throw new MutexLockedException();
		}
	}
	
	/**
	 * Unlocks the process
	 * @param the_process
	 * @throws MutexLockedException
	 */
	public void unlock(final Process the_process) throws MutexLockedException
	{
		if (isLocked() && hasLock(the_process))
		{
			my_is_locked = false;
			my_locking_process = null;
			notifyProcesses();
		}
		else if (isLocked())
		{
			throw new MutexLockedException();
		}
//		Does not care if you try to unlock an unlocked mutex
	}
	
	/**
	 * Notifies the observers that something has changed.
	 */
	private void notifyProcesses()
	{
		setChanged();
		notifyObservers(new MutexUpdate(my_is_locked, my_locking_process));
	}
	
	/**
	 * Returns the flag for a process
	 * @return flag for a locked process
	 */
	public boolean isLocked()
	{
		return my_is_locked;
	}
	
	/**
	 * Returns a flag for the process
	 * @param the_process
	 * @return flag for a process
	 */
	public boolean hasLock(final Process the_process)
	{
		return isLocked() && the_process.equals(my_locking_process);
	}
}
