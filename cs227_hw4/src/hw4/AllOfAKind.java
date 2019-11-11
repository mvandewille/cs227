package hw4;

import hw4.api.Die;
/**
 * 
 * @author Max Van de Wille
 *
 */
public class AllOfAKind extends CategorySuper
{
	public AllOfAKind(String name, int points)
	{
		super(name, points);
	}
	
	public boolean isSatisfiedBy(Hand hand)
	{
		Die[] dieArr = new Die[hand.getAllDice().length];
		dieArr = hand.getAllDice();
		for (int i = 0; i < dieArr.length - 1; i++)
		{
			if(dieArr[i].value() == dieArr[0].value())
			{
				return true;
			}
		}
		return false;
	}
}
