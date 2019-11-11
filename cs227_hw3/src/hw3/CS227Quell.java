package hw3;
import static api.CellState.MOVABLE_NEG;

import static api.CellState.MOVABLE_POS;
import static api.CellState.PEARL;
import static api.CellState.PORTAL;

import java.util.ArrayList;

import api.Cell;
import api.CellState;
import api.Descriptor;
import api.Direction;
import api.StringUtil;
import ui.ConsoleUI;

/**
 * Basic game state and operations for a simplified version of the video game 
 * "Quell".
 */
 
public class CS227Quell
{
  /**
   * Two-dimensional array of Cell objects representing the 
   * grid on which the game is played.
   */
	
  private Cell[][] grid;
  
  /**
   * Instance of GameSupport to be used in the move() algorithm.
   */
  
  private GameSupport support;

  /**
   * Integer to keep track of how many moves the player has made;
   */
  
  private int numMoves;
  
  /**
   * Boolean to keep track of the player's status as alive(true) or dead(false)
   */
  
  private boolean isAlive;
  
  /**
   * Variable used to keep track of the number of pearls at the very beginning 
   * of the game, initialized in the constructor by using countPearls() then the 
   * value is never changed, only used for conditionals
   */
 
  private int initialPearls;
  
  /**
   * Constructs a game from the given string description.  The conventions
   * for representing cell states as characters can be found in 
   * <code>StringUtil</code>.  
   * @param init
   *   string array describing initial cell states
   * @param support
   *   GameSupport instance to use in the <code>move</code> method
   */
  
  public CS227Quell(String[] init, GameSupport support)
  {
    grid = StringUtil.createFromStringArray(init);
    this.support = support;
    initialPearls = countPearls();
    numMoves = 0;
    isAlive = true;
    // TODO - any other initialization you need
  }
  
  
  /**
   * Returns the number of columns in the grid.
   * @return
   *   width of the grid
   */
  public int getColumns()
  {
    return grid[0].length;
  }
  
  
  /**
   * Returns the number of rows in the grid.
   * @return
   *   height of the grid
   */
  public int getRows()
  {
    return grid.length;
  }
  
  
  /**
   * Returns the cell at the given row and column.
   * @param row
   *   row index for the cell
   * @param col
   *   column index for the cell
   * @return
   *   cell at given row and column
   */
  public Cell getCell(int row, int col)
  {
    return grid[row][col];
  }
  
  
  /**
   * Returns the row index for the player's current location.
   * @return
   */
  public int getCurrentRow()
  {
	  //Initializing a local variable to store the int for player's row
	  int row = 0;
	  
	  //Using one for loop to run through the rows of the grid
	  for (int i = 0; i < grid.length; i++)
	  {
		  //Using a second for loop to run through each (row, column) grid within that row
		  for (int j = 0; j < grid[i].length; j++)
		  {
			  //Testing the cell at that (row, column) coordinate for the isPlayerPresent flag
			  if (grid[i][j].isPlayerPresent())
			  {
				  row = i;
			  }
		  }
	  }
	  return row;
  }
  
  
  /**
   * Returns the column index for the player's current location.
   * @return
   */
  public int getCurrentColumn()
  {
	  //Like the getCurrentRow() method, using nested for loop to run through rows and columns of 
	  //grid but this time keeping track of the column
	  int column = 0;
	  for (int i = 0; i < grid.length; i++)
	  {
		  for (int j = 0; j < grid[i].length; j++)
		  {
			  if (grid[i][j].isPlayerPresent())
			  {
				  column = j;
			  }
		  }
	  }
	  return column;
  }
  
  
  /**
   * Returns true if the game is over, false otherwise.  The game ends when all pearls
   * are removed from the grid or when the player lands on a cell with spikes.
   * @return
   *   true if the game is over, false otherwise
   */
  public boolean isOver()
  {
	  // Using nested for loop to run through the grid again
	  for (int i = 0; i < grid.length; i++)
	  {
		  for (int j = 0; j < grid[i].length; j++)
		  {
			  //Testing to see if the player is dead due to spikes
			  if (CellState.isSpikes(grid[i][j].getState()) && grid[i][j].isPlayerPresent())
			  {
				  //Updating value for isAlive for easier use in the won() method
				  isAlive = false;
				  return true;
			  }
		  }
	  }
	  //Verifying that all of the pearls have been collected
	  if (initialPearls - countPearls() == 0)
	  {
		  return true;
	  }
	  //Returning false if neither condition has been met, i.e. game is still going
	  return false;
  }
  
  
  /**
   * Returns true if the game is over and the player did not die on spikes.
   * @return
   */
  public boolean won()
  {
	  /*
	   * Checking whether or not the game is over and determining if the player is
	   * still alive, in which case the game is won 
	   */

	  if (isOver() && isAlive)
	  {
		  return true;
	  }
	  return false;
  }
  
  
  /**
   * Returns the current number of moves made in this game.
   * @return
   */
  public int getMoves()
  {
	  //Returning numMoves, which is updated as the number of player moves within the move() method
	  return numMoves;
  }
  
  
  /**
   * Returns the current score (number of pearls disappeared) for this game.
   * @return
   */
  public int getScore()
  {
	  /*
	   * Since the score is defined as number of pearls disappeared, simply subtract the current
	   * number of pearls from the initial value set up in the constructor
	   */

	  return initialPearls - countPearls();
  }
  
  
  /**
   * Performs a move along a cell sequence in the given direction, updating the score, 
   * the move count, and all affected cells in the grid.  The method returns an 
   * array of Descriptor objects representing the cells in original cell sequence before 
   * modification, with their <code>movedTo</code> and <code>disappeared</code>
   * status set to indicate the cells' new locations after modification.  
   * @param dir
   *   direction of the move
   * @return
   *   array of Descriptor objects describing modified cells
   */
  public Descriptor[] move(Direction dir)
  {
	  //Creating a new cell array in which to put the results from getCellSequence()
	  Cell[] moveSequence = new Cell[getCellSequence(dir).length];
	  moveSequence = getCellSequence(dir);
	  
	  //Creating a new descriptor array of same length as my cell array since they will be parallel
	  Descriptor[] parallelArray = new Descriptor[moveSequence.length];
	  
	  /*
	   * Using a for loop limited by the array length to assign the cell value in the descriptor to the 
	   * parallel cell in my cell array
	   */
	  for (int i = 0; i < moveSequence.length; i++)
	  {
		  parallelArray[i] = new Descriptor(moveSequence[i], i);
	  }
	  
	  //Using the initialized GameSupport support to call shiftMovableBlocks on my cell and descriptor arrays
	  support.shiftMovableBlocks(moveSequence, parallelArray);
	  
	  //Using the initialized GameSupport support to call shiftMovablePlayer on my cell and descriptor arrays
	  support.shiftPlayer(moveSequence, parallelArray, dir);
	  
	  //Utilizing helper method setCellSequence to essentially paste my updated cell array back into the grid
	  setCellSequence(moveSequence, dir);
	  
	  /*
	   * Updating the number of moves, since my score uses an initialPearls variable of fixed value and calls 
	   * the countPearls() method to update remaining pearls, there is no score value to update in this move() method
	   */
	  numMoves ++;
	  
	  //Returning descriptor array for use in the UI
	  return parallelArray;
  }
  
  
  /**
   * Finds a valid cell sequence in the given direction starting with the player's current 
   * position and ending with a boundary cell as defined by the method CellState.isBoundary. 
   * The actual cell locations are obtained by following getNextRow and getNextColumn in the 
   * given direction, and the sequence ends when a boundary cell is found. A boundary cell is 
   * defined by the CellState.isBoundary and is different depending on whether a movable block 
   * has been encountered so far in the cell sequence (the player can move through open gates 
   * and portals, but the movable blocks cannot). It can be assumed that there will eventually 
   * be a boundary cell (i.e., the grid has no infinite loops). The first element of the returned 
   * array is the cell containing the player, and the last element of the array is the boundary cell. 
   * This method does not modify the grid or any aspect of the game state.
   * @param dir
   * @return
   */
  public Cell[] getCellSequence(Direction dir)
  {
	  //Setting up a new ArrayList, since I am unsure how long my sequence will be initially, thus better to use list than primitive array
	  ArrayList<Cell> temp = new ArrayList<Cell>();
	  
	  //Declaring a boolean that will be used to account for doPortalJump to be true when going through a portal, but false when landing on the portal on the other side
	  boolean secondPortal = true;
	  
	  //Declaring checkRow and checkCol as current player location initially to know where to begin cell sequence
	  int checkRow = getCurrentRow();
	  int checkCol = getCurrentColumn();
	  
	  //Using a while loop since I am unsure of length, checking to see if the cell value being checked is a boundary, with false for containsMovable since the player that will move through the cell sequence is not a movable block
	  while (!CellState.isBoundary(grid[checkRow][checkCol].getState(), false))
	  {
		  //Adding the cell at my checked grid location to the array list
		  temp.add(grid[checkRow][checkCol]);
		  
		  //Setting the temporary row and column equal to the getNextRow and getNextColumn to continue moving along where the player would go around the grid
		  int tempRow = getNextRow(checkRow, checkCol, dir, secondPortal);
		  int tempCol = getNextColumn(checkRow, checkCol, dir, secondPortal);
		  
		  //Essentially if the cell I am on is a portal, I will go through it since secondPortal is declared as true to start, then on the other side of the portal I don't want to go back through, so I flip the value of the boolean
		  if(grid[checkRow][checkCol].getState() == PORTAL)
		  {
			  secondPortal = !secondPortal;  
		  }
		  
		  //Assigning actual values of checkRow and checkCol to the temporary values since I am no longer using the updated values in getNextRow or getNextColumn
		  checkRow = tempRow;
		  checkCol = tempCol;
	  }
	  
	  //Creating a normal cell array from my arraylist
	  Cell[] returnArr = temp.toArray(new Cell[temp.size()]);
	  return returnArr;
  }
  
  
  /**
   * Sets the given cell sequence and updates the player position. This method effectively retraces 
   * the steps for creating a cell sequence in the given direction, starting with the player's current 
   * position, and updates the grid with the new cells. Exactly one cell in the given sequence must have
   *  the condition isPlayerPresent true. The given cell sequence can be assumed to be structurally consistent
   *   with the existing grid, e.g., no portal or wall cells are moved.
   * @param cells
   * @param dir
   */
  public void setCellSequence(Cell[] cells, Direction dir)
  {
	  //Using the same logic as setCellSequence to determine the next cell in the grid that should be replaced by my inputed cell array
	  boolean portalJump = true;
	  
	  int nextRow = getCurrentRow();
	  int nextColumn = getCurrentColumn();
	  
	  //Unlike the getCellSequence, my length is limited by the inputed cell array thus I can use a for loop
	  for (int i = 0; i < cells.length; i++)
	  {
		  //Also unlike my getCellSequence I am no longer copying the value of the grid cell to an arraylist, I am copying the value of my cell array to the grid cell
		  grid[nextRow][nextColumn] = cells[i];
		  
		  int tempRow = getNextRow(nextRow, nextColumn, dir, portalJump);
		  int tempColumn = getNextColumn(nextRow, nextColumn, dir, portalJump);
		  
		  //Same logic for the portal here, where the boolean is flipped so it wont end up in a infinite portal loop
		  if(grid[nextRow][nextColumn].getState() == PORTAL)
		  {
			  portalJump = !portalJump;  
		  }
		  
		  nextRow = tempRow;
		  nextColumn = tempColumn;
	  }
	  //No return for this one, since it is void
  }
  
  
  /**
   * Helper method returns the next row for a cell sequence in the given direction, possibly wrapping around. 
   * If the flag doPortalJump is true, then the next row will be obtained by adding the cell's row offset. 
   * (Normally the caller will set this flag to true when first landing on a portal, but to false for the second 
   * portal of the pair.)
   * @param row
   * @param col
   * @param dir
   * @param doPortalJump
   * @return
   */
  public int getNextRow(int row, int col, Direction dir, boolean doPortalJump)
  {
	  //Initializing rowAfter variable to row to add on offset or up and down movement
	  int rowAfter = row;
	  
	  /*
	   * If I am on a portal and want to go through it, the only way to see my next location is to go through the portal, 
	   * effectively getting the offset and adding it
	   */
	  if (grid[row][col].getState() == PORTAL && doPortalJump)
	  {
		  rowAfter = rowAfter + grid[row][col].getRowOffset();
	  }
	  else
	  {
		  /*
		   * If the direction is up, my rowAfter may go to negative if I subtract 1, 
		   * thus by adding getColumns I can use mod to find my new location wrapped around the grid
		   */
		  if (dir == Direction.UP)
		  {
			  rowAfter = ((row - 1) + getRows()) % getRows();
		  }
		  
		  /*
		   * And if the direction is down, my rowAfter might exceed the limit of the array, thus 
		   * I use mod and the length of the array to find the excess as I wrap around
		   */
		  if (dir == Direction.DOWN)
		  {
			  rowAfter = (row + 1) % getRows();
		  }
	  }
	  return rowAfter;
  }
  
  
  /**
   * Helper method returns the next column for a cell sequence in the given direction, possibly wrapping around. 
   * If the flag doPortalJump is true, then the next column will be obtained by adding the cell's column offset. 
   * (Normally the caller will set this flag to true when first landing on a portal, but to false for the second
   * portal of the pair.)
   * @param row
   * @param col
   * @param dir
   * @param doPortalJump
   * @return
   */
  public int getNextColumn(int row, int col, Direction dir, boolean doPortalJump)
  {
	  //Initializing columnAfter variable to col to add on offset or left and right movement
	  int columnAfter = col;
	   
	  /*
	   * If I am on a portal and want to go through it, the only way to see my next location is to go through the portal, 
	   * effectively getting the offset and adding it
	   */
	  if (grid[row][col].getState() == PORTAL && doPortalJump)
	  {
		  columnAfter += grid[row][col].getColumnOffset();
	  }
	  //Assuming I am not on a portal
	  else
	  {
		  /*
		   * If the direction is left, my columnAfter may go to negative if I subtract 1, 
		   * thus by adding getColumns I can use mod to find my new location wrapped around the grid
		   */
		
		  if (dir == Direction.LEFT)
		  {
			  columnAfter = ((col - 1) + getColumns()) % getColumns();
		  }
		  
		  /*
		   * And if the direction is right, my columnAfter might exceed the limit of the array, thus 
		   * I use mod and the length of the array to find the excess as I wrap around
		   */
		  if (dir == Direction.RIGHT)
		  {
			  columnAfter = (col + 1) % getColumns();
		  }
	  }
	  return columnAfter;
  }
  
  
  /**
   * Returns the number of pearls left in the grid.
   * @return
   */
  public int countPearls()
  {
	  //Keeping track of a local variable as my return variable for the pearls remaining
	  int pearlCount = 0;
	  
	  //Running through the grid row by row and column by column using nested for loops
	  for (int i = 0; i < grid.length; i++)
	  {
		  for (int j = 0; j < grid[0].length; j++)
		  {
			  //Checking whether or not the cellstate of the cell at the grid coordinates is a pearl
			  if (grid[i][j].getState() == PEARL)
			  {
				  //If yes, adding pearl to total
				  pearlCount++;
			  }
		  }
	  }
	  return pearlCount;
  }
}
