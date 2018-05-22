#include <iostream>
#include <vector>
#include <algorithm>
#include <string>

long long sum1000[1001];
long long sum10000[10001];
long long sum100000[100001];
long long sum1000000[1000001];

long long f[13][101][10005][3];
std::vector<std::vector<int>> numOst(10);
std::vector<int> ost;

void updateDP(){
    for(int i = 0; i < 13; i++){
        for(int j = 0; j < 101; j++){
            for(int k = 0; k < 10005; k++){
                f[i][j][k][0] = 0;
                f[i][j][k][1] = 0;
            }
        }
    }
}

long long solveDP(long long k, int p, int q, long long marge){
    
    updateDP();
    
    f[0][0][0][1] = 1;
    
    std::vector<int> digits;
    
    while(marge){
        digits.push_back(marge % 10);
        marge /= 10;
    }
    
    std::reverse(digits.begin(), digits.end());
    unsigned long digitsSize = digits.size();
    
    numOst.clear();
    ost.resize(k + 1, 0);
    numOst.assign(10, ost);
    
    for(int i = 0; i < 10; i++){
        for(int j = 0; j < k; j++){
            numOst[i][j] = (j*10 + i)%k;
        }
    }
    
    for(int razryad = 0; razryad < digitsSize; razryad++){
        for(int summa = 0; summa < q + 1; summa++){
            for(int ostatok = 0; ostatok < k; ostatok++){
                if(f[razryad][summa][ostatok][0] != 0){
                    for(int number = 0; number < 10; number++){
                        if((summa + number) < q + 1){
                            f[razryad + 1][summa + number][numOst[number][ostatok]][0] += f[razryad][summa][ostatok][0];
                        }
                    }
                }
                
                if(f[razryad][summa][ostatok][1] != 0){
                    int digit = digits[razryad];
                    for(int number = 0; number < digit; number++){
                        if((summa + number) < q + 1){
                            f[razryad + 1][summa + number][numOst[number][ostatok]][0] += f[razryad][summa][ostatok][1];
                        }
                    }
                    
                    if((summa + digit) < q + 1){
                        f[razryad + 1][summa + digit][numOst[digit][ostatok]][1] += f[razryad][summa][ostatok][1];
                    }
                }
                
            }
        }
    }
    
    long long answer = 0;
    
    for (int iSumma = p; iSumma < q + 1; iSumma++){
        answer += f[digitsSize][iSumma][0][0] + f[digitsSize][iSumma][0][1];
    }
    
    return answer;
}

int sumOfDigits(long long digit){
    int sum = 0;
    while (digit) {
        sum += digit % 10;
        digit /= 10;
    }
    
    return sum;
}

int sumOfBigDigits(long long digit){
    int sum = 0;
    while(digit){
        sum += sum1000[digit % 1000];
        digit /= 1000;
    }
    
    return sum;
}

int sumOfBigBigDigits(long long digit){
    int sum = 0;
    while(digit){
        sum += sum100000[digit % 100000];
        digit /= 100000;
    }
    
    return sum;
}

int main(int argc, const char * argv[]) {
    freopen("lucky.in", "r", stdin);
    freopen("lucky.out", "w", stdout);
    
    long long k, a, b;
    int p, q;
    
    std::cin >> k >> p >> q >> a >> b;
    
    if(k > 10000 && k <= b){
        
        for(int i = 1; i < 101; i++){
            int temp = sumOfDigits(i);
            sum1000[i] = temp;
            sum10000[i] = temp;
            sum100000[i] = temp;
        }
        
        for(int i = 101; i < 1001; i++){
            int temp = sumOfDigits(i);
            sum1000[i] = temp;
            sum10000[i] = temp;
            sum100000[i] = temp;
        }
        
        for(int i = 1001; i < 10001; i++){
            int temp = sumOfBigDigits(i);
            sum10000[i] = temp;
            sum100000[i] = temp;
        }
        
        for(int i = 10001; i < 100001; i++){
            sum100000[i] = sumOfBigDigits(i);
        }
        
        int answer = 0;
        long long temp = k;
        if(a > k) k = (a/k + 1)*k;
        
        while(k <= b){
            int sum = sumOfBigBigDigits(k);
            if(sum >= p && sum <= q) answer++;
            k += temp;
        }
        
        std::cout << answer << "\n";
    }
    else if(k <= 10000){
        long long answer = solveDP(k, p, q, b) - solveDP(k, p, q, a - 1);
        std::cout << answer << "\n";
    }
    else{
        std::cout << 0 << "\n";
    }
    
    //std::cerr << clock() * 1000 / CLOCKS_PER_SEC << std::endl;
    return 0;
}
