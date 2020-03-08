import java.util.PriorityQueue;
import java.util.Scanner;

public class areau {
	public static void main(String[] args) {
		Scanner scan=new Scanner(System.in);
		int n=scan.nextInt();
		
		SegmentTree st=new SegmentTree(30010);
		PriorityQueue<event> q=new PriorityQueue<>();
		
		for(int i=0;i<n;i++) {
			int x1=scan.nextInt(), y1=scan.nextInt(), x2=scan.nextInt(), y2=scan.nextInt();
			q.offer(new event(y1,y2,x1,true));
			q.offer(new event(y1,y2,x2,false));
		}
		int prevX=0;
		long res=0L;
		
		while(!q.isEmpty()) {
			event e=q.poll();
//			System.out.println(e.x+" "+e.y1+" "+e.y2+" "+e.open);
//			System.out.println(st.rangeNonZero());
			res+=st.rangeNonZero()*(e.x-prevX);
			int delta=e.open?1:-1;
			st.increment(e.y1+1,e.y2,delta);
			prevX=e.x;
		}
		System.out.println(res);
	}
	static class event implements Comparable<event> {
		int y1,y2,x;
		boolean open;
		
		event(int y1, int y2, int x, boolean open) {
			this.y1=y1;
			this.y2=y2;
			this.x=x;
			this.open=open;
		}
		@Override
		public int compareTo(event o) {
			return x-o.x;
		}
	}
	static class SegmentTree {
		int n;
		int[] lo, hi, tag, nonzero;
		
		SegmentTree(int n) {
			this.n=n;
			lo=new int[4*n+1];//low end of range for each node
			hi=new int[4*n+1];//high end of range for each node
			tag=new int[4*n+1];
			nonzero=new int[4*n+1];
			
			init(1,1,n);//init for root node and it is the entire range
		}
		
		void init(int i, int a, int b) {
			lo[i]=a;
			hi[i]=b;
			
			if(a==b) return;//you are at a leaf
			
			int m=(a+b)/2;
			init(2*i,a,m);//recur for left child
			init(2*i+1,m+1,b);//recur for right child
		}
		
		void increment(int a, int b, int val) {
			increment(1,a,b,val);
		}
		void increment(int i, int a, int b, int val) {
			if(hi[i]<a||lo[i]>b) return;//no cover
			
			if(lo[i]>=a&&hi[i]<=b) {//full cover
				tag[i]+=val;
			}
			
			//partial cover
			
			else {
				increment(2*i,a,b,val);
				increment(2*i+1,a,b,val);
			}
			
			if(tag[i]>0) {
				nonzero[i]=hi[i]-lo[i]+1;
			}
			else nonzero[i]=nonzero[2*i]+nonzero[2*i+1];
		}
		
		long rangeNonZero() {
			return nonzero[1];
		}
	}
}
