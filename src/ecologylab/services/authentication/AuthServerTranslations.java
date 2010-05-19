package ecologylab.services.authentication;

import ecologylab.appframework.types.pref.AuthTranslations;
import ecologylab.services.messages.DefaultServicesTranslations;
import ecologylab.xml.TranslationScope;

/**
 * Contains all of the information necessary to translate XML objects used in an authenticating
 * server. Use AuthenticationTranslations.get() to acquire a TranslationSpace.
 * 
 * @author Zachary O. Toups (zach@ecologylab.net)
 * 
 */
public class AuthServerTranslations
{
	protected static final String	NAME						= "authentication";

	static final Class						TRANSLATIONS[]	=
																								{
			ecologylab.services.authentication.messages.Login.class,
			ecologylab.services.authentication.messages.Logout.class,
			ecologylab.services.authentication.messages.LoginStatusResponse.class,
			ecologylab.services.authentication.messages.LogoutStatusResponse.class,
			ecologylab.services.authentication.User.class,
			ecologylab.services.authentication.UserWithEmail.class,
			ecologylab.services.authentication.AuthenticationListXMLImpl.class };

	public static TranslationScope get()
	{
		return TranslationScope.get(NAME, DefaultServicesTranslations.get(), TRANSLATIONS);
	}

	public static TranslationScope get(String customName, TranslationScope otherSpaceToCompose)
	{
		TranslationScope[] spaces =
		{ DefaultServicesTranslations.get(), AuthTranslations.get(), otherSpaceToCompose };

		return TranslationScope.get(customName, spaces, TRANSLATIONS);
	}
}