package lab8;

import java.util.Scanner;
import java.awt.Point;
import plotter.Plotter;
import plotter.Polyline;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;

public class PolyReader 
{
	public static void main(String[] args) throws FileNotFoundException
	{
		Scanner input = new Scanner(System.in);
		System.out.println("What is the name of your file?");
		String file = input.next();
		ArrayList<Polyline> polyFinal = new ArrayList<Polyline>(readFile(file));
		Plotter plotter = new Plotter();
		for (int i = 0; i < polyFinal.size(); i++)
		{
			plotter.plot(polyFinal.get(i));
		}
		input.close();
	}

	private static ArrayList<Polyline> readFile(String filename) throws FileNotFoundException
	{
		File file = new File(filename);
		Scanner scanner = new Scanner(file);
		
		ArrayList<Polyline> arr = new ArrayList<Polyline>();
		while (scanner.hasNextLine())
		{
			String currentLine = scanner.nextLine();
			if ((currentLine.trim()).length() != 0)
			{
				if (currentLine.charAt(0) != '#')
				{
					arr.add(polyCreator(currentLine));
				}
			}
		}
		scanner.close();
		return arr;
	}
	
	private static Polyline polyCreator(String line)
	{
		Scanner temp = new Scanner(line);
		int width = 1;
		ArrayList<String> lineArr = new ArrayList<String>();
		while (temp.hasNext())
		{
			lineArr.add(temp.next());
		}
		temp.close();
		if ((lineArr.get(0)).matches("\\d+"))
		{
			Polyline p1 = new Polyline(lineArr.get(1), width);
			width = Integer.valueOf(lineArr.get(0));
			for (int i = 2; i < lineArr.size() - 1; i++)
			{
				p1.addPoint(new Point(Integer.valueOf(lineArr.get(i)), Integer.valueOf(lineArr.get(i+1))));
				i++;
			}
			return p1;
		}
		else
		{
			width = 1;
			Polyline p2 = new Polyline(lineArr.get(0), width);
			for (int i = 1; i < lineArr.size() - 1; i++)
			{
				p2.addPoint(new Point(Integer.valueOf(lineArr.get(i)), Integer.valueOf(lineArr.get(i+1))));
				i++;
			}
			return p2;
		}
	}
}
