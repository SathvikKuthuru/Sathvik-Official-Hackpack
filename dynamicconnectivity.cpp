#define all(x) begin(x), end(x)
struct dsu_save {
    int u, v, subU, subV;
    dsu_save() {}
    dsu_save(int _u, int _v, int _subU, int _subV)
        : u(_u), v(_v), subU(_subU), subV(_subV) {}
};

struct dsu_with_rollbacks {
    vector<int> p, sub;
    int comps;
    deque<dsu_save> op;

    dsu_with_rollbacks() {}

    dsu_with_rollbacks(int n) {
        p.resize(n);
		sub.resize(n);
		iota(all(p), 0);
		fill(all(sub), 1);
        comps = n;
    }

    int root(int v) {
        return (v == p[v]) ? v : root(p[v]);
    }

    bool unite(int u, int v) {
		u = root(u), v = root(v);
		if(u == v) return false;
		if(sub[u] < sub[v]) swap(u, v);
        op.push_back(dsu_save(u, v, sub[u], sub[v]));
        p[v] = u, sub[u] += sub[v];
        comps--;
        return true;
    }

    void rollback() {
        if(op.empty()) return;
        dsu_save x = op.back();
        op.pop_back();
		p[x.u] = x.u, p[x.v] = x.v;
		sub[x.u] = x.subU, sub[x.v] = x.subV;
        comps++;
    }
};

struct query {
    int v, u;
    bool united;
    query(int _v, int _u) : v(_v), u(_u) {}
};

struct QueryTree {
    vector<vector<query>> t;
    dsu_with_rollbacks dsu;
    int T;

    QueryTree() {}

    QueryTree(int _T, int n) : T(_T) {
        dsu = dsu_with_rollbacks(n);
        t.resize(4 * T + 4);
    }

    void add_to_tree(int v, int l, int r, int ul, int ur, query& q) {
        if (ul > ur)
            return;
        if (l == ul && r == ur) {
            t[v].push_back(q);
            return;
        }
        int mid = (l + r) / 2;
        add_to_tree(2 * v, l, mid, ul, min(ur, mid), q);
        add_to_tree(2 * v + 1, mid + 1, r, max(ul, mid + 1), ur, q);
    }

    void add_query(query q, int l, int r) {
        add_to_tree(1, 0, T - 1, l, r, q);
    }

    void dfs(int v, int l, int r, vector<int>& ans) {
        for (query& q : t[v]) {
            q.united = dsu.unite(q.v, q.u);
        }
        if (l == r)
            ans[l] = dsu.comps;
        else {
            int mid = (l + r) / 2;
            dfs(2 * v, l, mid, ans);
            dfs(2 * v + 1, mid + 1, r, ans);
        }
        for (query q : t[v]) {
            if (q.united)
                dsu.rollback();
        }
    }

    vector<int> solve() {
        vector<int> ans(T);
        dfs(1, 0, T - 1, ans);
        return ans;
    }
};
