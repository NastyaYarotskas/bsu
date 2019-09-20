#define _CRC_SECURE_NO_WRNINGS
#include <iostream>
#include <vector>
#include <set>
#include <queue>
#include <ctime>
#include <algorithm>

std::vector<long long> dijkstra(int s, int n, std::vector<std::vector<std::pair<int, int> > > list){
    std::vector<long long> dist(n, LLONG_MAX);
    std::vector<char> used(n, 0);
    std::priority_queue<std::pair<long long, int> > q;
    
    q.push(std::make_pair(0, s));
    dist[s] = 0;
    
    while(!q.empty()){
        auto cur = q.top();
        q.pop();
        
        if(!used[cur.second]){
            used[cur.second] = true;
            dist[cur.second] = -cur.first;
            
            for(int i = 0; i < list[cur.second].size(); i++){
                int w = list[cur.second][i].first;
                long long cost = list[cur.second][i].second;
                if(!used[w]){
                    long long prior = dist[cur.second] + cost;
                    q.push(std::make_pair(-prior, w));
                }
            }
        }
        
    }
    
    return dist;
}

int main(int argc, const char * argv[]) {
    freopen("input.txt", "r", stdin);
    freopen("output.txt", "w", stdout);
    
    int n, m, x, y, z;
    std::cin >> n >> m;
    
    std::vector<std::vector<std::pair<int, int> > > list(n);
    
    for(int i = 0; i < m; i++){
        int a, b, k;
        std::cin >> a >> b >> k;
        list[a].push_back(std::make_pair(b, k));
        list[b].push_back(std::make_pair(a, k));
    }
    
    std::cin >> x >> y >> z;
    std::vector<long long> pathX = dijkstra(x, n, list);
    std::vector<long long> pathY = dijkstra(y, n, list);
    std::vector<long long> pathZ = dijkstra(z, n, list);
    
    long long min = LLONG_MAX;
    for(int i = 0; i < n; i++){
        min = std::min(min, pathX[i] + pathY[i] + pathZ[i]);
    }
    
    std::cout << min << "\n";    
    return 0;
}
