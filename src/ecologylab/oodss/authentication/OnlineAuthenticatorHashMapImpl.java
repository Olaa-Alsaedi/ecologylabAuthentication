/*
 * Created on Oct 31, 2006
 */
package ecologylab.oodss.authentication;

import java.util.HashMap;
import java.util.Set;

import ecologylab.generic.Debug;
import ecologylab.oodss.exceptions.SaveFailedException;

/**
 * Encapsulates all authentication actions (tracking who is online, etc.), so that Servers don't
 * need to. Requires a backend database of users with passwords (an AuthenticationList).
 * 
 * @author Zachary O. Toups (zach@ecologylab.net)
 */
public class OnlineAuthenticatorHashMapImpl<A extends User> extends Debug implements
		OnlineAuthenticator<A>
{
	protected AuthenticationList<A>		authList;

	/**
	 * Map of authenticated keys to session ids. Authenticated keys are typically either usernames or
	 * email addresses, depending on implementation. Subclasses may use another key, if desired.
	 * 
	 * In the base implementation (OnlineAuthenticatorHashMapImpl), the authentication key is
	 * username.
	 */
	protected HashMap<String, String>	authKeyToSessionId	= new HashMap<String, String>();

	/**
	 * Map of session ids to authenticated keys. As with authKeyToSessionId, authenticated key may be
	 * an email address or username, depending on implementation. This reverse lookup is provided when
	 * there is a need to logout a session without knowing the key.
	 * 
	 * In the base implementation (OnlineAuthenticatorHashMapImpl), the authentication key is
	 * username.
	 */
	protected HashMap<String, String>	sessionIdToAuthKey	= new HashMap<String, String>();

	/**
	 * Creates a new Authenticator using the given AuthenticationList as a backend database of
	 * usernames and passwords.
	 * 
	 * @param source
	 *          - the AuthenticationList of usernames and passwords to use for authentication.
	 */
	public OnlineAuthenticatorHashMapImpl(AuthenticationList<A> source)
	{
		authList = source;
	}

	/**
	 * @see ecologylab.oodss.authentication.OnlineAuthenticator#login(A, java.lang.String)
	 */
	public boolean login(A entry, String sessionId)
	{
		System.out.println("*****************************************");
		System.out.println("entry: " + entry.toString());

		boolean loggedInSuccessfully = false;

		// first see if the username exists
		// TODO removed the check if the username exists; isValid checks already
		if (entry != null)
		{
			// check password
			if (authList.isValid(entry))
			{
				// now make sure that the user isn't already logged-in
				if (!sessionIdToAuthKey.containsKey(entry.getUserKey()))
				{
					// mark login successful
					loggedInSuccessfully = true;

					// and add to collections
					addAuthenticatedSession(entry.getUserKey(), sessionId);

					authList.setUID(entry);
					entry.setSessionId(sessionId);
				}
				else
				{
					debug("already logged in.");
				}
			}
			else
			{
				debug("invalid user with session id");
			}
		}
		else if (entry == null)
		{
			debug("<null> attempted login.");
			loggedInSuccessfully = false;
		}
		else
		{
			debug("username: " + entry.getUserKey() + " does not exist in authentication list.");
		}

		return loggedInSuccessfully;
	}

	/**
	 * @see ecologylab.oodss.authentication.AuthenticationList#getAccessLevel(ecologylab.oodss.authentication.User)
	 */
	public int getAccessLevel(A entry)
	{
		if (authList.isValid(entry))
		{
			return authList.getAccessLevel(entry);
		}
		else
		{
			return -1;
		}
	}

	@Override
	public int getAccessLevel(String userKey)
	{
		return authList.getAccessLevel(userKey);
	}
	
	/**
	 * @see ecologylab.oodss.authentication.OnlineAuthenticator#usersLoggedIn(A)
	 */
	public Set<String> usersLoggedIn(A administrator)
	{
		if (this.getAccessLevel(administrator) >= AuthLevels.ADMINISTRATOR)
		{
			return this.usersLoggedIn();
		}
		else
		{
			return null;
		}
	}

	/**
	 * @see ecologylab.oodss.authentication.OnlineAuthenticator#usersLoggedIn(A)
	 */
	public Set<String> usersLoggedIn()
	{
		return this.authKeyToSessionId.keySet();
	}

	/**
	 * @see ecologylab.oodss.authentication.OnlineAuthenticator#logout(A, java.lang.String)
	 */
	public boolean logout(A entry, String sessionId)
	{
		try
		{
			if (entry.getUserKey().equals(this.sessionIdToAuthKey.get(sessionId)))
			{
				removeSessionByUsername(entry.getUserKey());
				entry.setSessionId(null);
				return true;
			}
			else
			{
				return false;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}

	public String getSessionId(A entry)
	{
		return this.authKeyToSessionId.get(entry.getUserKey());
	}

	/**
	 * @see ecologylab.oodss.authentication.OnlineAuthenticator#isLoggedIn(java.lang.String)
	 */
	public boolean isLoggedIn(A entry)
	{
		return (authKeyToSessionId.containsKey(entry.getUserKey()));
	}

	protected void removeSessionByUsername(String username)
	{
		Object key = authKeyToSessionId.remove(username);

		if (key != null)
		{
			this.sessionIdToAuthKey.remove(key);
		}
	}

	public void logoutBySessionId(String sessionId)
	{
		String key = sessionIdToAuthKey.remove(sessionId);

		if (key != null)
		{
			this.authKeyToSessionId.remove(key);
		}
	}

	/**
	 * Adds a username + sessionId to the appropriate tracking objects. Should be called only after a
	 * session has been created (such as by login()).
	 * 
	 * @param username
	 * @param sessionId
	 */
	protected void addAuthenticatedSession(String username, String sessionId)
	{
		this.sessionIdToAuthKey.put(sessionId, username);
		this.authKeyToSessionId.put(username, sessionId);
	}

	public boolean sessionValid(String sessionId)
	{
		return this.sessionIdToAuthKey.containsKey(sessionId);
	}

	/**
	 * @throws SaveFailedException
	 * @see ecologylab.oodss.authentication.AuthenticationList#addUser(ecologylab.oodss.authentication.User)
	 */
	public boolean addUser(A entry) throws SaveFailedException
	{
		return this.authList.addUser(entry);
	}

	/**
	 * @see ecologylab.oodss.authentication.AuthenticationList#contains(ecologylab.oodss.authentication.User)
	 */
	public boolean contains(A entry)
	{
		return this.authList.contains(entry);
	}

	/**
	 * @see ecologylab.oodss.authentication.AuthenticationList#isValid(ecologylab.oodss.authentication.User)
	 */
	public boolean isValid(A entry)
	{
		return this.authList.isValid(entry);
	}

	/**
	 * @throws SaveFailedException
	 * @see ecologylab.oodss.authentication.AuthenticationList#removeUser(ecologylab.oodss.authentication.User)
	 */
	public boolean removeUser(A entry) throws SaveFailedException
	{
		return this.authList.removeUser(entry);
	}

	/**
	 * @see ecologylab.oodss.authentication.AuthenticationList#setUID(ecologylab.oodss.authentication.User)
	 */
	public void setUID(A entry)
	{
		this.authList.setUID(entry);
	}

	/**
	 * @see ecologylab.oodss.authentication.AuthenticationList#save()
	 */
	public void save() throws SaveFailedException
	{
		this.authList.save();
	}
}
