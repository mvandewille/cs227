package lab7;

import java.util.Arrays;
import java.util.Scanner;
import java.util.Random;

public class ArrayExample3
{
  public static void main(String[] args)
  {
    String s = "3 5 7 9 12";
    int[] result = readNumbers(s);
    System.out.println(Arrays.toString(result));
    
    int[] numberArray = new int[] {0,1,4,-1,5,-34,48,92,-3,-77,0,44};
    System.out.println(Arrays.toString(getPositives(numberArray)));
    
    System.out.println(Arrays.toString(randomExperiment(10, 1000)));
  }
  
  public static int[] readNumbers(String text)
  {
    Scanner scanner = new Scanner(text);
    int count = 0;
    while (scanner.hasNextInt())
    {
      scanner.nextInt();
      count +=1;
    }
    
    int[] nums = new int[count];
    scanner = new Scanner(text);
    int index = 0;
    while (scanner.hasNextInt())
    {
      int num = scanner.nextInt();
      nums[index] = num;
      index += 1;
    }
    return nums;  
  }
  
  public static int[] getPositives(int[] num)
  {
	  int count = 0;
	  for (int i = 0; i <num.length; i++)
	  {
		  if (num[i] > 0)
		  {
			  count++;
		  }
	  }
	  int position = 0;
	  int[] positive = new int[count];
	  for(int j =0; j < num.length; j++)
	  {
		  if(num[j] > 0)
	      {
	        positive[position] = num[j];
	        position++;
	      }
	  }
	  return positive;
  }
  
  public static int[] randomExperiment(int max, int iters)
  {
	  {
		  int count[] = new int[max];
		  Random rd = new Random();
		  for(int i = 0; i < 1000; i++)
		  {
			  count[rd.nextInt(max)]++;
		  }
		  return count;
	  }
  }
}