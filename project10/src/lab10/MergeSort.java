package lab10;

public class MergeSort
{
  
  /**
   * Performs a recursive merge sort on the given array. This version
   * requires only 50% additional memory.
   * 
   * @param arr
   */
  public static void mergeSort(int[] arr)
  {
    mergeSort(arr, 0, arr.length - 1);
  }
  
  /**
   * Performs a recursive merge sort of the elements arr[start] through arr[end]
   */
  private static void mergeSort(int[] arr, int start, int end)
  {
    // Base case
    if (start >= end)
    {
      return;
    }
    
    int mid = (start + end) / 2;
    
    mergeSort(arr, start, mid);
    mergeSort(arr, mid + 1, end);
    merge(arr, start, end, mid);
  }
  
  /**
   * Merges two sorted subarrays of a given array, storing the result back in 
   * the given array.
   *   
   * Precondition: arr[start] through arr[mid] is already sorted
   *               arr[mid + 1] through arr[end] is already sorted
   * Postcondition: arr[start] through arr[end] is sorted.
   */
  static void merge(int[] arr, int start, int end, int mid)
  {
    
    // copy first half of arr to a temporary array
    int aSize = mid - start + 1;
    int[] a = new int[aSize];
    for (int i = 0; i < aSize; ++i)
    {
      a[i] = arr[i + start];
    }
    int[] b = arr;                // 'b' array is just the second half of arr
    int[] result = arr;           // result goes in original array arr
    
    int i = 0;                    // starting index in a
    int j = mid + 1;              // starting index in b
    final int iMax = aSize;       // 1 + max index in a
    final int jMax = end + 1;     // 1 + max index in b
    int k = start;                // index in result
    
    // Remaining code is identical to previous version MergeSort.merge()
    
    while (i < iMax && j < jMax)
    {
      if (a[i] <= b[j])
      {
        result[k] = a[i];
        i = i + 1;
        k = k + 1;
       }
      else
      {
        result[k] = b[j];
        j = j + 1;
        k = k + 1;
      }
    }
    
    // pick up any stragglers
    while (i < iMax)
    {
      result[k] = a[i];
      i = i + 1;
      k = k + 1;
    }
    while (j < jMax)
    {
      result[k] = b[j];
      j = j + 1;
      k = k + 1;
    }    
  }
}