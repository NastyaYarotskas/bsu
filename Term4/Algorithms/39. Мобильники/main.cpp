#include <iostream>
#include <algorithm>

int size;

struct NodeY{
    NodeY* left;
    NodeY *right;
    long long sum;
    
    NodeY() {
        left = NULL; right = NULL; sum = 0;
    };
};

struct NodeX{
    NodeX* left;
    NodeX *right;
    NodeY *nodeY;
    
    NodeX() {
        left = NULL; right = NULL; nodeY = NULL;
    };
};

long long get_sum(NodeY* node){
    if(node == NULL) return 0;
    return node->sum;
}

long long sum_y(NodeY *node, int Ly, int Ry, int ly, int ry){
    if(ly > ry){
        return 0;
    }
    
    if(ly == Ly && ry == Ry){
        return get_sum(node);
    }
    
    int m = (Ly + Ry) >> 1;
    
    long long sum1, sum2;
    
    if(node->left == NULL) sum1 = 0;
    else sum1 = sum_y(node->left, Ly, m, ly, std::min(ry, m));
    
    if(node->right == NULL) sum2 = 0;
    else sum2 = sum_y(node->right, m + 1, Ry, std::max(ly, m + 1), ry);
    
    return sum1 + sum2;
}

long long sum_x(NodeX* node, int Lx, int Rx, int lx, int rx, int ly, int ry){
    if(lx > rx){
        return 0;
    }
    
    if(lx == Lx && rx == Rx){
        if(node->nodeY == NULL) return 0;
        return sum_y(node->nodeY, 0, size - 1, ly, ry);
    }
    
    int m = (Lx + Rx) >> 1;
    long long sum1, sum2;
    
    if(node->left == NULL) sum1 = 0;
    else sum1 = sum_x(node->left, Lx, m, lx, std::min(rx, m), ly, ry);
    
    if(node->right == NULL) sum2 = 0;
    else sum2 = sum_x(node->right, m + 1, Rx, std::max(lx, m + 1), rx, ly, ry);
    
    return sum1 + sum2;
}

void update_y(NodeY* node, int l, int r, int L, int R, int x, int y, int val){
    if(L == R){
        node->sum += val;
    }
    else{
        int m = (L + R) >> 1;
        if(y <= m){
            if(node->left == NULL) node->left = new NodeY();
            update_y(node->left, l, r, L, m, x, y, val);
        }
        else{
            if(node->right == NULL) node->right = new NodeY();
            update_y(node->right, l, r, m + 1, R, x, y, val);
        }
        
        node->sum = get_sum(node->right) + get_sum(node->left);
    }
}

void update_x(NodeX* node, int L, int R, int x, int y, int val){
    if(L != R){
        int m = (L + R) >> 1;
        if(x <= m){
            if(node->left == NULL) node->left = new NodeX();
            update_x(node->left, L, m, x, y, val);
        }
        else{
            if(node->right == NULL) node->right = new NodeX();
            update_x(node->right, m + 1, R, x, y, val);
        }
    }
    
    if(node->nodeY == NULL) node->nodeY = new NodeY();
    update_y(node->nodeY, L, R, 0, size - 1, x, y, val);
}

int main(int argc, const char * argv[]) {
    freopen("input.txt", "r", stdin);
    freopen("output.txt", "w", stdout);
    
    int comand;
    std::cin >> comand >> size;
    
    NodeX* root = new NodeX();
    int x, y, a;
    int k, b, r, t;
    while(std::cin >> comand){
        if(comand == 1){
            std::cin >> x >> y >> a;
            update_x(root, 0, size - 1, x, y, a);
        }
        else if(comand == 2){
            std::cin >> k >> b >> r >> t;
            std::cout << sum_x(root, 0, size - 1, k, r, b, t) << std::endl;
        }
        else if(comand == 3){
            return 0;
        }
    }
    
    return 0;
}
