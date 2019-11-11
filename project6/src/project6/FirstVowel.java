package project6;

public class FirstVowel
{
	public static int vowelLocation(String givenString)
	{
		for (int i = 0; i < givenString.length(); i ++)
		{
			char ch = givenString.charAt(i);
			if ("aeiouAEIOU".indexOf(ch) >= 0)
			{
				return givenString.indexOf(ch)+1;
			}
		}
		return -1;
		
	}
}
