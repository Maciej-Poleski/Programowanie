#include <iostream>
#include "tracer.cpp"

using namespace std;

int main(int argc, char *argv[])
{
    string s[] = {"Ala", "ma", "kota"};
    tracer t1(s, 3);
    tracer t2 = t1;
    t2.concat(0, 1);
    t2.concat(0, 2);
    s[0] = "Zuzanna";
    cout << t1;
    cout << t1;
    cout << t1.printno() << endl;
    cout << t2;
    cout << t2.printno() << endl;
    cout << tracer::objectno() << endl;
    return 0;
}
