/*
 * Created on Mar 30, 2006
 */
package ecologylab.oodss.authentication.messages;

import ecologylab.collections.Scope;
import ecologylab.oodss.authentication.Authenticatable;
import ecologylab.oodss.authentication.User;
import ecologylab.oodss.authentication.registryobjects.AuthServerRegistryObjects;
import ecologylab.oodss.distributed.server.clientsessionmanager.BaseSessionManager;
import ecologylab.oodss.messages.RequestMessage;
import ecologylab.serialization.simpl_inherit;

/**
 * Used to log into a server that requires authentication; carries username and password information
 * in strings, and checks them against "authenticationList" in the objectRegistry.
 * 
 * @author Zachary O. Toups (zach@ecologylab.net)
 */
@simpl_inherit
public class Login<S extends Scope> extends RequestMessage<S> implements AuthMessages, AuthServerRegistryObjects, AuthenticationRequest
{
	@simpl_composite
	protected User	entry;

	/**
	 * Should not normally be used; only for XML translations.
	 */
	public Login()
	{
		super();
	}

	/**
	 * Creates a new Login object using the given AuthenticationListEntry.
	 * 
	 * @param entry
	 *          - the entry to use for the Login object.
	 */
	public Login(User entry)
	{
		super();
		this.entry = entry;
	}

	/**
	 * Creates a new Login object using the given username and password; the password is hashed, per
	 * AuthenticationListEntry, before it is stored.
	 * 
	 * @param username
	 *          - the username to use for the Login object.
	 * @param password
	 *          - the password to hash, and then use for the Login object.
	 */
	public Login(String username, String password)
	{
		this(new User(username, password));
	}

	/**
	 * Determines if the supplied username and password are contained in the list of usernames and
	 * passwords in the object registry.
	 * 
	 * @return A ResponseMessage indicating whether or not the username/password were accepted.
	 */
	@Override
	public LoginStatusResponse performService(S localScope)
	{
		Authenticatable authenticatable = (Authenticatable) localScope.get(MAIN_AUTHENTICATABLE);

		// set to the default failure message
		LoginStatusResponse loginConfirm = new LoginStatusResponse(LOGIN_FAILED_PASSWORD);

		boolean loginSuccess = false;

		if (this.getSender() != null)
		{
			String sessionId = (String) localScope.get(BaseSessionManager.SESSION_ID);
			loginSuccess = authenticatable.login(this.entry, sessionId);
		}

		if (loginSuccess)
		{ // we're logged in!
			loginConfirm.setExplanation(LOGIN_SUCCESSFUL);
		}
		else
		{
			// figure out why it failed
			if (this.getSender() == null)
			{
				loginConfirm.setExplanation(LOGIN_FAILED_NO_IP_SUPPLIED);
			}
			else if (authenticatable.isLoggedIn(entry))
			{
				loginConfirm.setExplanation(LOGIN_FAILED_LOGGEDIN);
			}
		}

		return loginConfirm;
	}

	/**
	 * @return Returns the entry.
	 */
	public User getEntry()
	{
		return entry;
	}

	/**
	 * @param entry
	 *          The entry to set.
	 */
	public void setEntry(User entry)
	{
		this.entry = entry;
	}
}
