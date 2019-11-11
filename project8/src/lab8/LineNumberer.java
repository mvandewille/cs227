package lab8;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class LineNumberer
{
  public static void main(String[] args) throws FileNotFoundException
  {
	File file = new File("../lab7/bin/lab7/Deck.java");
	  
    Scanner scanner = new Scanner(file);
    int lineCount = 1;

    while (scanner.hasNextLine())
    {
      String line = scanner.nextLine();
      System.out.print(lineCount + " ");
      System.out.println(line);
      lineCount += 1;
    }
    scanner.close();
    System.out.println("Done");
  }
}