/*
 * Created on Mar 30, 2006
 */
package ecologylab.authentication.messages;

import ecologylab.authentication.Authenticatable;
import ecologylab.authentication.User;
import ecologylab.authentication.registryobjects.AuthServerRegistryObjects;
import ecologylab.collections.Scope;
import ecologylab.oodss.distributed.server.clientsessionmanager.BaseSessionManager;
import ecologylab.oodss.messages.DisconnectRequest;
import ecologylab.serialization.annotations.simpl_composite;
import ecologylab.serialization.annotations.simpl_inherit;

/**
 * A Logout message indicates that the connnected client no longer wants to be connected.
 * 
 * @author Zachary O. Toups (zach@ecologylab.net)
 */
@simpl_inherit
public class Logout<SCOPE extends Scope> extends DisconnectRequest<SCOPE> implements AuthMessages,
		AuthServerRegistryObjects
{
	@simpl_composite
	protected User	entry	= new User("", "");

	/** Should not normally be used; only for XML translations. */
	public Logout()
	{
		super();
	}

	/**
	 * Creates a new Logout object using the given AuthenticationListEntry object, indicating the user
	 * that should be logged out of the server.
	 * 
	 * @param entry
	 *          - the entry to use for this Logout object.
	 */
	public Logout(User entry)
	{
		super();
		this.entry = entry;
	}

	/**
	 * Attempts to log the user specified by entry from the system; if they are already logged in; if
	 * not, sends a failure response.
	 */
	@Override
	public LogoutStatusResponse performService(SCOPE localScope)
	{
		debug("*************************** LOGOUT " + this.entry.getUserKey());
		Authenticatable server = (Authenticatable) localScope.get(MAIN_AUTHENTICATABLE);
		String sessionId = (String) localScope.get(BaseSessionManager.SESSION_ID);

		if (server.logout(entry, sessionId))
		{ // logout successful, return response and disconnect
			super.performService(localScope);
			return new LogoutStatusResponse(LOGOUT_SUCCESSFUL);
		}
		
		super.performService(localScope);
		return new LogoutStatusResponse(LOGOUT_FAILED_IP_MISMATCH);
	}

	/**
	 * @return Returns the entry.
	 */
	public User getEntry()
	{
		return entry;
	}

}
