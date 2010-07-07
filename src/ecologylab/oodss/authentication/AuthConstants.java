/*
 * Created on May 12, 2006
 */
package ecologylab.oodss.authentication;

/**
 * Constants for authenticating clients.
 * 
 * @author Zachary O. Toups (zach@ecologylab.net)
 * 
 */
public interface AuthConstants
{
	/**
	 * Specifies how long an NIOAuthClient will wait for a response from the server regarding log in status in
	 * milliseconds.
	 */
	public static final int		LOGIN_WAIT_TIME		= 10000;

	/** Message indicating login failure due to timeout. */
	public static final String	LOGIN_FAILED_TIMEOUT	= "Server failed to respond after waiting for "
																			+ (LOGIN_WAIT_TIME / 1000) + " seconds.";
}
