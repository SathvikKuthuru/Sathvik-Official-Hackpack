#include <bits/stdc++.h>
using namespace std;

typedef long long ll;
typedef long double ld;

typedef pair<int, int> pi;
typedef pair<ll, ll> pl;
typedef pair<ld, ld> pd;

typedef vector<int> vi;
typedef vector<ll> vl;
typedef vector<ld> vd;
typedef vector<pi> vpi;
typedef vector<pl> vpl;
typedef vector<pd> vpd;

#define f first
#define s second
#define mp make_pair
#define pb push_back
#define lb lower_bound
#define ub upper_bound
#define all(x) x.begin(), x.end()
#define sz(x) (int)(x).size()

const int MAXN = (int) (2e5 + 100);
const int MAXB = 19;
const ll INF = (ll) (1e18);

int n;
vi adj[MAXN];
vi adj_vt[MAXN];
bool important[MAXN];
int st[MAXN], en[MAXN], dep[MAXN];
int lift[MAXN][MAXB + 1];
int dfs_time = 0;


int lg[MAXN];
void precomp() {
    lg[1] = 0;
    for(int i = 2; i < MAXN; i++) lg[i] = lg[i / 2] + 1;
}

struct RMQ {
    vector<vector<ll>> lift;
    int n, k;

    RMQ() {}

    RMQ(vector<ll>& a) {
        n = a.size();
        k = lg[n] + 1;
        lift = vector<vl>(n + 10, vl(k + 1));

        for(int i = 0; i < n; i++) lift[i][0] = a[i];
        for(int j = 1; j <= k; j++) {
            for(int i = 0; i + (1 << j) <= n; i++) {
                lift[i][j] = min(lift[i][j-1], lift[i + (1 << (j - 1))][j - 1]);
            }
        }
    }

    ll query(int l, int r) {
        int j = lg[r - l + 1];
        return min(lift[l][j], lift[r - (1 << j) + 1][j]);
    }
};

vi layer[MAXN];
ll cost[MAXN];
ll virtual_cost[MAXN];
ll dp[MAXN];
ll ans;

RMQ rmq;

void reset() {
    for(int i = 0; i <= n; i++) {
        layer[i].clear();
        ans = 0;
        dfs_time = 0;
        adj[i].clear();
        dep[i] = 0;
    }
}

void read() {
    cin >> n;
    vl temp(n);
    for(int i = 0; i < n; i++) {
        cin >> cost[i];
        temp[i] = cost[i];
    }
    rmq = RMQ(temp);

    for(int i = 0; i < n - 1; i++) {
        int a, b;
        cin >> a >> b;
        adj[a].pb(b);
        adj[b].pb(a);
    }

    ans = 0;
    dfs_time = 0;
}

void dfs(int at, int par) {
    lift[at][0] = par;
    for(int i = 1; i <= MAXB; i++) {
        lift[at][i] = lift[lift[at][i - 1]][i - 1];
    }

    st[at] = ++dfs_time;
    for(int x : adj[at]) {
        if(x != par) {
            dep[x] = dep[at] + 1;
            dfs(x, at);
        }
    }
    en[at] = dfs_time;
}

bool upper(int u, int v) {
    return st[u] <= st[v] && en[v] <= en[u];
}

int lca(int u, int v) {
    if(upper(u, v)) return u;
    if(upper(v, u)) return v;

    for(int i = MAXB; i >= 0; i--) {
        if(!upper(lift[u][i], v)) {
            u = lift[u][i];
        }
    }
    return lift[u][0];
}

void precompute_lca(int root) {
    dfs(root, root);
}

bool cmp(int u, int v) { return st[u] < st[v]; }


int virtual_tree(vi vert) {
    sort(all(vert), cmp);

    int k = sz(vert);
    for(int i = 0; i < k - 1; i++) {
        int new_vertex = lca(vert[i], vert[i + 1]);
        vert.pb(new_vertex);
    }

    sort(all(vert), cmp);
    vert.erase(unique(all(vert)), vert.end());

    for(int v : vert) {
        adj_vt[v].clear();
        dp[v] = -1;
    }

    vi stk;
    stk.pb(vert[0]);
    for(int i = 1; i < sz(vert); i++) {
        int u = vert[i];
        while(sz(stk) >= 2 && !upper(stk.back(), u)) {
            adj_vt[stk[sz(stk) - 2]].pb(stk.back());
            stk.pop_back();
        }

        stk.pb(u);
    }

    while(sz(stk) >= 2) {
        adj_vt[stk[sz(stk) - 2]].pb(stk.back());
        stk.pop_back();
    }

    return stk[0];
}

void get_virtual_cost(int at, int p, int l) {
    int diffPar = dep[at] - dep[p], diffLayer = l - dep[at];
    int left = diffLayer, right = left + max(0, diffPar - 1);
    virtual_cost[at] = rmq.query(left, right);

    for(int x : adj_vt[at]) {
        get_virtual_cost(x, at, l);
    }
}

ll go(int at) {
    if(dp[at] != -1) return dp[at];

    ll ret = virtual_cost[at];
    ll take_children = 0;
    for(int x : adj_vt[at]) {
        take_children += go(x);
    }
    if(!adj_vt[at].empty()) {
        ret = min(ret, take_children);
    }

    return dp[at] = ret;
}

ll solve(int root, int l) {
    get_virtual_cost(root, 1, l);
    return go(root);
}

void solve() {
    precompute_lca(1);
    for(int i = 1; i <= n; i++) {
        layer[dep[i]].pb(i);
    }

    for(int i = 0; i < MAXN; i++) {
        if(layer[i].empty()) break;
        
        vi vert(layer[i]);
        vert.pb(1);
        for(int x : vert) {
            important[x] = true;
        }

        int vt_root = virtual_tree(vert);
        ans += solve(vt_root, i);

        for(int x : vert) {
            important[x] = false;
        }
    }

    cout << ans << '\n';
}

int main() {
    cin.tie(0)->sync_with_stdio(0);

    precomp();

    int tc; cin >> tc;

    while(tc--) {
        read();
        solve();
        reset();
    }

    return 0;
}
