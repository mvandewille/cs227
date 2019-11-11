package project6;

public class NameToInitials
{
	public static String findInitials(String name)
	{
		String initials = String.valueOf(name.charAt(0));
		char b = ' ';
		for (int i = 0; i < name.length(); i ++)
		{
			char c = name.charAt(i);
			if (c == ' ')
			{
				b = name.charAt(i + 1);
				initials += b;
			}
		}
		return initials;
	}
}