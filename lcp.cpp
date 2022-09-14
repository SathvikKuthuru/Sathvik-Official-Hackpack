#include <bits/stdc++.h>

using namespace std;

typedef long long ll;
typedef long double ld;
typedef vector<int> vi;
typedef vector<ll> vl;

#define all(x) (x).begin(), (x).end()
#define pb push_back
#define mp make_pair
#define f first
#define s second

const int MAXN = (int) (1e5 + 100);

int lg[MAXN+1];

string x;
int n;

void precomp() {
    lg[1] = 0;
    for(int i = 2; i <= MAXN; i++) lg[i] = lg[i / 2] + 1;
}

struct RMQ {
    vector<vector<int>> lift;
    int n, k;

    RMQ() {}

    RMQ(vector<int>& a) {
        n = a.size();
        k = lg[n] + 1;
        lift = vector<vi>(n + 10, vi(k + 1));

        for(int i = 0; i < n; i++) lift[i][0] = a[i];
        for(int j = 1; j <= k; j++) {
            for(int i = 0; i + (1 << j) <= n; i++) {
                lift[i][j] = min(lift[i][j-1], lift[i + (1 << (j - 1))][j - 1]);
            }
        }
    }

    int query(int l, int r) {
        int j = lg[r - l + 1];
        return min(lift[l][j], lift[r - (1 << j) + 1][j]);
    }
};


struct SuffixArray {
    vi suffixSorted;
    vi suffixPos;
    vi lcp;
    RMQ rmq;

    SuffixArray(string& x) {
        suffixSorted = suffix_array_construction(x);
        lcp = lcp_construction(x, suffixSorted);
        suffixPos = vi(x.length());

        for(int i = 0; i < x.length(); i++) suffixPos[suffixSorted[i]] = i;
        rmq = RMQ(lcp);
    }

    vector<int> sort_cyclic_shifts(string const& s) {
        int n = s.size();
        const int alphabet = 256;
        vector<int> p(n), c(n), cnt(max(alphabet, n), 0);
        for(int i = 0; i < n; i++) cnt[s[i]]++;
        for(int i = 1; i < alphabet; i++) cnt[i] += cnt[i-1];
        for(int i = 0; i < n; i++) p[--cnt[s[i]]] = i;
        c[p[0]] = 0;
        int classes = 1;
        for (int i = 1; i < n; i++) {
            if (s[p[i]] != s[p[i-1]])
                classes++;
            c[p[i]] = classes - 1;
        }

        vector<int> pn(n), cn(n);
        for (int h = 0; (1 << h) < n; ++h) {
            for (int i = 0; i < n; i++) {
                pn[i] = p[i] - (1 << h);
                if (pn[i] < 0)
                    pn[i] += n;
            }
            fill(cnt.begin(), cnt.begin() + classes, 0);
            for (int i = 0; i < n; i++)
                cnt[c[pn[i]]]++;
            for (int i = 1; i < classes; i++)
                cnt[i] += cnt[i-1];
            for (int i = n-1; i >= 0; i--)
                p[--cnt[c[pn[i]]]] = pn[i];
            cn[p[0]] = 0;
            classes = 1;
            for (int i = 1; i < n; i++) {
                pair<int, int> cur = {c[p[i]], c[(p[i] + (1 << h)) % n]};
                pair<int, int> prev = {c[p[i-1]], c[(p[i-1] + (1 << h)) % n]};
                if (cur != prev)
                    ++classes;
                cn[p[i]] = classes - 1;
            }
            c.swap(cn);
        }
        return p;
    }

    vector<int> suffix_array_construction(string s) {
        s += "$";
        vector<int> sorted_shifts = sort_cyclic_shifts(s);
        sorted_shifts.erase(sorted_shifts.begin());
        return sorted_shifts;
    }

    vector<int> lcp_construction(string const& s, vector<int> const& p) {
        int n = s.size();
        vector<int> rank(n, 0);
        for (int i = 0; i < n; i++)
            rank[p[i]] = i;

        int k = 0;
        vector<int> lcp(n-1, 0);
        for (int i = 0; i < n; i++) {
            if (rank[i] == n - 1) {
                k = 0;
                continue;
            }
            int j = p[rank[i] + 1];
            while (i + k < n && j + k < n && s[i+k] == s[j+k])
                k++;
            lcp[rank[i]] = k;
            if (k)
                k--;
        }
        return lcp;
    }

    int query(int i, int j) {
        if(i == j) return n - i;
        i = suffixPos[i];
        j = suffixPos[j];
        if(i > j) swap(i, j);

        return rmq.query(i, j - 1);
    }
};
