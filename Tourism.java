import java.util.*;

/*
Alex decided to go on a touristic trip over the country.
For simplicity let's assume that the country has n cities and m bidirectional roads connecting them. Alex lives in city s and initially located in it.
To compare different cities Alex assigned each city a score wi which is as high as interesting city seems to Alex.
Alex believes that his trip will be interesting only if he will not use any road twice in a row.
That is if Alex came to city v from city u, he may choose as the next city in the trip any city connected with v by the road, except for the city u.
Your task is to help Alex plan his city in a way that
maximizes total score over all cities he visited. Note that for each city its score is counted at most once,
even if Alex been there several times during his trip.

Input
First line of input contains two integers n and m, (1≤n≤2⋅105, 0≤m≤2⋅105) which are numbers of cities and roads in the country.
Second line contains n integers w1,w2,…,wn (0≤wi≤109) which are scores of all cities.
The following m lines contain description of the roads. Each of these m lines contains
two integers u and v (1≤u,v≤n) which are cities connected by this road.
It is guaranteed that there is at most one direct road between any two cities,
no city is connected to itself by the road and, finally, it is possible to go from any city to any other one using only roads.
The last line contains single integer s (1≤s≤n), which is the number of the initial city.

Output
Output single integer which is the maximum possible sum of scores of visited cities.

Example:
5 7
2 2 8 6 9
1 2
1 3
2 4
3 2
4 5
2 5
1 5
2


27

 */

public class Tourism {
    static int n, m;
    static long[] weight;
    static long[] metaWeight;
    static boolean[] visited;
    static int[] disc, low, parent;
    static int[] component, size;
    static boolean[] comeBack;

    static ArrayList<Integer>[] graph;
    static ArrayList<Integer>[] metaGraph;
    static HashSet<Integer>[] not;

    static int time = 0;

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        n = scan.nextInt();
        m = scan.nextInt();
        graph = new ArrayList[n];
        weight = new long[n];
        visited = new boolean[n];
        disc = new int[n];
        low = new int[n];
        parent = new int[n];
        not = new HashSet[n];
        component = new int[n];

        for(int i = 0; i < n; i++) {
            graph[i] = new ArrayList<Integer>();
            not[i] = new HashSet<Integer>();
            weight[i] = scan.nextLong();
            parent[i] = -1;
            component[i] = -1;
        }
        for(int i = 0; i < m; i++) {
            int a = scan.nextInt()-1, b = scan.nextInt()-1;
            graph[a].add(b);
            graph[b].add(a);
        }
        for(int i = 0; i < n; i++) {
            if(!visited[i]) {
                getBridges(i);
            }
        }
        int currentId = 0;
        for(int i = 0 ; i < n; i++) {
            if(component[i] == -1) addMetaNode(i, currentId++);
        }
        metaWeight = new long[currentId];
        metaGraph = new ArrayList[currentId];
        size = new int[currentId];
        comeBack = new boolean[currentId];
        for(int i = 0; i < n; i++) {
            metaWeight[component[i]] += weight[i];
            size[component[i]]++;
        }
        for(int i = 0; i < currentId; i++) metaGraph[i] = new ArrayList<>();
        for(int i = 0; i < n; i++) {
            for(int j : not[i]) {
                metaGraph[component[i]].add(component[j]);
            }
        }
        int from = scan.nextInt()-1;
        comeBack[component[from]] = true;
        long ans = dfs(-1, component[from]);
        long maxTerminalWeight = 0;
        for(int i = 0; i < currentId; i++) {
            if(comeBack[i]) {
                maxTerminalWeight = Math.max(maxTerminalWeight, getTerminal(-1, i));
            }
        }
        ans += maxTerminalWeight;
        System.out.println(ans);
    }

    static long dfs(int p, int at) {
        long total = 0;
        if(size[at] > 1) comeBack[at] = true;
        for(int i : metaGraph[at]) {
            if(i == p) continue;
            long get = dfs(at, i);
            if(comeBack[i]) {
                comeBack[at] = true;
                total += get;
            }
        }
        if(comeBack[at]) total += metaWeight[at];
        return total;
    }

    static long getTerminal(int p, int at) {
        long best = 0;
        for(int i : metaGraph[at]) {
            if(i == p || comeBack[i]) continue;
            best = Math.max(best, getTerminal(at, i));
        }
        return comeBack[at] ? best : best + metaWeight[at];
    }

    static void addMetaNode(int u, int id) {
        component[u] = id;
        for(int v : graph[u]) {
            if(component[v] == -1 && !not[u].contains(v)) {
                addMetaNode(v, id);
            }
        }
    }

    static void getBridges(int u) {
        visited[u] = true;
        disc[u] = low[u] = time++;
        for(int v : graph[u]) {
            if(!visited[v]) {
                parent[v] = u;
                getBridges(v);
                low[u] = Math.min(low[u], low[v]);
                if(low[v] > disc[u]) {
                    not[v].add(u);
                    not[u].add(v);
                }
            }
            else if(v != parent[u]) {
                low[u] = Math.min(low[u], disc[v]);
            }
        }
    }
}
