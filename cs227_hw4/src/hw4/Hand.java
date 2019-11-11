  package hw4;

import java.util.Random;

import java.util.ArrayList;
import java.util.Arrays;

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
/**
 * 
 * @author Max Van de Wille
 *
 */
public class Hand
{
	private int diceCount;
	private int rollCount;
	private ArrayList<Die> diceSaved = new ArrayList<Die>();
	private ArrayList<Die> diceAvailable = new ArrayList<Die>();
	private int maxNumRolls;
	private int maxDiceValue;
	
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
	  diceCount = numDice;
	  maxNumRolls = maxRolls;
	  maxDiceValue = maxValue;
	  for(int i = 0; i < diceCount; i++)
	  {
		  diceAvailable.add(new Die(1, maxValue));
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
	  diceCount = numDice;
	  maxNumRolls = maxRolls;
	  maxDiceValue = maxValue;
	  for(int i = 0; i < initialValues.length; i++)
	  {
		  diceAvailable.add(new Die(initialValues[i], maxValue));
	  }
	  
  }  
  
  /**
   * Returns the number of dice in this hand.
   * @return
   *   number of dice in this hand
   */
  public int getNumDice()
  {
	  return diceCount;
  }
  
  /**
   * Returns the maximum die value in this hand.
   * Valid values start at 1.
   * @return
   *   maximum die value
   */
  public int getMaxValue()
  {
	  return maxDiceValue;
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
	  if (rollCount >= maxNumRolls)
	  {
		  keepAll();
	  }
	  else
	  {
		  for(int i = 0; i < diceAvailable.size(); i++)
		  {
			  diceAvailable.get(i).roll(rand);
		  } 
		  rollCount++;
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
	  Die changeMePls = new Die(value, maxDiceValue);
	  if(rollCount <= maxNumRolls)
	  {
		  if(diceAvailable.contains(changeMePls))
		  {
			  diceAvailable.remove(changeMePls);
			  changeMePls.setAvailable(false);
			  diceSaved.add(changeMePls);
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
	  Die changeMePls2 = new Die(value, maxDiceValue);
	  changeMePls2.setAvailable(false);
	  if (rollCount <= maxNumRolls)
	  {
		  if(diceSaved.contains(changeMePls2))
		  {
			  diceSaved.remove(changeMePls2);
			  changeMePls2.setAvailable(true);
			  diceAvailable.add(changeMePls2);
		  }
	  }
  }
  
  /**
   * Causes all die values to be changed from available to fixed.
   * Has no effect if the number of rolls has reached the maximum.
   */
  public void keepAll()
  {
	  for(int i = 0; i < diceAvailable.size(); i++)
	  {
		  keep(diceAvailable.get(i).value());
	  }
  }
  
  
  /**
   * Causes all die values to be changed from fixed to available. 
   * Has no effect if the number of rolls has reached the maximum.
   */
  public void freeAll()
  {
	  for(int i = 0; i < diceSaved.size(); i++)
	  {
		  free(diceSaved.get(i).value());
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
	  if(diceAvailable.size() <= 0)
	  {
		  return false;
	  }
	  return true;
  }

  public Die[] getFixedDice()
  {
	  Die[] tempArr = new Die[diceSaved.size()];
	  for (int i = 0; i < diceCount; i++)
	  {
		  tempArr[i] = diceSaved.get(i);
	  }
	  Arrays.sort(tempArr, new DieComparator());
	  return tempArr;
  }

  public Die[] getAvailableDice()
  {
	  Die[] tempArr = new Die[diceAvailable.size()];
	  for(int i = 0; i < diceCount; i++)
	  {
		  tempArr[i] = diceAvailable.get(i);
	  }
	  Arrays.sort(tempArr, new DieComparator());
	  return tempArr;
  }
  
 
  /**
   * Returns all die values in this hand, in ascending order.
   * @return
   *   all die values in this hand
   */
  public int[] getAllValues()
  {
	  int[] returnArr = new int[diceCount];
	  Die[] tempArr = getAllDice();
	  for(int i = 0; i < diceCount; i++)
	  {
		  returnArr[i] = tempArr[i].value();
	  }
	  return returnArr;
  }
  
  /**
   * Returns an array of all the dice in this hand.
   * @return
   *  array of all dice 
   */
  public Die[] getAllDice()
  {
	  ArrayList<Die> diceArr = new ArrayList<Die>();
	  for(int i = 0; i < diceAvailable.size(); i++)
	  {
		  diceArr.add(diceAvailable.get(i));
	  }
	  for(int j = 0; j < diceSaved.size(); j++)
	  {
		  diceArr.add(diceSaved.get(j));
	  }
	  Die[] returnArr = new Die[diceArr.size()];
	  for(int k = 0; k < diceArr.size(); k++)
	  {
		  returnArr[k] = diceArr.get(k);
	  }
	  return returnArr;
  }
    
}
