package hw4;
/**
 * 
 * @author Max Van de Wille
 *
 */
public class CategorySuper
{
	boolean filled;
	int possibleScore;
	int actualScore;
	Hand storedHand;
	String display;
	
	/*
	 * 
	 */
	public CategorySuper(String name, int pointTotal)
	{
		display = name;
		actualScore = pointTotal;
	}
	
	/*
	 * 
	 */
	public CategorySuper(String name)
	{
		display = name;
	}
	
	public boolean isSatisfiedBy(Hand hand)
	{
		return false;
	}
	
	/*
	 * Determines whether this category is filled.
	 */
	public boolean isFilled()
	{
		return filled;
	}
	
	/*
	 * If the category has been filled, returns the score for the permanently saved hand that was used to fill it; otherwise returns 0.
	 */
	public int getScore()
	{
		if(filled = true)
		{
			return possibleScore;
		}
		else
		{
			return 0;
		}
	}
	
	/*
	 * Returns the Hand that was used to fill this category, or null if not filled.
	 */
	public Hand getHand()
	{
		return storedHand;
	}
	
	/*
	 * Returns the name for this category.
	 */
	public java.lang.String getDisplayName()
	{
		return display;
	}
	
	/*
	 * Permanently sets the hand being used to fill this category. The score is set to the value of getPotentialScore for the given hand. Throws IllegalStateException if the category has already been filled or if the given hand is not complete (as defined by the Hand.isComplete method).
	 */
	public void fill(Hand hand)
	{
		storedHand = hand;
	}
	
	/*
	 * Returns the potential score that would result from using the given hand to fill this category. Always returns zero if the isSatisfiedBy() method returns false for the given hand. This method does not modify the state of this category and does not modify the hand.
	 */
	public int getPotentialScore(Hand hand)
	{
		return 0;
	}
}
