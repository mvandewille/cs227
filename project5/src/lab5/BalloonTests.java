package lab5;

import balloon3.Balloon;

public class BalloonTests
{
	public static void main(String[] args)
	{
		Balloon b = new Balloon(10);
		
		//A newly constructed Balloon should have radius zero.
		System.out.println("Balloon radius is: " + b.getRadius());
		System.out.println("Expected: 0");
		
		//A newly constructed Balloon should not be popped.
		System.out.println("The balloon is popped: " + b.isPopped());
		System.out.println("Expected: false");
		
		//After calling blow(5) on a Balloon with maximum radius 10, the radius should be 5.
		b.blow(5);
		System.out.println("The balloon radius is: " + b.getRadius());
		System.out.println("Expected: 5");
		
		//After being deflated, the balloon radius should be back to 0
		b.deflate();
		System.out.println("The balloon radius is: " + b.getRadius());
		System.out.println("Expected: 0");
		
		//The balloon should not be able to be inflated past 10
		b.blow(12);
		System.out.println("The balloon radius is: " + b.getRadius());
		System.out.println("Expected: 0");
		System.out.println("The balloon is popped: " + b.isPopped());
		System.out.println("Expected: true");
		
		Balloon b2 = new Balloon(10);
		
		//After popping, the balloon should have radius 0 and should not be fillable again
		b2.pop();
		System.out.println("The balloon radius is: " + b2.getRadius());
		System.out.println("Expected: 0");
		b2.blow(10);
		System.out.println("The balloon radius is: " + b2.getRadius());
		System.out.println("Expected: 0");
		
		//And the balloon should realize it is popped
		System.out.println("The balloon is popped: " + b2.isPopped());
		System.out.println("Expected: true");
		
	}

}
