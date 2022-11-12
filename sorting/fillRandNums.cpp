#include<fstream>

using namespace std;

int main(){

  ofstream file("data-1mil.txt");

  for(int i=0; i<1000000; i++){
    file << rand()%9999 << ", ";
  }
  
}