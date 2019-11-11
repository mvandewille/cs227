package hw3;

import static api.CellState.*;

import java.util.ArrayList;

import api.Cell;
import api.CellState;
import api.Descriptor;
import api.Direction;
import api.PortalInfo;

/**
 * Utility class containing the key algorithms for moves in the
 * CS227Quell game.  This class is completely stateless.
 */
public class GameSupport
{
  public GameSupport()
  {
    // does nothing
  }
  
  /**
   * Shifts the player location in a cell sequence in which no movable blocks
   * are being moved.  That is, if the sequence contains any movable blocks at all,
   * they are all at the far right and cannot move further. The first cell
   * in the array contains the player and the last cell in the array
   * must be a boundary cell, as defined by the <code>CellState.isBoundary</code>
   * method.  The player moves only to the right (there is a <code>Direction</code>
   * parameter, but that is only used to detect whether the player lands on
   * spikes).
   * <p>
   * The player's new location will be one of the following:
   * <ul>
   * <li>the cell just before the first movable block in the array, if any, or
   * <li>the cell just before the boundary, or
   * <li>the boundary cell itself, in the case that the boundary is a cell
   * with spikes whose direction is opposite that of the given direction argument
   * (or is SPIKES_ALL)
   * </ul>
   * Any pearls in the sequence are disappeared and any open gates passed
   * by the player are closed by this method.
   * <p>
   * If the descriptors array is non-null, then each descriptor
   * is updated as follows:
   * <ul>
   * <li>The first descriptor's <code>moveTo</code> attribute is set to the
   * player's new index in the array
   * <li>All pearls are marked as <code>disappeared</code>
   * </ul>
   * The cell objects within each descriptor are not modified.
   * It should be assumed that the given descriptors array is <em>consistent</em>
   * with the cells array (allowing for prior movement of movable blocks
   * from a previous call to <code>shiftMovableBlocks</code>).
   * @param cells
   *   a valid cell sequence in which movable blocks, if any, are at the far right only
   * @param descriptors
   *   a parallel array of <code>Descriptors</code>, consistent with the cell sequence (possibly null)
   * @param dir
   *   direction of the move, for the purpose of determining whether spikes may be deadly
   */
  public void shiftPlayer(Cell[] cells, Descriptor[] descriptors, Direction dir) 
  {
	  //Setting up placeholder cells of state EMPTY and CLOSED_GATE to input at proper times in the updated cell array
	  Cell empty = new Cell(EMPTY);
	  Cell closedGate = new Cell(CLOSED_GATE);
	  
	  //Using a for loop to run through the given cell array
	  for (int i = 0; i < cells.length; i ++)
	  {
		  //Wiping the current player location
		  if (cells[i].isPlayerPresent())
		  {
			  cells[i].setPlayerPresent(false);
		  }
		  
		  //Checking if the cell is a boundary or movable block, since the movable blocks will have been moved over by shiftMovableBlocks
		  if (CellState.isBoundary(cells[i].getState(), false) || CellState.isMovable(cells[i].getState()))
		  {
			  //If the cell is a spike and the spikes are deadly to the player, set the player to be on that spike and update the descriptors accordingly
			  if (CellState.isSpikes(cells[i].getState()) && CellState.spikesAreDeadly(cells[i].getState(), dir))
			  {
				  //Since player will always be at index 0 for the getCellSequence arrays, descriptor at index 0 is updated
				  cells[i].setPlayerPresent(true);
				  descriptors[0].setMovedToIndex(i);
			  }
			  else
			  {
				  //If the boundary is spikes but they are not deadly, then the player is set to the cell just before the spikes and descriptor is updated accordingly
				  cells[i - 1].setPlayerPresent(true);
				  descriptors[0].setMovedToIndex(i - 1);
			  }
		  }
	  }

	  //Now that the player has been removed and updated to their new position, I will scan through starting at the beginning up until the new player location to remove pearls and fix open gates to closed. I do not want to remove pearls past the new player location since the player never got there, thus the while loop testing for playePresent
	  int k = 0;
	  while (!cells[k].isPlayerPresent())
	  {
		  //If the pearl is before the new player location, remove the pearl and update descriptor disappeared state to true
		  if (cells[k].getState() == PEARL)
		  {
			  cells[k] = empty;
			  descriptors[k].setDisappeared();
		  }
		  
		  //If the cell is an open gate that the player has moved through, close the gate but no change for the descriptor (There really should be like a closed flag to figure this out but I'm just going off of what was specified)
		  if (cells[k].getState() == OPEN_GATE)
		  {
			  cells[k] = closedGate;
		  }
		  k++;
	  }
  }
  
  /**
   * Shifts movable blocks in a cell sequence to the right, if any.  Adjacent movable blocks
   * with opposite parity are "merged" and removed.  The last cell in the array
   * must be a boundary cell, as defined by the <code>CellState.isBoundary</code>
   * method.  If a movable block moves over a pearl (whether or not the block is subsequently removed
   * due to merging with an adjacent block) then the pearl is also removed.
   * <p>
   * If the given array of descriptors is non-null, then it must have the same length as
   * the cell sequence and the ith descriptor must initially contain a <em>copy</em> of the ith cell
   * in the <code>cells</code> array.  When the method completes, all descriptors for movable
   * cells must be updated with the new index of the cell and flagged as disappeared if 
   * the the cells were merged and removed.  <em>Note that merging is done from the right.</em>  
   * For example, given a cell sequence represented by ".+-+#", the resulting cell sequence 
   * would be "...+#", but the descriptors would show positions 2 and 3 as having moved to 
   * index 4 and disappeared,  and position 1 as having moved to index 4.
   * @param cells
   *   given cell sequence
   * @param descriptors
   *   parallel array of <code>Descriptors</code> exactly matching the cell sequence, possibly null
   */
  public void shiftMovableBlocks(Cell[] cells, Descriptor[] descriptors)
  {
	  //Setting up placeholder cells for EMPTY, movable blocks of type negative and positive to assign appropriately
	  Cell empty = new Cell(EMPTY);
	  Cell posBlock = new Cell(MOVABLE_POS);
	  Cell negBlock = new Cell(MOVABLE_NEG);
	  
	  //Moving backwards through the array from the end index
	  for (int i = cells.length; i >= 0; i--)
	  {
		  //Assign int to index of right most movable cell
		  int movable = findRightmostMovableCell(cells, i);
		  
		  //Make sure there is a movable cell
		  if (movable != -1)
		  {
			  for (int j = 1; j < cells.length - movable; j++)
			  {
				  //Check if the block at the index to the right of the movable cell is a boundary with containsMovable flag set to true since we are looking at movable blocks
				  if (CellState.isBoundary(cells[movable + j].getState(), true))
				  {
					  //Creating new blocks at the new location of the block depending on its initial state as negative or positive and updating descriptor index
						  if (cells[movable].getState() == MOVABLE_POS)
						  {
							  cells[movable + j - 1] = posBlock;
							  descriptors[movable].setMovedToIndex(movable + j - 1);
						  }
						  if (cells[movable].getState() == MOVABLE_NEG)
						  {
							  cells[movable + j - 1] = negBlock;
							  descriptors[movable].setMovedToIndex(movable + j - 1);
						  }
						  //Removing the original location by copying over empty placeholder cell
						  cells[movable] = empty;
				  }
				  
				  //Check if a positive block is running into a negative block
				  if (cells[movable + j].getState() == MOVABLE_POS && cells[movable].getState() == MOVABLE_NEG)
				  {
					  //In which case remove both of the blocks, set the descriptor movedToIndex for the block moving into the other and set disappeared flag for both
					  cells[movable] = empty;
					  cells[movable + j] = empty;
					  descriptors[movable].setMovedToIndex(movable + j);
					  descriptors[movable].setDisappeared();
					  descriptors[movable + j].setDisappeared();  
				  }
				  
				//Check if a negative block is running into a positive block
				  if (cells[movable + j].getState() == MOVABLE_NEG && cells[movable].getState() == MOVABLE_POS)
				  {
					//In which case remove both of the blocks, set the descriptor movedToIndex for the block moving into the other and set disappeared flag for both
					  cells[movable] = empty;
					  cells[movable + j] = empty;
					  descriptors[movable].setMovedToIndex(movable + j);
					  descriptors[movable].setDisappeared();
					  descriptors[movable + j].setDisappeared();  
				  }
				  
				  //The above cases account for all cases of movable block ending points, thus now we remove pearls that the blocks went over or the pearls will be removed in shiftPlayer
				  else
				  {
					  if (cells[movable + j].getState() == PEARL)
					  {
						  cells[movable + j] = empty;
						  descriptors[movable + j].setDisappeared();
					  }
				  }
			  }
		  }
	  }
  }
  

  /**
   * Returns the index of the rightmost movable block that is at or
   * to the left of the given index <code>start</code>.  Returns -1 if
   * there is no movable block at <code>start</code> or to the left.
   * @param cells
   *   array of Cell objects
   * @param start
   *   starting index for searching
   * @return
   *   index of first movable block encountered when searching towards
   *   the left starting from the given starting index, or -1 if there
   *   is no such cell
   */
  public int findRightmostMovableCell(Cell[] cells, int start)
  {
	  //For loop starting at the given start and going until index 1
    for (int i = start; i > 0; i --)
    {
    	//Testing if the cells are movable blocks, starting at (start - 1) since we are looking left of the start value
    	if (cells[i - 1].getState() == CellState.MOVABLE_POS || cells[i - 1].getState() == CellState.MOVABLE_NEG)
    	{
    		return (i - 1);
    	}
    }
    
    //If no removable blocks found in the array, return -1
    return -1;
  }
}
