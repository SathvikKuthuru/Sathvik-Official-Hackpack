
public class segtreeminmaxsum {
	static class segtree {
		int n;
		int[] lo, hi, min, max, sum, delta;

		segtree(int n) {
			this.n=n;
			lo=new int[4*n+1];//low end of range for each node
			hi=new int[4*n+1];//high end of range for each node
			min=new int[4*n+1];
			max=new int[4*n+1];
			sum=new int[4*n+1];
			delta=new int[4*n+1];

			init(1,0,n-1);//init for root node and it is the entire range
		}

		void init(int i, int a, int b) {
			lo[i]=a;
			hi[i]=b;

			if(a==b) return;//you are at a leaf

			int m=(a+b)/2;
			init(2*i,a,m);//recur for left child
			init(2*i+1,m+1,b);//recur for right child
		}

		//Lazy propagation (push delta to children)
		void prop(int i) {
			delta[2*i]+=delta[i];
			delta[2*i+1]+=delta[i];
			delta[i]=0;
		}

		//update range minimum
		void update(int i) {
			min[i]=Math.min(min[2*i]+delta[2*i],min[2*i+1]+delta[2*i+1]);
			max[i]=Math.max(max[2*i]+delta[2*i],max[2*i+1]+delta[2*i+1]);
			
			sum[i]=sum[2*i]+((hi[2*i]-lo[2*i]+1)*delta[2*i]);
			sum[i]+=sum[2*i+1]+((hi[2*i+1]-lo[2*i+1]+1)*delta[2*i+1]);
		}
		void increment(int a, int b, int val) {
			increment(1,a,b,val);
		}
		void increment(int i, int a, int b, int val) {
			if(hi[i]<a||lo[i]>b) return;//no cover

			if(lo[i]>=a&&hi[i]<=b) {//full cover
				delta[i]+=val;
				return;
			}

			//partial cover
			prop(i);

			increment(2*i,a,b,val);
			increment(2*i+1,a,b,val);

			update(i);
		}

		int rangeMin(int i, int a, int b) {
			if(hi[i]<a||lo[i]>b) return Integer.MAX_VALUE;//no cover

			if(lo[i]>=a&&hi[i]<=b) {//full cover
				return min[i]+delta[i];
			}

			//partial cover

			prop(i);

			int left=rangeMin(2*i,a,b);
			int right=rangeMin(2*i+1,a,b);

			update(i);

			return Math.min(left,right);
		}

		int rangeMax(int i, int a, int b) {
			if(hi[i]<a||lo[i]>b) return Integer.MIN_VALUE;//no cover

			if(lo[i]>=a&&hi[i]<=b) {//full cover
				return max[i]+delta[i];
			}

			//partial cover

			prop(i);

			int left=rangeMax(2*i,a,b);
			int right=rangeMax(2*i+1,a,b);

			update(i);

			return Math.max(left,right);
		}

		int rangeSum(int i, int a, int b) {
			if(hi[i]<a||lo[i]>b) return 0;//no cover

			if(lo[i]>=a&&hi[i]<=b) {//full cover
				return sum[i]+delta[i]*(hi[i]-lo[i]+1);
			}

			//partial cover

			prop(i);

			int left=rangeSum(2*i,a,b);
			int right=rangeSum(2*i+1,a,b);

			update(i);

			return left+right;
		}
	}
}
