#define _CRC_SECURE_NO_WRNINGS
#include <iostream>
#include <vector>
#include <string>
#include <fstream>
#include <sstream>
#include <cmath>
#include <ctime>
#include <algorithm>
#include <functional>
#include <queue>
#include <set>

int n, m, s, t;

float INF = 1000000000;

struct edge{
    int from, to;
    int flow, cap;
    float cost;
};

std::vector<edge> e;
float** cost;
float* dist;
int* parent;
float* phi;
std::vector<std::vector<int>> g;
int edgeNumber;
std::priority_queue<std::pair<float, int> > q;
std::vector<int> ages1;
std::vector<int> ages2;
std::set<int> set1;
std::set<int> set2;
int cap1[61] = {0};
int cap2[61] = {0};

void addEdge(int from, int to, int cap, float cost){
    edge temp;
    temp.from = from; temp.to = to; temp.flow = 0; temp.cap = cap; temp.cost = cost;
    e.push_back(temp);
    g[from].push_back(edgeNumber);
    
    temp.from = to; temp.to = from; temp.flow = cap; temp.cap = cap; temp.cost = -cost;
    e.push_back(temp);
    g[to].push_back(edgeNumber + 1);
    
    edgeNumber += 2;
}

void dijkstra(){
    std::fill(dist, dist + t + 1, INF);

    dist[s] = 0;
    q.push(std::make_pair(0, s));

    while(!q.empty()){
        int cur = q.top().second;
        float cur_dist = -q.top().first;
        q.pop();
        if(cur_dist > dist[cur]) continue;
        if(dist[cur] == INF) break;

        for(int i = 0; i < g[cur].size(); i++){
            int ind = g[cur][i];
            if(e[ind].flow == e[ind].cap) continue;
            int to = e[ind].to;
            float w = e[ind].cost + phi[cur] - phi[to];
            if(cur_dist + w < dist[to]){
                dist[to] = cur_dist + w;
                parent[to] = ind;
                q.push(std::make_pair(-dist[to], to));
            }
        }
    }
}

float minCostMaxFlow(int flow){
    float result = 0;
    
    dijkstra();
    
    for(int i = s; i <= t; i++){
        phi[i] = dist[i];
    }
    
    while(true){
        
        dijkstra();
        
        if(dist[t] == INF) return result;
        
        for(int i = s; i <= t; i++) {
            phi[i] = std::min(phi[i] + dist[i], INF);
        }
        
        int augFlow = flow;
        int cur = t;
        while(cur != s){
            edge temp = e[parent[cur]];
            augFlow = std::min(augFlow, temp.cap - temp.flow);
            int from = temp.from;
            cur = from;
        }
        
        flow -= augFlow;
        cur = t;
        while(cur != s){
            edge temp = e[parent[cur]];            
            e[parent[cur]].flow += augFlow;
            e[parent[cur] ^ 1].flow -= augFlow;
            result += (float)(augFlow * temp.cost);
            cur = temp.from;
        }
        
        if(flow == 0) break;
        
    }
    
    return result;
}

void readGraph(){
    std::ifstream file("input.txt", std::ios::in);
    bool f = true;
    
    std::string str;
    while(getline(file, str)) {
        std::istringstream ss(str);
        int num;
        if(f){
            while(ss >> num) {
                ages1.push_back(num);
                set1.insert(num);
                cap1[num] += 1;
                
            }
            f = false;
        }
        else{
            while(ss >> num){
                ages2.push_back(num);
                set2.insert(num);
                cap2[num] += 1;
                
            }
        }
    }
    
    if(ages1.size() != ages2.size()){
        if(ages1.size() > ages2.size()){
            int temp = (int)(ages1.size() - ages2.size());
            for(int i = 0; i < temp; i++){
                ages2.push_back(0);
                set2.insert(0);
                cap2[0] += 1;
            }
            
            
        }else{
            int temp = (int)(ages2.size() - ages1.size());
            for(int i = 0; i < temp; i++){
                ages1.push_back(0);
                set1.insert(0);
                cap1[0] += 1;
            }
            
        }
    }
    
    n = (int)set1.size();
    ages1.resize(n);
    for(int i = 0; i < n; i++){
        ages1[i] = *set1.begin();
        set1.erase(ages1[i]);
    }
    
    m = (int)set2.size();
    ages2.resize(m);
    for(int i = 0; i < m; i++){
        ages2[i] = *set2.begin();
        set2.erase(ages2[i]);
    }
    
    cost = new float*[n + 2];
    for(int i = 0; i < n + 2; i++){
        cost[i] = new float[m + 2];
    }
    
    std::sort(ages1.begin(), ages1.end());
    std::sort(ages2.begin(), ages2.end());
    
    for(int i = 1; i <= n; i++){
        int a = ages1[i - 1];
        for(int j = 1; j <= m; j++){
            int b = ages2[j - 1];
            if(a != 0 && b != 0) cost[i][j] = abs(a - b);
            else if(a == 0) cost[i][j] = (float)b/2;
            else if(b == 0) cost[i][j] = (float)a/2;
        }
    }
}

int main(){
    std::ios_base::sync_with_stdio(false);
    freopen("input.txt", "r", stdin);
    freopen("output.txt", "w", stdout);
    
    readGraph();
    g.resize(n + m + 2);
    dist = new float[n + m + 2];
    parent = new int[n + m + 2];
    phi = new float[n + m + 2];
    
    s = 0; t = n + m + 1;
    edgeNumber = 0;
    
    for(int i = 1; i <= n; i++){
        addEdge(s, i, cap1[ages1[i - 1]], 0);
    }
    
    for(int i = n + 1; i <= n + m; i++){
        addEdge(i, t, cap2[ages2[i - 1 - n]], 0);
    }
    
    for(int i = 1; i <= n; i++){
        for(int j = 1; j <= m; j++){
            addEdge(i, n + j, cap1[ages1[i - 1]], cost[i][j]);
        }
    }
    
    std::cout << std::fixed;
    std::cout.precision(1);
    std::cout << minCostMaxFlow(INT_MAX) << "\n";
    return 0;
}
