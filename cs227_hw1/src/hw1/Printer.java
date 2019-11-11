package hw1;
/*
 * A model for the usage of paper and ink in a printer capable of single and double-sided print operations.
 * @author Max Van de Wille
 */
public class Printer 
{
	 /**
	 * Capacity, in ounces, of a new ink cartridge.
	 */
	 public static final double INK_CAPACITY = 2.0;

	 /**
	 * Amount of ink, in ounces, used per printed page.
	 */
	 public static final double INK_USAGE = 0.0023;
	 
	/**
	 * Amount of paper currently in the printer, given by initial number of sheets and altered when paper is added or used to print.
	 */
	
	 private int paperCount;
	 
	 /*
	  * Defining a variable for printer capacity to use in the addPaper() method since we cannot call local variable givenCapacity in the addPaper() method.
	  */
	 
	 private int paperCapacity;
	
	/*
	 * Defining a variable for paperUse to use in the getTotalPaperUse() method
	 */
	 
	 private int paperUse;
	
	/*
	 * Defining a variable for the amount of ink remaining in the printer after use and refills.
	 */
	
	 private double inkRemaining;
	
	 /**
	  * Constructs a printer that has the given capacity (number of sheets of paper it can hold). Initially it contains no paper and a full ink cartridge.
	  * @param givenCapacity The intended capacity of the printer
	  */
	
	 public Printer(int givenCapacity)
	 {
		 paperCount = 0;
		 paperCapacity = givenCapacity;
		 inkRemaining = INK_CAPACITY;
		 paperUse = 0;
		 
	 }
	 
	 /**
	  * Constructs a printer that has the given capacity (number of sheets of paper it can hold). Initially it contains the given number of sheets of paper and a full ink cartridge. Note that if givenNumberOfSheets is greater than givenCapacity, then the printer will initially contain givenCapacity sheets of paper.
	  * @param givenCapacity The intended capacity of the printer
	  * @param givenNumberOfSheets The quantity of paper loaded into the printer
	  */
	 
	 public Printer(int givenCapacity, int givenNumberOfSheets)
	 {
		 paperCount = givenNumberOfSheets;
		 paperCapacity = givenCapacity;
		 inkRemaining = INK_CAPACITY;
		 paperUse = 0;
	 }
	 
	 /**
	  * Adds the given number of sheets of paper to this printer, not exceeding the printer's capacity.
	  * @param additionalSheets The number of sheets to be added into the printer
	  */
	 
	 public void addPaper(int additionalSheets)
	 {
		//Compares additionalSheets input with the current empty space in the printer using paperCapacity - paperCount then adds either the desired amount of sheets or fully fills the printer if additionalSheets is too large
		paperCount = paperCount + Math.min(additionalSheets, (paperCapacity - paperCount));
		 
	 }
	 
	 /**
	  * Returns the number of sheets of paper currently in this printer. This value is never negative.
	  * @return The amount of paper remaining in the printer
	  */
	 
	 public int getCurrentPaper()
	 {
		 return paperCount;
		 
	 }
	 

	 /**
	  * Determines whether the ink has run out. Returns true if the amount of ink left is smaller than the quantity INK_USAGE needed to print one page
	  * @return A boolean value stating whether the printer has enough ink to print another sheet
	  */
	 
	 public boolean isInkOut()
	 {
		 return inkRemaining < 0.0023;
		 
	 }
	 
	 /**
	  * Simulates replacement of the ink cartridge, restoring the quantity of ink in the printer to INK_CAPACITY.
	  */
	 
	 public void replaceInk()
	 {
		 inkRemaining = INK_CAPACITY;
		 
	 }
	 
	 /**
	  * Simulates printing pages in one-sided mode, using the appropriate number of sheets and a corresponding quantity of ink. If there is not enough paper, the printer will use up all remaining paper and will only use the quantity of ink needed for the sheets actually printed. If there is not enough ink, the printer will use up all the ink, and will still use up the specified number of sheets of paper (i.e., it just prints a bunch of blank pages after the ink runs out).
	  * @param numberOfPages The number of pages/sheets desired to be printed in a single-sided print operation
	  */
	 
	 public void print(int numberOfPages)
	 {
		 // Changes the value of paperUse based on the number of sheets used in the print job, printer will max out at value of paperCount regardless of how many sheets are intended to be printed
		 paperUse = paperUse + Math.min(numberOfPages, paperCount);
		 // Changes the value of inkRemaining based on the amount of ink used for all sheets in the print job
		 inkRemaining = inkRemaining - Math.min(numberOfPages, paperCount)*INK_USAGE;
		 // Alters the value of remaining paper in the printer by the number of sheets actually printed (Maximum value of paperCount)
		 paperCount = paperCount - Math.min(paperCount, numberOfPages);
		 
	 }
	 
	 /**
	  * Simulates printing pages in two-sided mode, using the appropriate number of sheets and a corresponding quantity of ink. If there is not enough paper, the printer will use up all remaining paper and will only use the quantity of ink needed for the sheets actually printed. If there is not enough ink, the printer will use up all the ink, and will still use up the specified number of sheets of paper (i.e., it just prints a bunch of blank pages after the ink runs out).
	  * @param numberOfPages The number of pages desired to be printed in a double sided print operation where each sheet of paper will have two pages, one on the front and one on the back
	  */
	 
	 public void printTwoSided(int numberOfPages)
	 {
		 // Altering paperCount variable based on the pages selected to print provided that number is smaller than the current paperCount, then subtracting an additional modulus of numberOfPages and 2 so that if the requested print job is an odd number of pages, it will print the correct amount of sheets double sided and 1 sheet single sided
		 paperUse = paperUse + Math.min(numberOfPages, paperCount)/2 + Math.min(numberOfPages, paperCount)%2;
		 // Alters ink usage the same way as one sided print, as the number of pages still corresponds with ink usage
		 inkRemaining = inkRemaining - Math.min(numberOfPages, paperCount)*INK_USAGE;
		 // Alters remaining paper in the printer according to sheets printed divided by two, and the mod function will subtract an additional 1 if the numbers of pages desired to be printed is odd, as it will have to print an additional sheet with only one side of ink
		 paperCount = paperCount - (Math.min(paperCount, numberOfPages/2)) - numberOfPages%2;
		 
	 }
	 
	 /**
	  * Returns the total number of sheets of paper printed by this printer since its construction. Note this is counting sheets of paper, not pages printed, i.e. sheets used for two sided printing still count as just one sheet.
	  * @return The number of sheets printed thus far by the printer
	  */
	 
	 public int getTotalPaperUse()
	 {
		 return paperUse;
		 
	 }
	 
}