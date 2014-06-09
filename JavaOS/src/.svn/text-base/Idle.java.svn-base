/**
 * Idle command class that does no command.
 * @author Cody Shafer, Mike Westbrook, Brandon Martin, and Jesse Kitterman
 *
 */
public class Idle extends Process
{

	/**
	 * Constructs Idle process.
	 * 
	 * @param the_id
	 * @param the_cpu
	 * @param the_memory_location
	 */
	public Idle(int the_id, CPU the_cpu, int the_memory_location)
	{
		super(the_id, the_cpu, the_memory_location, 1);
	}

	/**
	 * Overrides from the command interface.
	 */
	@Override
	protected Command[] getInstructions()
	{
		final Command[] command = {new Command(){public void execute(){doNothing();}}};
		return command;
	}

}
