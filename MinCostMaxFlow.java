
import java.util.*;


public class escape
{
   public static int fi(int rr, int cc)
   {
      return (cc * r + rr)<<1;
   }
   public static int se(int rr, int cc)
   {
      return ((cc * r + rr)<<1)|1;
   }
   
   public static int[] dx = {1,0,-1,0};
   public static int[] dy = {0,-1,0,1};
   public static int r, c;
   public static void main(String[] Args)
   {
      Scanner sc = new Scanner(System.in);
      r = sc.nextInt();
      c = sc.nextInt();
      MinCostFlow mcf = new MinCostFlow(2 * r * c, 2);
      
      // Read Grid
      char[][] grid = new char[r][c];
      for (int i = 0; i < r; i++)
      {
         String line = sc.next();
         for (int j = 0; j < c; j++)
         {
            grid[i][j] = line.charAt(j);
         }
      }
      
      // Make the empty cells
      for (int i = 0; i < r; i++)
      {
         for (int j = 0; j < c; j++)
         {
            if (grid[i][j] == '.')
            {
               mcf.add(fi(i,j), se(i,j), 1, 1);
            }
            else if (grid[i][j] == 'F')
            {
               mcf.add(fi(i,j), mcf.t, 2, 0);
            }
            else if (grid[i][j] == 'B' ||
               grid[i][j] == 'C')
            {
               mcf.add(mcf.s, se(i,j), 1, 0);
            }
            for (int k = 0; k < 4; k++)
            {
               int ii = i + dx[k];
               int jj = j + dy[k];
               if (ii >= 0 && jj >= 0 &&
                  ii < r && jj < c)
               {
                  mcf.add(se(i,j), fi(ii,jj), 1, 0);
               }
            }
         }
      }
      
      // get the flow
      long[] ret = mcf.getFlow();
      
      // Add the cost of moving into the car spot
      ret[1]+=2;
      
      // Check if both were able to make it to the car
      if (ret[0] != 2)
      {
         System.out.println(-1);
      }
      else
      {
         System.out.println(ret[1]);
      }
   }
   public static class MinCostFlow 
   {
      static int MINCOSTFLOW = 0, MINCOSTMAXFLOW = 1;
      int N, s, t, ss, tt;
      long oo = (long)1e12;
      long[] ex;
      ArrayList<Edge>[] adjj;
      Edge[][] adj;
      MinCostFlow(int NN, int flowType) 
      {
         N = (tt = (ss = (t = (s = NN) + 1) + 1) + 1) + 1;
         adj = new Edge[N][0];
         adjj = new ArrayList[N];
         for (int i = 0; i < N; i++) 
         {
            adjj[i] = new ArrayList<Edge>();
         }
         ex = new long[N];
         add(t, s, oo, -oo / 10 * flowType);
      }
      void add(int i, int j, long cap, long cost) 
      {
         Edge fwd = new Edge(i, j, cap, cost), rev = new Edge(j, i, 0, -cost);
         adjj[i].add(rev.rev = fwd); adjj[j].add(fwd.rev = rev);
      }
      long[] getFlow() 
      {
         preFlow();
         for (int i = 0; i < N; i++) 
         {
            adj[i] = adjj[i].toArray(adj[i]);
         }
         boolean[] canU = new boolean[N], hasU = new boolean[N];
         long[] d = new long[N], width = new long[N];
         Edge[] prev = new Edge[N];
         while (true) 
         {
            Arrays.fill(d, oo);
            d[ss] = 0; width[ss] = oo;
            boolean updated = hasU[ss] = true;
            while (updated) 
            {
               updated = false;
               for (int i = 0; i < N; hasU[i++] = false) 
               {
                  canU[i] = hasU[i];
               }
               for (int i = 0; i < N; i++)
               {
                  if (canU[i])
                  {
                     for (Edge e : adj[i])
                     {
                        if (e.flow != e.cap && d[e.j] > d[e.i] + e.cost) 
                        {
                           d[e.j] = d[e.i] + (prev[e.j] = e).cost;
                           width[e.j] = Math.min(width[e.i], e.cap - e.flow);
                           hasU[e.j] = updated = true;
                        }
                     }
                  }
               }
            }
            if (d[tt] >= oo) 
            {  
               break;
            }
            for (Edge e = prev[tt]; e != null; e = prev[e.i])
            {
               e.rev.flow = -(e.flow += width[tt]);
            }
         }
         long flow = 0, cost = 0;
         for (Edge e : adj[s]) 
         {
            if (e.flow > 0) 
            {
               flow += e.flow;
            }
         }
         for (int i = 0; i < N; i++) for (Edge e : adj[i])
         {
            if (e.flow > 0 && 
               e.i != t && e.j != s && 
               e.i < ss && e.j < ss)
            {
               cost += e.flow * e.cost;
            }
         }
         return new long[] { flow, cost };
      }
      void preFlow() 
      {
         for (int i = 0; i < N; i++)
         {
            for (Edge e : adjj[i])
            {
               if (e.cost < 0 && e.cap - e.flow > 0) 
               {
                  ex[e.i] -= e.cap - e.flow;
                  ex[e.j] += e.cap - e.flow;
                  e.rev.flow = -(e.flow = e.cap);
               }
            }
         }
         for (int i = 0; i < N; i++)
         {
            if (ex[i] > 0) 
            {
               add(ss, i, ex[i], -oo);
            }
            else if (ex[i] < 0)
            {
               add(i, tt, -ex[i], -oo);
            }
         }
         Arrays.fill(ex, 0);
      }
   }
   public static class Edge 
   {
      int i, j;
      long cap, flow, cost;
      Edge rev;
      Edge(int ii, int jj, long cc, long C) 
      {
         i = ii; j = jj; cap = cc; cost = C;
      }
   }
}
