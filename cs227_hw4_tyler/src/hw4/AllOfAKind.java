package hw4;

import hw4.api.ScoringCategory;

public class AllOfAKind implements ScoringCategory
{
	private String categoryName;
	private int categoryPoints;
	private boolean filledState;
	private Hand hand;
	
	public AllOfAKind(String name, int points)
	{
		categoryName = name;
		categoryPoints = points;
	}

	@Override
	public boolean isFilled() 
	{
		return filledState;
	}

	@Override
	public int getScore() {
		if(filledState)
		{
			return categoryPoints;
		}
		return 0;
	}

	@Override
	public Hand getHand() {
		if(filledState)
		{
			return hand;
		}
		return null;
	}

	@Override
	public String getDisplayName() 
	{
		return categoryName;
	}

	@Override
	public void fill(Hand hand) throws IllegalStateException 
	{	
		if(isFilled() || !hand.isComplete())
		{
			throw new IllegalStateException();
		}
		else
		{
			filledState = true;
			this.hand = hand;
			categoryPoints = getPotentialScore(hand);
		}		
	}

	@Override
	public boolean isSatisfiedBy(Hand hand) {
		Hand h = hand;
		int[] values = h.getAllValues();
		for(int iterate = 0; iterate < (h.getNumDice() - 1); iterate++)
		{
			if(values[iterate] != values[iterate + 1])
			{
				return false;
			}
		}
		return true;
	}

	@Override
	public int getPotentialScore(Hand hand) {
		if(isSatisfiedBy(getHand()))
		{
			return categoryPoints;
		}
		return 0;
	}

}
