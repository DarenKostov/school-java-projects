#include<fstream>

using namespace std;

int main(){

  ofstream file("100nums.txt");

  for(int i=0; i<100; i++){
    file << rand()%1000 << " ";
  }
  
}