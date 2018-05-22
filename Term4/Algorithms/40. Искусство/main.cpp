#include <iostream>
#include <vector>
#include <functional>
#include <algorithm>
#include <stack>
#include <list>
#include <cstdio>

int* col;
std::stack<int> st;

void dfs(int v, const std::vector<std::list<int>>& list){
    if(col[v] == 1) return;
    if(col[v] == 2) return;
    col[v] = 1;
    for(auto it = list[v].begin(); it != list[v].end(); it++){
        dfs(*it, list);
    }
    
    st.push(v);
    col[v] = 2;
}

void top(const std::vector<std::list<int>>& list, int n, int count, const std::vector<std::list<int>>& list2){
    for(int i = 1; i < n + 1; i++){
        dfs(i, list);
    }
    
    int* ans = new int[n + 1];
    int* v = new int[n + 1];
    for(int i = 1; i < n + 1; i++){
        ans[i] = st.top();
        v[st.top()] = i;
        st.pop();
    }
    
    for(int i = 1; i < n + 1; i++){
        for(auto it = list2[i].begin(); it != list2[i].end(); it++){
            if(v[i] < v[*it]) count++;
        }
    }
    
    printf("%d\n", count);
    for(int i = 1; i < n + 1; i++){
        printf("%d ", ans[i]);
    }
    printf("\n");
}

int main(int argc, const char * argv[]) {
    std::ios::sync_with_stdio(false);
    freopen("input.txt", "r", stdin);
    freopen("output.txt", "w", stdout);
    
    int n, m;
    scanf("%d %d", &n, &m);
    
    std::vector<std::list<int>> list(n + 1);
    std::vector<std::list<int>> list1(n + 1);
    
    col = new int[n + 1];
    
    int a, b, c, d1 = 0, d2 = 0;
    for(int i = 0; i < m; i++){
        scanf("%d %d %d", &a, &b, &c);
        if(c == 1){
            if(a > b){
                d2++;
                list1[a].push_back(b);
            } else {
                d1++;
                list[a].push_back(b);
            }
        } else {
            if(a > b){
                d1++;
                list[b].push_back(a);
            } else {
                d2++;
                list1[b].push_back(a);
            }
            
        }
    }
    
    if(d1 > d2){
        top(list, n, d1, list1);
    } else {
        top(list1, n, d2, list);
    }
    
    return 0;
}
