#include <iostream>
#include <vector>
#include <set>
#include <fstream>
#include <string>
#include <sstream>
#include <algorithm>

std::vector<std::vector<int> > list;
int g[100][100];
std::vector<int> path;
std::vector<int> bestColoring;

int n, minColor;

void dfs(int from, int to, int indPath, std::vector<int>& coloring, int maxColor){
    if(maxColor >= minColor)
        return;
    if(indPath == to){
        for(int i = from; i < to; i++)
            bestColoring[path[i]] = coloring[path[i]];
        minColor = maxColor;
        return;
    }
    std::vector<int> setColors(n + 1, 0);
    for(int i = 0; i < list[path[indPath]].size(); i++){
        int ind = coloring[list[path[indPath]][i]];
        setColors[ind] = 1;
    }
    for(int i = 1; i <= maxColor; i++){
        if(!setColors[i]){
            coloring[path[indPath]] = i;
            dfs(from, to, indPath + 1, coloring, std::max(maxColor, i + 1));
            coloring[path[indPath]] = 0;
        }
    }
}

void sortWithDegree(){
    std::vector<std::pair<int, int>> p;
    
    for(int i = 1; i < list.size(); i++){
        p.push_back(std::make_pair(list[i].size(), i));
    }
    
    std::sort(p.begin(), p.end(), [](auto& v1, auto& v2){if(v1.first == v2.first) return v1.second < v2.second;
    else return v1.first > v2.first;
    });
    
    for(int i = 1; i <= p.size(); i++)
        path[i] = p[i - 1].second;
}

void f(){
    std::vector<int> deg(n + 2, 0);
    path.resize(n + 2, 0);
    
    sortWithDegree();
    
    for(int from = 1, to = 2; to <= n + 1; to++){
        int best = to;
        for(int i = to; i <= n; i++){
            if(g[path[to - 1]][path[i]])
                deg[path[i]] += 1;
            if(deg[path[best]] < deg[path[i]])
                best = i;
        }
        int t = path[to];
        path[to] = path[best];
        path[best] = t;
        
        if(deg[path[to]] == 0){
            minColor = n + 3;
            std::vector<int> coloring(n + 3, 0);
            dfs(from, to, from, coloring, 1);
            from = to;
        }
    }
}


int main(int argc, const char * argv[]) {
    freopen("colgraph.in", "r", stdin);
    freopen("colgraph.out", "w", stdout);
    
    std::cin >> n;
    
    list.resize(n + 1);
    bestColoring.resize(n + 1);
    
    int a, b;
    while(std::cin >> a >> b){
        g[a][b] = 1;
        g[b][a] = 1;
        list[a].push_back(b);
        list[b].push_back(a);
    }
    
    f();
    
    int max = -1;
    for(int i = 1; i < n + 1; i++){
        if(max < bestColoring[i]) max = bestColoring[i];
    }
    std::cout << max << std::endl;
    
    for(int i = 1; i < n; i++)
        std::cout << bestColoring[i] << " ";
    std::cout << bestColoring[n] << std::endl;
    
    std::cout << clock() * 1000 / CLOCKS_PER_SEC << std::endl;
    return 0;
}

