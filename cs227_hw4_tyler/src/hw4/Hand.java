  package hw4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import hw4.api.Die;

/**
 * This class represents values of a group of dice for a dice game such as Yahtzee in which 
 * multiple rolls per turn are allowed. The number of faces on the dice, 
 * the number of dice in the Hand, and the maximum number of rolls are configurable 
 * via the constructor. At any time some of the dice may be <em>available</em>
 * to be rolled, and the other dice are <em>fixed</em>.  Calls to the 
 * <code>roll()</code> method roll the available
 * dice only.  After the maximum number of rolls, all dice are automatically
 * fixed; before that, the client can select which dice to "keep" (change from
 * available to fixed) and which dice to "free" (change from fixed to
 * available).
 * <p>
 * Note that valid die values range from 1 through the given
 * <code>maxValue</code>. 
 */
public class Hand
{
	private int numOfDice;
	private int maximumValue;
	private int maximumRolls;
	private int currentRolls;
	private ArrayList<Die> savedDice = new ArrayList<>();
	private ArrayList<Die> availableDice = new ArrayList<>();

	
	
  /**
   * Constructs a new Hand in which each die initially has 
   * the value 1.
   * @param numDice
   *   number of dice in this group
   * @param maxValue
   *   largest possible die value, where values range from 1
   *   through <code>maxValue</code>
   * @param maxRolls
   *   maximum number of total rolls
   */
  public Hand(int numDice, int maxValue, int maxRolls)
  {
	  
	  numOfDice = numDice;
	  maximumValue = maxValue;
	  maximumRolls = maxRolls;
	  for(int i = 0; i < numDice; i++)
	  {
		 availableDice.add(new Die(1, maxValue));
	  }
	  
  }   
  
  /**
   * Constructs a new Hand in which each die initially has 
   * the value given by the <code>initialValues</code> array.
   * If the length of the array is greater than the number of dice, the
   * extra values are ignored.  If the length of the array is smaller
   * than the number of dice, remaining dice
   * will be initialized to the value 1.
   * <p>
   * This version of the constructor is primarily intended for testing.
   * @param numDice
   *   number of dice in this hand
   * @param maxValue
   *   largest possible die value, where values range from 1
   *   through <code>maxValue</code>
   * @param maxRolls
   *   maximum number of total rolls
   * @param initialValues
   *   initial values for the dice
   */
  public Hand(int numDice, int maxValue, int maxRolls, int[] initialValues)
  {
	  
	  numOfDice = numDice;
	  maximumValue = maxValue;
	  maximumRolls = maxRolls;	  
	  for(int i = 0; i < numDice; i++)
	  {
		 availableDice.add(new Die(initialValues[i], maxValue));
	  }
  }  
  
  /**
   * Returns the number of dice in this hand.
   * @return
   *   number of dice in this hand
   */
  public int getNumDice()
  {
    return numOfDice;
  }
  
  /**
   * Returns the maximum die value in this hand.
   * Valid values start at 1.
   * @return
   *   maximum die value
   */
  public int getMaxValue()
  {
    return maximumValue;
  }
  
  /**
   * Rolls all available dice using the given random number generator.
   * If the number of rolls has reached the maximum, all dice are
   * marked as fixed.
   * @param rand
   *   random number generator to be used for rolling dice
   */
  public void roll(Random rand)
  {
	  if(currentRolls < maximumRolls)
	  {
		  currentRolls++;
		  for(int i = 0; i < availableDice.size(); i++)
		  {
			  availableDice.get(i).roll(rand);
		  } 
	  }
	  else if(currentRolls == maximumRolls)
	  {
		  keepAll();
	  }
  }

  /**
   * Selects a single die value to be changed from the available dice to the
   * fixed dice. If there are multiple available dice with the given value, 
   * only one is changed to be fixed. Has no effect if the given value is 
   * not among the values in the available dice.  Has no effect if
   * the number of rolls has reached the maximum.
   * @param value
   *   die value to be changed from available to fixed
   */
  public void keep(int value)
  {
	  Die dieToChange = new Die(value, maximumValue);
	  if(currentRolls <= maximumRolls)
	  {
		  if(availableDice.contains(dieToChange))
		  {
			  availableDice.remove(dieToChange);
			  dieToChange.setAvailable(false);
			  savedDice.add(dieToChange);
		  }
	  }
  }

  /**
   * Selects a die value to be moved from the fixed dice to
   * the available dice (i.e. so it will be re-rolled in the
   * next call to <code>roll()</code>). If there are multiple fixed dice 
   * with the given value, only one is changed be available. 
   * Has no effect if the given value is 
   * not among the values in the fixed dice. Has no effect if
   * the number of rolls has reached the maximum.
   * @param value
   *   die value to be moved
   */
  public void free(int value)
  {
	  Die dieToChange = new Die(value, maximumValue);
	  dieToChange.setAvailable(false);
	  if(currentRolls <= maximumRolls)
	  {
		  if(savedDice.contains(dieToChange))
		  {
			  savedDice.remove(dieToChange);
			  dieToChange.setAvailable(true);
			  availableDice.add(dieToChange);
		  }
	  }
  }
  
  /**
   * Causes all die values to be changed from available to fixed.
   * Has no effect if the number of rolls has reached the maximum.
   */
  public void keepAll()
  {
	int savedSize = availableDice.size();
	for(int i = 0; i < savedSize; i++)
	{
		keep(availableDice.get(0).value());
	}
  }
  
  
  /**
   * Causes all die values to be changed from fixed to available. 
   * Has no effect if the number of rolls has reached the maximum.
   */
  public void freeAll()
  {
	  int savedSize = savedDice.size();
	  for(int i = 0; i < savedSize; i++)
		{
			free(savedDice.get(0).value());
		}
  }
  
  /**
   * Determines whether there are any dice available to be 
   * rolled in this hand.
   * @return
   *   true if there are no available dice, false otherwise
   */
  public boolean isComplete()
  {
	  if(availableDice.size() > 0)
	  {
		  return true;
	  }
      return false;
  }

  public Die[] getFixedDice()
  {
	  Die[] array = new Die[savedDice.size()];
	  if(savedDice.size() > 0)
	  {
		    for(int iterate = 0; iterate < numOfDice; iterate++)
		    {
		    	array[iterate] = savedDice.get(iterate);
		    }
			Arrays.sort(array, new DieComparator());
		    return array;
	  }
	  return array;
  }

  public Die[] getAvailableDice()
  {
	  Die[] array = new Die[availableDice.size()];
	  if(availableDice.size() > 0)
	  {
	    for(int iterate = 0; iterate < numOfDice; iterate++)
	    {
	    	array[iterate] = availableDice.get(iterate);
	    }
		Arrays.sort(array, new DieComparator());
	    return array;
	  }
	  return array;
  }
  
 
  /**
   * Returns all die values in this hand, in ascending order.
   * @return
   *   all die values in this hand
   */
  public int[] getAllValues()
  {
	  int[] values = new int[numOfDice];
	  Die[] allDie = getAllDice();
	  Arrays.sort(allDie, new DieComparator());
	  for(int i = 0; i < numOfDice; i++)
	  {
		  values[i] = allDie[i].value();
	  }
	  return values;
  }
  
  /**
   * Returns an array of all the dice in this hand.
   * @return
   *  array of all dice 
   */
  public Die[] getAllDice()
  {
	  ArrayList<Die> allDie = new ArrayList<>();
	  if(availableDice.size() > 0)
	  {
		  for(int aDie = 0; aDie < availableDice.size(); aDie++)
		  {
			  allDie.add(availableDice.get(aDie));
		  }
	  }
	  if(savedDice.size() > 0)
	  {
		  for(int sDie = 0; sDie < savedDice.size(); sDie++)
		  {
			  allDie.add(savedDice.get(sDie));
		  }
	  }
	  Die[] finalDie = new Die[numOfDice];
      return allDie.toArray(finalDie);
  }  
}
