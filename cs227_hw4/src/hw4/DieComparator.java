package hw4;

import java.util.Comparator;

import hw4.api.Die;
/**
 * @author Max Van de Wille
 */

/**
 * Comparator for ordering Die objects.  Dice are ordered first
 * by value; dice with the same value are ordered by their max value, and dice
 * with the same value and the same max value are ordered by whether they are
 * available, with available dice preceding non-available dice.
 */
public class DieComparator implements Comparator<Die>
{
	
	
  @Override
  public int compare(Die left, Die right)
  {
	  if (left.value() < right.value())
	  {
		  return -1;
	  }
	  if (left.value() > right.value())
	  {
		  return 1;
	  }
	  if (left.value() == right.value())
	  {
		  if(left.maxValue() > right.maxValue())
		  {
			  return 1;
		  }
		  if(left.maxValue() < right.maxValue())
		  {
			  return -1;
		  }
		  if(left.maxValue() == right.maxValue())
		  {
			  if(left.isAvailable() != right.isAvailable() && left.isAvailable() == true)
			  {
				  return -1;
			  }
			  if(left.isAvailable() != right.isAvailable() && right.isAvailable() == true)
			  {
				  return 1;
			  }
			  else
			  {
				  return 0;
			  }
		  }
	  }
	  return 0;
  }

}
