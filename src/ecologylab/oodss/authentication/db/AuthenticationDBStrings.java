/**
 * 
 */
package ecologylab.oodss.authentication.db;

/**
 * @author Zachary O. Toups (zach@ecologylab.net)
 * 
 */
public interface AuthenticationDBStrings
{
	/** End of an SQL statement that concludes with a string (includes single quotation mark). */
	static final String	STATEMENT_END_STRING									= "';";

	static final String	END_STRING														= ";";

	/** End of an SQL statement that concludes with a string inside a parenthesis. */
	static final String	STATEMENT_END_STRING_PAREN						= "');";

	/**
	 * 
	 */
	static final String	LIST_SEPARATOR_STRING_TYPE						= "', '";

	static final String	TABLE_USER														= "user";

	static final String	COL_UID																= "uid";

	static final String	COL_USER_KEY													= "userKey";

	static final String	COL_PASSWORD													= "password";

	static final String	COL_EMAIL															= "email";

	static final String	COL_LEVEL															= "level";

	static final String	COL_ONLINE														= "online";

	static final String	COL_LAST_ONLINE												= "lastOnline";

	static final String	COL_SESSION_ID												= "sessionId";

	static final String	INSERT_USER_PREFIX										= "INSERT INTO "
																																+ TABLE_USER
																																+ " ("
																																+ COL_USER_KEY
																																+ ", "
																																+ COL_PASSWORD
																																+ ", "
																																+ COL_EMAIL
																																+ ") VALUES ('";

	static final String	SELECT_USER_BY_USER_KEY_PREFIX				= "SELECT * FROM "
																																+ TABLE_USER
																																+ " WHERE "
																																+ COL_USER_KEY
																																+ " = '";

	static final String	SELECT_USER_BY_SESSION_ID_PREFIX			= "SELECT * FROM "
																																+ TABLE_USER
																																+ " WHERE "
																																+ COL_SESSION_ID
																																+ " = '";

	static final String	SELECT_USER_LEVEL_BY_USER_KEY_PREFIX	= "SELECT "
																																+ COL_LEVEL
																																+ " FROM "
																																+ TABLE_USER
																																+ " WHERE "
																																+ COL_USER_KEY
																																+ " = '";

	static final String	DELETE_USER_BY_USER_KEY_PREFIX				= "DELETE FROM "
																																+ TABLE_USER
																																+ " WHERE "
																																+ COL_USER_KEY
																																+ " = '";

	static final String	LOGIN_USER_PREFIX											= "UPDATE "
																																+ TABLE_USER
																																+ " SET "
																																+ COL_ONLINE
																																+ "=TRUE, "
																																+ COL_LAST_ONLINE
																																+ "=now(), "
																																+ COL_SESSION_ID
																																+ " = '";

	static final String	LOGIN_USER_BY_USER_KEY_WHERE_CLAUSE		= "' WHERE " + COL_USER_KEY + "='";

	static final String	LOGOUT_USER_BY_USER_KEY_PREFIX				= "UPDATE "
																																+ TABLE_USER
																																+ " SET "
																																+ COL_ONLINE
																																+ "=FALSE WHERE "
																																+ COL_USER_KEY
																																+ "='";

	static final String	LOGOUT_USER_BY_SESSION_ID_PREFIX			= "UPDATE "
																																+ TABLE_USER
																																+ " SET "
																																+ COL_ONLINE
																																+ "=FALSE, "
																																+ COL_SESSION_ID
																																+ "=NULL WHERE "
																																+ COL_SESSION_ID
																																+ "='";

	static final String	SELECT_ALL_ONLINE_USERS								= "SELECT * FROM "
																																+ TABLE_USER
																																+ " WHERE "
																																+ COL_ONLINE
																																+ "=TRUE;";
}
