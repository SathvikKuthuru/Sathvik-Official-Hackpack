const int MAXN = (int) (2e5 + 10);

vi adj[MAXN];
int sub[MAXN];
bool rem[MAXN];
int n, q;

void dfs_sub(int at, int p) {
  sub[at] = 1;
  for(int x : adj[at]) {
    if(x == p || rem[x]) continue;
    dfs_sub(x, at);
    sub[at] += sub[x];
  }
}

int dfs_cent(int at, int p, int cnt) {
  for(int x : adj[at]) {
    if(x == p || rem[x]) continue;
    if(sub[at] * 2 > cnt) {
      return dfs_cent(x, at, cnt);
    }
  }
  return at;
}

void cent_decomp(int root) {
  dfs_sub(root, -1);
  int cent = dfs_cent(root, -1, sub[root]);
  // Do Stuff
  rem[cent] = true;
  for(int x : adj[cent]) {
    if(rem[x]) continue;
    cent_decomp(x);
  }
}
