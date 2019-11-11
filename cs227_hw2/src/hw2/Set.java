package hw2;

import hw2.TennisGame;

/**
 * Simulates and scores a set of tennis with two players, player0 and player 1 alternating the role of server.
 * @author Max
 *
 */

public class Set
{
	//will be used to keep track of which player is server, 0 for player0 and 1 for player1
	private int server;
	
	//instance variable for minimum required games to win
	private int minSetLength;
	
	//will be used to track games won by player 1
	private int player1WinCount;
	
	//will be used to track games won by player0
	private int player0WinCount;
	
	//assigns a new variable of type TennisGame used to call on TennisGame methods that are not static and cant be called on directly
	private TennisGame currentGame = new TennisGame();
	
	/**
	 * Constructs a set of tennis games with a desired number of games required to win, and says who will serve first.
	 * @param minimumGamesToWin sets the number of tennis games required to win the set
	 * @param player1ServesFirst says if player1 will serve first
	 */
	
	public Set(int minimumGamesToWin, boolean player1ServesFirst)
	{
		//sets instance variable for server based on given parameter
		
		if (player1ServesFirst == true)
		{
			server = 1;
		}
		else
		{
			server = 0;
		}

		//establishes wins at 0 for a new set and sets value of instance variable for minimum required games
		
		player0WinCount = 0;
		player1WinCount = 0;
		minSetLength = minimumGamesToWin;
	}
	
	/**
	 * Simulates a serve and keeps track of the games won provided the serve ends the current game.
	 * @param serviceFault dictates whether or not the server faulted on their serve
	 */
	
	public void serve(boolean serviceFault)
	{
		//ensures that the game is not over and the set is not over
		
		if (isSetOver() == false && currentGame.isOver() == false)
		{
			//invokes serve() method from TennisGame class
			
			currentGame.serve(serviceFault);
			if (currentGame.isOver() == true)
			{
				//if calling the serve() method results in the end of the game, then assign wins to players according to who is currently serving. since only the receiver can win the game off of a faulted serve, no logic needed to determine who won game
				
				if (server == 1)
				{
					player1WinCount += 1;
				}
				if (server == 0)
				{
					player0WinCount += 1;
				}
			}
		}
	}
	
	/**
	 * Simulates a hit and keeps track of the games won provided the hit ends the current game.
	 * @param fault dictates if the hitter faulted on their hit
	 * @param outOfBounds says whether or not the ball is headed out of bounds as a result of the hit
	 */
	
	public void hit(boolean fault, boolean outOfBounds)
	{
		//ensures that the game is not over and the set is not over
		
		if (isSetOver() == false && currentGame.isOver() == false)
		{
			//invokes hit() method from TennisGame class
			
			currentGame.hit(fault, outOfBounds);
			
			//if calling the serve() method results in the end of the game, then assign wins to players according to who is currently serving and who won the game
			
			if (currentGame.isOver() == true)
			{
				if (currentGame.receiverWon() == true)
				{
					if (server == 0)
					{
						player1WinCount += 1;
					}
					if (server == 1)
					{
						player0WinCount += 1;
					}
				}
				if (currentGame.serverWon() == true)
				{
					if (server == 1)
					{
						player1WinCount += 1;
					}
					if (server == 0)
					{
						player0WinCount += 1;
					}
				}
			}
		}
	}
	
	/**
	 * Simulates a miss by the player the ball is headed towards and keeps track of games won provided the action of missing ends the current game
	 */
	
	public void miss()
	{
		//ensures that the game is not over and the set is not over
		
		if (isSetOver() == false && currentGame.isOver() == false)
		{
			//invokes miss() from TennisGame class
			
			currentGame.miss();
			if (currentGame.isOver() == true)
			{
				//if calling the miss() method results in the end of the game, then assign wins to players according to who is currently serving and who won the game
				
				if (currentGame.receiverWon() == true)
				{
					if (server == 0)
					{
						player1WinCount += 1;
					}
					if (server == 1)
					{
						player0WinCount += 1;
					}
				}
				if (currentGame.serverWon() == true)
				{
					if (server == 1)
					{
						player1WinCount += 1;
					}
					if (server == 0)
					{
						player0WinCount += 1;
					}
				}
			}
		}
	}
	
	/**
	 * Fast forwards the game to a future point with given scores for the server and receiver.
	 * @param serverScore how many points the server will have scored
	 * @param receiverScore how many points the receiver will have scored
	 */
	
	public void fastForward(int serverScore, int receiverScore)
	{
		//ensures that the game is not over and the set is not over
		
		if (currentGame.isOver() == false && isSetOver() == false)
		{
			//invokes miss() from TennisGame class
			
			currentGame.setScore(serverScore, receiverScore);
			if (currentGame.isOver() == true)
			{
				//if calling the miss() method results in the end of the game, then assign wins to players according to who is currently serving and who won the game
				
				if (currentGame.receiverWon() == true)
				{
					if (server == 0)
					{
						player1WinCount += 1;
					}
					if (server == 1)
					{
						player0WinCount += 1;
					}
				}
				if (currentGame.serverWon() == true)
				{
					if (server == 1)
					{
						player1WinCount += 1;
					}
					if (server == 0)
					{
						player0WinCount += 1;
					}
				}
			}
		}
	}
	
	/**
	 * Creates a new game provided the current game is over and the set is not over.
	 */
	
	public void newGame()
	{
		//ensures that the game is not over and the set is not over
		
		if (currentGame.isOver() == true && isSetOver() == false)
		{
			//sets a new game
			
			currentGame = new TennisGame();
			
			//switches server for the next game
			
			if (server == 0)
			{
				server = 1;
			}
			else
			{
				server = 0;
			}
		}
	}
	
	/**
	 * Checks whether or not the current tennis game is over
	 * @return true if the current game is over, false if not
	 */
	
	public boolean isCurrentGameOver()
	{
		//checks if the current game is over using the method from TennisGame
		
		if (currentGame.isOver() == true)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * Checks if the set is over using the required minimum games to win
	 * @return true if set is over, false if it is not
	 */
	
	public boolean isSetOver()
	{
		//determines if the set is completed according to the scoring rules
		
		if (player0WinCount >= minSetLength && player0WinCount - player1WinCount >= 2)
		{
			return true;
		}
		if (player1WinCount >= minSetLength && player1WinCount - player0WinCount >= 2)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * Returns the score of the game based on a series of rules dictating whether to return raw score or using the tennis scoring conventions
	 * @param useCallString says whether or not the string should return using tennis scoring conventions
	 * @return a string showing set score and current/last game score.
	 */
	
	public java.lang.String getCurrentStatus(boolean useCallString)
	{
		//checks if the return string should contain scores of form x-y or of tennis conventions form
		
		if (useCallString == false)
		{
			//since serving players score is put first, checks to see who is serving
			
			if (server == 0)
			{
				return ("Set: " + String.valueOf(player0WinCount) + "-" + String.valueOf(player1WinCount) + " Game: " + currentGame.getScore());
			}
			else
			{
				return ("Set: " + String.valueOf(player1WinCount) + "-" + String.valueOf(player0WinCount) + " Game: " + currentGame.getScore());
			}
		}
		else
		{
			//since serving players score is put first, checks to see who is serving
			
			if (server == 0)
			{
				return ("Set: " + String.valueOf(player0WinCount) + "-" + String.valueOf(player1WinCount) + " Game: " + currentGame.getCallString());
			}
			else
			{
				return ("Set: " + String.valueOf(player1WinCount) + "-" + String.valueOf(player0WinCount) + " Game: " + currentGame.getCallString());
			}
		}
	}
	
	/**
	 * Tests which player is serving the ball at the moment
	 * @return 0 if player0 is serving, 1 if player1 is serving
	 */
	
	public int whoIsServing()
	{
		//returns simple 0 or 1 to say who is serving using server instance variable
		
		return server;
		
	}
	
	/**
	 * Returns the number of games won by player0 in the set
	 * @return an integer value for number of games won by player0
	 */
	
	public int player0GamesWon()
	{
		//simply returns the number of games won by player0
		
		return player0WinCount;
		
	}
	
	/**
	 * Returns the number of games won by player1 in the set
	 * @return an integer value for number of games won by player1
	 */
	
	public int player1GamesWon()
	{
		//simply returns the number of games won by player1
		
		return player1WinCount;
		
	}
}
