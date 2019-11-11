package hw2;

import static hw2.BallDirection.*;

/**
 * Simulates and scores a game of tennis with two players, a server and a receiver.
 * @author Max Van de Wille
 *
 */

public class TennisGame
{
	//will be used to keep track of ball direction
	private BallDirection currentBallStatus;
	
	//will be used to keep track of server points in the game
	private int serverScore;
	
	//will be used to keep track of receiver points in the game
	private int receiverScore;
	
	//will be used to track if the ball is headed out of bounds, used by the miss() method but value is provided by hit() method thus instance variable is needed
	private boolean goingOutOfBounds;
	
	//will be used to ensure that if the server faults twice, a point is awarded
	private int numberServiceFaults;
	
	/**
	 * Creates a new TennisGame where both players will start with 0 points and the ball starts off not in play.
	 */
	
	public TennisGame()
	{
		//establishes the values of some instance variables under the impression that a new tennis game is being started
		
		serverScore = 0;
		receiverScore = 0;
		numberServiceFaults = 0;
		currentBallStatus = NOT_IN_PLAY;
	}
	
	/**
	 * Sets the scores for the server and receiver directly and sets ball status to not in play.
	 * @param newServerScore the amount of points you wish to give the server
	 * @param newReceiverScore the amount of points you wish to give the receiver
	 */
	
	public void setScore(int newServerScore, int newReceiverScore)
	{
		//sets the server and receiver scores to the desired values and currentBallStatus to NOT_IN_PLAY as indicated in the assignment
		
		serverScore = newServerScore;
		receiverScore = newReceiverScore;
		currentBallStatus = NOT_IN_PLAY;
	}
	
	/**
	 * Returns the raw score for the receiver
	 * @return current number of points for the receiver
	 */
	
	public int getReceiverPoints()
	{
		//since we need an instance variable to keep track of receiver score, all this method has to do is return this instance variable
		
		return receiverScore;
	}
	
	/**
	 * Returns the raw score for the server
	 * @return current number of points for the receiver
	 */
	
	public int getServerPoints()
	{
		//since we need an instance variable to keep track of server score, all this method has to do is return this instance variable
		
		return serverScore;
	}
	
	/**
	 * Returns the direction of the ball 
	 * @return current direction of the ball (NOT_IN_PLAY, TOWARD_RECEIVER, TOWARD_SERVER)
	 */
	
	public BallDirection getBallStatus()
	{
		//since we need an instance variable to keep track of ball direction, all this method has to do is return this instance variable
		
		return currentBallStatus;
		
	}
	
	/**
	 * Returns true if the game is over according to the rules of tennis
	 * @return boolean value, true is game is over and false if not
	 */
	
	public boolean isOver()
	{
		//simply tests if the server or receiver has reached the amount of points and point differential required to win
		
		if (serverScore > 3 && serverScore - receiverScore >=2)
		{
			return true;
		}
		if (receiverScore > 3 && receiverScore - serverScore >=2)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * Returns true if the server has won the game
	 * @return if the server won this is true
	 */
	
	public boolean serverWon()
	{
		//according to tennis scoring rules, serverScore must equal at least 4 and be at least 2 points greater than receiverScore if the server is to win
		
		if (serverScore > 3 && serverScore - receiverScore >=2)
		{
			return true;
		}
		else
		{
			return false;
		}
		
	}
	
	/**
	 * Returns true if the server has won the game
	 * @return if the receiver won this is true
	 */
	
	public boolean receiverWon()
	{
		//according to tennis scoring rules, receiverScore must equal at least 4 and be at least 2 points greater than serverScore if the receiver is to win
		
		if (receiverScore > 3 && receiverScore - serverScore >=2)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * This method simulates the server serving the ball towards the receiver, with a boolean parameter for if they have faulted their serve
	 * @param serviceFault dictates whether the server has faulted in their serve
	 */
	
	public void serve(boolean serviceFault)
	{
		//tests to ensure the game is not over and that the ball is currently not in play (i.e. any previous rally has ended)
		
		if (isOver() == false && currentBallStatus == NOT_IN_PLAY)
		{
			//if server does not fault, ball is now headed for receiver and their count of faults remains at 0
			
			if (serviceFault == false)
			{
				currentBallStatus = TOWARD_RECEIVER;
				numberServiceFaults = 0;
			}
			
			//if server faults, the ball remains not in play in preparation for the next serve, and the fault count increases
			
			else
			{
				currentBallStatus = NOT_IN_PLAY;
				numberServiceFaults += 1;
			}
			
			//if the fault count reaches 2, then the server has faulted twice in a row and the receiver is awarded a point
			
			if (numberServiceFaults == 2)
			{
				receiverScore += 1;
			}
		}
	}
	
	/**
	 * This method simulates a hit from either the server or the receiver, with boolean parameters for if they faulted their hit or if their hit is headed out of bounds
	 * @param fault true if the hitter faulted on their hit
	 * @param headedOutOfBounds true if the ball is headed out of bounds
	 */
	
	public void hit(boolean fault, boolean headedOutOfBounds)
	{
		//checks for fault to see if rally has ended
		
		if (fault == false)
		{
			//since rally is not over, reassign ball direction towards the other player
			
			if (currentBallStatus == TOWARD_SERVER)
			{
				currentBallStatus = TOWARD_RECEIVER;
			}
			else
			{
				if (currentBallStatus == TOWARD_RECEIVER)
				{
					currentBallStatus = TOWARD_SERVER;
				}
			}
		}
		else
		{
			//since fault is true, rally ends and opposite player is awarded a point
			
			if (currentBallStatus == TOWARD_SERVER)
			{
				receiverScore += 1;
			}
			if (currentBallStatus == TOWARD_RECEIVER)
			{
				serverScore += 1;
			}
			currentBallStatus = NOT_IN_PLAY;
		}
		//sets goingOutOfBounds equal to headedOutOfBounds for use in the miss() method
		
		goingOutOfBounds = headedOutOfBounds;
	}
	
	/**
	 * Simulates a miss from whichever player the ball is headed towards, causing the ball to hit the ground and the rally to end
	 */
	
	public void miss()
	{
		//ensures that ball is in play and evaluates who to give points to based on who the ball was headed towards at the time of the miss
		
		if (currentBallStatus == TOWARD_RECEIVER)
		{
			//if the ball is going out of bounds and is missed, the ball hits out of bounds and awards the player who missed a point
			
			if (goingOutOfBounds == true)
			{
				receiverScore += 1;
			}
			else
			{
				serverScore += 1;
			}
		}
		if (currentBallStatus == TOWARD_SERVER)
		{
			if (goingOutOfBounds == true)
			{
				serverScore += 1;
			}
			else
			{
				receiverScore += 1;
			}
		}
		
		//sets ball status to NOT_IN_PLAY as a point has been given out, now serve must be called to start another rally. resets goingOutOfBounds to false.
		
		currentBallStatus = NOT_IN_PLAY;
		goingOutOfBounds = false;
	}
	
	/**
	 * Returns the score of the game using each player's raw score
	 * @return string dictating the score of the game as "server-receiver"
	 */
	
	public java.lang.String getScore()
	{
		//returns in convention x-y where x = serverScore and y = receiverScore
		
		return (String.valueOf(serverScore) + "-" + String.valueOf(receiverScore));
	}
	
	/**
	 * Returns the score of the game using conventional tennis rules 
	 * @return string dictating the score of the game as "server-receiver"
	 */
	
	public java.lang.String getCallString()
	{
		//if the game is over, return as x-y similar to in getScore(), one if statement for server win and one for receiver win
		
		if (serverScore > 3 && serverScore - receiverScore >=2)
		{
			return (String.valueOf(serverScore) + "-" + String.valueOf(receiverScore));
		}	
		if (receiverScore > 3 && receiverScore - serverScore >=2)
		{
			return (String.valueOf(serverScore) + "-" + String.valueOf(receiverScore));
		}
		
		//custom return for Adv-4
		
		if (serverScore >= 4 && serverScore - receiverScore == 1)
		{
			return "advantage in";
		}
		
		//custom return for 4-Adv
		
		if (receiverScore >= 4 && receiverScore - serverScore == 1)
		{
			return "advantage out";
		}
		
		//custom return for 4-4 score
		
		if (serverScore == receiverScore && receiverScore >= 3 && serverScore >= 3)
		{
			return "deuce";
		}
		
		//custom return for 2-2 score
		
		if (serverScore == receiverScore && receiverScore == 2 && serverScore == 2)
		{
			return "30-all";
		}
		
		//custom return for 1-1 score
		
		if (serverScore == receiverScore && receiverScore == 1 && serverScore == 0)
		{
			return "15-all";
		}
		
		//custom return for 0-0 score
		
		if (serverScore == receiverScore && receiverScore == 0 && serverScore == 0)
		{
			return "love-all";
		}
		
		//else statement accounts for all other cases (i.e. the a-b returns)
		
		else
		{
			//create a string for serverScore/receiverScore multiplied by 15 to account for scores 15 and 30
			
			String a = String.valueOf(serverScore*15);
			String b = String.valueOf(receiverScore*15);
			
			//since scores go 15, 30, 40 not 15, 30, 45 i reassign a and b as 40 if they are previously 45
			
			if (serverScore == 3)
			{
				a = "40";
			}
			
			//since tennis conventions say 0 = love, i reassign valueOf(0) to "love" for a and b
			
			if (serverScore == 0) 
			{
				a = "love";
			}
			if (receiverScore == 3)
			{
				b = "40";
			}
			if (receiverScore == 0)
			{
				b = "love";
			}
			
			//output a-b as directed
			
			return (a + "-" + b);
		}
	}
}
