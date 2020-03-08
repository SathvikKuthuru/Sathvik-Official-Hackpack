import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class PursuitForArtifacts {
	public static void main(String[] args) {
		FastScanner scan=new FastScanner();
		int n=scan.nextInt(), m=scan.nextInt();
		a=new ArrayList[n];
		for(int i=0;i<n;i++) a[i]=new ArrayList<>();
		for(int i=0;i<m;i++) {
			int u=scan.nextInt()-1, v=scan.nextInt()-1, art=scan.nextInt();
			a[u].add(new edge(v,i,art));
			a[v].add(new edge(u,i,art));
		}
		pre=new int[n];
		low=new int[n];
		bridge=new boolean[m];
		Arrays.fill(pre,-1);
		
		dfs(0,-1);
		
		metaId=new int[n];
		Arrays.fill(metaId,-1);
		for(int i=0;i<n;i++) {
			if(metaId[i]==-1) metaaaaa(i);
			comp++;
		}
		meta=new ArrayList[comp];
		for(int i=0;i<comp;i++) meta[i]=new ArrayList<>();
		
		hasArt=new boolean[comp];
		
		for(int i=0;i<n;i++) {
			for(edge nxt:a[i]) {
				if(metaId[i]!=metaId[nxt.v]) {
					meta[metaId[i]].add(new edge(metaId[nxt.v],nxt.id,nxt.art));
					meta[metaId[nxt.v]].add(new edge(metaId[i],nxt.id,nxt.art));
				}
				else if(nxt.art==1) hasArt[metaId[nxt.v]]=true;
			}
		}
		//answer is YES in two situations
		//1. you encounter a node with an artifact
		//2. you encounter an edge with an artifact
		//(on your path from a to b)
		
		s=metaId[scan.nextInt()-1];
		e=metaId[scan.nextInt()-1];
		dfsagain(s,new boolean[comp],false);
		System.out.println(weGood?"YES":"NO");
	}
	static boolean weGood=false;
	public static void dfsagain(int at, boolean[] v, boolean found) {
		v[at]=true;
		boolean nxtF=found;
		nxtF|=hasArt[at];
		
		if(at==e&&nxtF) {
			weGood=true;
			return;
		}
		for(edge nxt:meta[at]) {
			if(v[nxt.v]) continue;
			dfsagain(nxt.v,v,nxtF||nxt.art==1);
		}
	}
	static int s,e;
	static int comp=0;
	public static void metaaaaa(int i) {
		metaId[i]=comp;
		for(edge nxt:a[i]) {
			if(metaId[nxt.v]==-1&&!bridge[nxt.id]) metaaaaa(nxt.v);
		}
	}
	static boolean[] hasArt;
	static int[] metaId;
	static boolean[] bridge;
	static int ct=0;
	public static void dfs(int at, int prev) {
		pre[at]=low[at]=ct++;
		
		for(edge e:a[at]) {
			if(e.v==prev) continue;
			if(pre[e.v]==-1) {//forward edge
				dfs(e.v,at);
				low[at]=Math.min(low[at],low[e.v]);
				if(low[e.v]>pre[at]) bridge[e.id]=true;
			}
			else {//back edge
				low[at]=Math.min(low[at],pre[e.v]);
			}
		}
	}
	static int[] pre,low;
	static ArrayList<edge>[] a,meta;
	
	static class edge {
		int v,id,art;
		edge(int v, int id, int art) {
			this.v=v;
			this.id=id;
			this.art=art;
		}
	}
	static class FastScanner {
		BufferedReader br;
		StringTokenizer st;

		public FastScanner() {
			try	{
				br = new BufferedReader(new InputStreamReader(System.in));
				st = new StringTokenizer(br.readLine());
			} catch (Exception e){e.printStackTrace();}
		}

		public String next() {
			if (st.hasMoreTokens())	return st.nextToken();
			try {st = new StringTokenizer(br.readLine());}
			catch (Exception e) {e.printStackTrace();}
			return st.nextToken();
		}

		public int nextInt() {return Integer.parseInt(next());}

		public long nextLong() {return Long.parseLong(next());}

		public double nextDouble() {return Double.parseDouble(next());}

		public String nextLine() {
			String line = "";
			if(st.hasMoreTokens()) line = st.nextToken();
			else try {return br.readLine();}catch(IOException e){e.printStackTrace();}
			while(st.hasMoreTokens()) line += " "+st.nextToken();
			return line;
		}
	}
}
