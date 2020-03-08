import java.util.Arrays; 
import java.util.Comparator; 
  
// Class to represent a job 
class Job 
{ 
    int start, finish, profit; 
  
    // Constructor 
    Job(int start, int finish, int profit) 
    { 
        this.start = start; 
        this.finish = finish; 
        this.profit = profit; 
    } 
} 
  
// Used to sort job according to finish times 
class JobComparator implements Comparator<Job> 
{ 
    public int compare(Job a, Job b) 
    { 
        return a.finish < b.finish ? -1 : a.finish == b.finish ? 0 : 1; 
    } 
} 
  
public class WeightedIntervalScheduling 
{ 
    /* A Binary Search based function to find the latest job 
      (before current job) that doesn't conflict with current 
      job.  "index" is index of the current job.  This function 
      returns -1 if all jobs before index conflict with it. 
      The array jobs[] is sorted in increasing order of finish 
      time. */
    static public int binarySearch(Job jobs[], int index) 
    { 
        // Initialize 'lo' and 'hi' for Binary Search 
        int lo = 0, hi = index - 1; 
  
        // Perform binary Search iteratively 
        while (lo <= hi) 
        { 
            int mid = (lo + hi) / 2; 
            if (jobs[mid].finish <= jobs[index].start) 
            { 
                if (jobs[mid + 1].finish <= jobs[index].start) 
                    lo = mid + 1; 
                else
                    return mid; 
            } 
            else
                hi = mid - 1; 
        } 
  
        return -1; 
    } 
  
    // The main function that returns the maximum possible 
    // profit from given array of jobs 
    static public int schedule(Job jobs[]) 
    { 
        // Sort jobs according to finish time 
        Arrays.sort(jobs, new JobComparator()); 
  
        // Create an array to store solutions of subproblems. 
        // table[i] stores the profit for jobs till jobs[i] 
        // (including jobs[i]) 
        int n = jobs.length; 
        int table[] = new int[n]; 
        table[0] = jobs[0].profit; 
  
        // Fill entries in M[] using recursive property 
        for (int i=1; i<n; i++) 
        { 
            // Find profit including the current job 
            int inclProf = jobs[i].profit; 
            int l = binarySearch(jobs, i); 
            if (l != -1) 
                inclProf += table[l]; 
  
            // Store maximum of including and excluding 
            table[i] = Math.max(inclProf, table[i-1]); 
        } 
  
        return table[n-1]; 
    } 
  
    // Driver method to test above 
    public static void main(String[] args) 
    { 
        Job jobs[] = {new Job(1, 2, 50), new Job(3, 5, 20), 
                      new Job(6, 19, 100), new Job(2, 100, 200)}; 
  
        System.out.println("Optimal profit is " + schedule(jobs)); 
    } 
} 
