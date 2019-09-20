#include <iostream>
#include <vector>
#include <cmath>
#include <algorithm>
#include <functional>
#include <queue>
#include <tuple>

int n, m, s, t;

int INF = INT_MAX;
int dist[110];
int parent[110];
int phi[110];
std::priority_queue<std::tuple<int, int, int>> q;

struct edge{
    int from, to;
    int flow, cap;
    int cost;
    
    edge(int from, int to, int cost, int cap, int flow){
        this->from = from;
        this->to = to;
        this->cost = cost;
        this->cap = cap;
        this->flow = flow;
    }
};

std::vector<edge> e;
std::vector<std::vector<int>> g;
std::pair<int, int> data[101][101];
int edgeNumber;

void addEdge(int from, int to, int cost, int cap){
    edge temp(from, to, cost, cap, cap);
    g[from].push_back(edgeNumber);
    e.push_back(temp);

    edge temp2(to, from, -cost, cap, 0);
    g[to].push_back(edgeNumber + 1);
    e.push_back(temp2);

    edgeNumber += 2;
}

void dijkstra(){
    std::fill(dist, dist + n + 1, INF);
    std::vector<char> used(n + 1, 0);
    dist[s] = 0;
    q.push(std::make_tuple(0, s, -1));

    while(!q.empty()){
        auto cur = q.top();
        q.pop();
        
        int ver = std::get<1>(cur);
        
        if(!used[ver]){
            used[ver] = true;
            dist[ver] = -std::get<0>(cur);
            parent[ver] = std::get<2>(cur);
            
            for(int i = 0; i < g[ver].size(); i++){
                int ind = g[ver][i];
                if(e[ind].flow == 0) continue;

                int to = e[ind].to;
                int w = e[ind].cost + phi[ver] - phi[to];
                if(!used[to]){
                    int prior = dist[ver] + w;
                    q.push(std::make_tuple(-prior, to, ind));
                }
            }
        }
    }
}

std::pair<int, int> minCostMaxFlow(int flow){
    int cost = 0;
    int flowRes = 0;

    while(true){

        dijkstra();

        if(dist[t] == INF) {
            return std::make_pair(cost, flowRes);
        }

        for(int i = s; i <= n; i++) {
            if(dist[i] == INF) continue;
            phi[i] += dist[i];
        }

        int augFlow = flow;
       
        for(int cur = t; cur != s; cur = e[parent[cur]].from){
            augFlow = std::min(augFlow, e[parent[cur]].flow);
        }

        flowRes += augFlow;
        flow -= augFlow;
        
        for(int cur = t; cur != s; cur = e[parent[cur]].from){
            e[parent[cur]].flow -= augFlow;
            e[parent[cur] ^ 1].flow += augFlow;
            cost += (float)(augFlow * e[parent[cur]].cost);
        }

        if(flow == 0) break;

    }

    return std::make_pair(cost, flowRes);
}


int main(int argc, const char * argv[]) {
    std::ios_base::sync_with_stdio(false);
    freopen("input.txt", "r", stdin);
    freopen("output.txt", "w", stdout);

    std::cin >> n >> m >> s >> t;

    g.resize(n + 1);

    int from, to, cap, cost;
    for(int i = 0; i < m; i++){
        std::cin >> from >> to >> cap >> cost;
        addEdge(from, to, cost, cap);
        data[from][to] = std::make_pair(cap, cost);
    }

    auto res = minCostMaxFlow(INT_MAX);

    g.clear();
    g.resize(n + 1);
    edgeNumber = 0;
    e.clear();

    while(std::cin >> from >> to){
        addEdge(from, to, data[from][to].second, data[from][to].first);
    }

    auto res2 = minCostMaxFlow(INT_MAX);

    if(res == res2){
        std::cout << "Yes" << std::endl;
        std::cout << res.second << std::endl;
        std::cout << res.first << std::endl;
    }
    else{
        std::cout << "No" << std::endl;
        std::cout << res.second << std::endl;
        std::cout << res.first << std::endl;
        std::cout << res2.second << std::endl;
        std::cout << res2.first << std::endl;
    }

    return 0;
}



