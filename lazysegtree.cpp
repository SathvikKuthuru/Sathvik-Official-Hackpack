const int MAXN = (int) (2e5 + 100);

struct V {
    ll sum = 0, lazy = 0;
    V(){}
};

V t[4 * MAXN];
int TYPE_ADD = 0, TYPE_QUERY = 1;

void add(int v, ll x) {
    t[v].sum += x;
    t[v].lazy += x;
}

void push(int v) {
    add(v * 2, t[v].lazy);
    add(v * 2 + 1, t[v].lazy);
    t[v].lazy = 0;
}

void pull(int v) {
    t[v].sum = min(t[v * 2].sum, t[v * 2 + 1].sum);
}

void rec(int v, int tl, int tr, int l, int r, int TYPE, ll&x) {
    if(r < tl || l > tr) return;
    if(l <= tl && tr <= r) {
        if(TYPE == TYPE_ADD) add(v, x);
        else x = min(x, t[v].sum);
        return;
    }
    push(v);
    int mid = (tl + tr) / 2;
    rec(v * 2, tl, mid, l, r, TYPE, x);
    rec(v * 2 + 1, mid + 1, tr, l, r, TYPE, x);
    pull(v);
}
