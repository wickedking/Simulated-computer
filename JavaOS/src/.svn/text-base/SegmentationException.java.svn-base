
/**
 * This exception is thrown when a non-valid memory address is accessed.
 * @author Cody Shafer, Mike Westbrook, Brandon Martin, and Jesse Kitterman
 *
 */
@SuppressWarnings("serial")
public class SegmentationException extends Exception
{
	/**
	 * Memory address.
	 */
	private final int my_address;
 
	/**
	 * Constructor that instantiates the my_address.
	 * @param the_address
	 */
	public SegmentationException(final int the_address)
	{
		my_address = the_address;
	}
	
	/**
	 * Overridden message method.
	 */
	public String getMessage()
	{
		return Integer.toString(my_address) + " is not a valid memory address.";
	}
}
