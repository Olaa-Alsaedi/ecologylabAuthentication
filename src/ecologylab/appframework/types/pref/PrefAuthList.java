/**
 * 
 */
package ecologylab.appframework.types.pref;

import ecologylab.appframework.types.prefs.Pref;
import ecologylab.services.authentication.AuthenticationListXMLImpl;
import ecologylab.xml.xml_inherit;
import ecologylab.xml.ElementState.xml_nested;

/**
 * A preference that is an AuthenticationList.
 * 
 * @author Zachary O. Toups (zach@ecologylab.net)
 */
@xml_inherit
public class PrefAuthList extends Pref<AuthenticationListXMLImpl>
{
	@xml_nested
	AuthenticationListXMLImpl	value;

	public PrefAuthList()
	{
		super();
	}

	public PrefAuthList(String name, AuthenticationListXMLImpl authList)
	{
		super(name);
		this.value = authList;
	}

	/**
	 * @see ecologylab.appframework.types.prefs.Pref#getValue()
	 */
	@Override
	protected AuthenticationListXMLImpl getValue()
	{
		return value;
	}

	/**
	 * @see ecologylab.appframework.types.prefs.Pref#setValue(T)
	 */
	@Override
	public void setValue(AuthenticationListXMLImpl newValue)
	{
		this.value = newValue;

		prefChanged();
	}

	/**
	 * @see ecologylab.appframework.types.prefs.Pref#clone()
	 */
	@Override
	public Pref<AuthenticationListXMLImpl> clone()
	{
		return new PrefAuthList(this.name, this.value);
	}
}