package lab8;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class StoryCounter
{
	public static void main(String[] args) throws FileNotFoundException
	{
		File file = new File("story.txt");
		Scanner lineFinder = new Scanner(file);
		int lineNum = 0;
		while (lineFinder.hasNextLine())
		{
			String line = lineFinder.nextLine();
			lineNum ++;
			System.out.println("Line " + lineNum + ": " + count(line) + " words");
		}
		lineFinder.close();
	}
	
	private static int count(String line)
	{
		Scanner scanner = new Scanner(line);
		ArrayList<String> lineArray = new ArrayList<String>();
		while (scanner.hasNext())
		{
			lineArray.add(scanner.next());
		}
		scanner.close();
		return lineArray.size();
	}
}
