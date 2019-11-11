package hw4;

import java.util.Comparator;

import hw4.api.Die;

/**
 * Comparator for ordering Die objects.  Dice are ordered first
 * by value; dice with the same value are ordered by their max value, and dice
 * with the same value and the same max value are ordered by whether they are
 * available, with available dice preceding non-available dice.
 * Returns negative if left comes before right, positive if right comes before left, and zero if equal
 * current value > max value > available > not available
 */
public class DieComparator implements Comparator<Die>
{
  @Override
  public int compare(Die left, Die right)
  {
    if(left.value() < right.value())
    {
    	return -1;
    }
    else if(right.value() < left.value())
    {
    	return 1;
    }
    else
    {
    	if(left.maxValue() < right.maxValue())
        {
        	return -1;
        }
        else if(right.maxValue() < left.maxValue())
        {
        	return 1;
        }
        else
        {
        	if((left.isAvailable() == true) && (right.isAvailable()) == false)
            {
            	return -1;
            }
            else if((left.isAvailable() == false) && (right.isAvailable()) == true)
            {
            	return 1;
            }
            else
            {
            	return 0;
            }
        }
    }
  }

}
