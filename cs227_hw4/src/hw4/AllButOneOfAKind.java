package hw4;

import hw4.api.Die;
/**
 * 
 * @author Max Van de Wille
 *
 */
public class AllButOneOfAKind extends CategorySuper
{
	public AllButOneOfAKind(String name)
	{
		super(name);
	}
	
	public boolean isSatisfiedBy(Hand hand)
	{
		int count = 0;
		Die[] dieArr = new Die[hand.getAllDice().length];
		dieArr = hand.getAllDice();
		for (int i = 0; i < dieArr.length; i++)
		{
			for (int k = 0; k < dieArr.length; k++)
			{
				if (dieArr[i].value() == dieArr[k].value())
				{
					count++;
				}
				else
				{
					count = 0;
				}
			}
		}
		if (count == dieArr.length - 2)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
}
