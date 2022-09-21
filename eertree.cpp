const int ALPHA = 26;

struct node {
    int link;
    vi next;    
    int from, len;
    ll occ;

    node(int f, int l) {
        link = -1;
        from = f;
        len = l;
        occ = 0;
        next.resize(ALPHA, -1);
    }
};

string x;
vector<node> t;
int curr;

void init() {
    t.pb(node(-1, -1));
    t.pb(node(-1, 0));
    t[0].link = 0;
    t[1].link = 0;
    curr = 1;
}

int go(int at, int index) {
    if(at == 0) return at;
    int before = index - t[at].len - 1;
    if(before >= 0 && x[before] == x[index]) return at;

    return go(t[at].link, index);
}

int create(int curr, int to) {
    if(t[curr].next[to] != -1) return t[curr].next[to];
    t[curr].next[to] = t.size();
    t.pb(node(curr, t[curr].len + 2));

    return t.size() - 1;
}

void addIndex(int index) {
    curr = go(curr, index);
    int to = x[index] - 'a';

    curr = create(curr, to);
    if(t[curr].len == 1) t[curr].link = 1;
    if(t[curr].link == -1) {
        t[curr].link = create(go(t[t[curr].from].link, index), to);
    }
    t[curr].occ++;
}
