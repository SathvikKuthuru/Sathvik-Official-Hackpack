import java.util.Arrays;
import java.util.Scanner;

public class lisnlogn {
	public static void main(String[] args) {
		Scanner scan=new Scanner(System.in);
		int n=scan.nextInt();
		int[] a=new int[n];
		for(int i=0;i<n;i++) a[i]=scan.nextInt();
		System.out.println(lis(a));
	}
	public static int lis(int[] a) {
		//nlog(n) implementation of longest increasing subsequence, using binary search
		
		int n=a.length;
		int inf=Integer.MAX_VALUE/2-5;
		int[] insert=new int[n+1];
		
		Arrays.fill(insert,inf);
		insert[0]=-inf;
		int[] lis=new int[n];
		int res=0;
		
		for(int i=0;i<n;i++) {
			int idx=search(insert,a[i]);//always gets the best answer
			insert[idx]=a[i];
			lis[i]=idx;
			res=Math.max(res,lis[i]);
		}
		return res;
	}
	public static int search(int[] a, int x) {
		//returns smallest index such that a[i]>=x
		int res=-1;
		
		int n=a.length;
		int lo=0, hi=n-1;
		while(hi>=lo) {
			int mid=(lo+hi)/2;
			if(a[mid]>=x) {//we could be lower
				res=mid;
				hi=mid-1;
			}
			else lo=mid+1;
		}
		return res;
	}
}
