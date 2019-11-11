package lab8;
import java.util.ArrayList;

public class RemoveDups 
{
	public static void removeDuplicates(ArrayList<String> words)
	{
		ArrayList<String> tempList = new ArrayList<String>();
		for (int i = 0; i < words.size(); i++)
		{
			if (tempList.contains(words.get(i)) == false)
			{
				tempList.add(words.get(i));
			}
		}
		words.clear();
		words.addAll(tempList);
	}

	public static void main(String[] args)
	{
		ArrayList<String> testInput = new ArrayList<String>();
		testInput.add("Jeff");
		testInput.add("Kyle");
		testInput.add("Sam");
		testInput.add("Jeff");
		testInput.add("Max");
		testInput.add("Random");
		testInput.add("Jeff");
		testInput.add("Sam");
		removeDuplicates(testInput);
		System.out.println(String.valueOf(testInput));
	}
}

