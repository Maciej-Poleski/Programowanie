#include <iostream>
#include "fraction.h"
using namespace std;
int main()
{
    Fraction x;
    Fraction y(2);
    cout << "x=" << x << " y=" << y << "\n";
    cout << x++ * y << "\n"; // efekt taki sam jak dla typu int
    cout << "x=" << x << " y=" << y << "\n";
    cout << (x++ == y ? x++ : x--) << "\n"; // efekt inny niż dla typu int
    cout << "x=" << x << " y=" << y << "\n";
}
