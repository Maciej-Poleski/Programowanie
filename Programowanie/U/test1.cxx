#include <iostream>
#include "fraction.h"
using namespace std;
int main()
{
    Fraction x(1, 2);
    Fraction *y = new Fraction(4, 6);
    Fraction z;
    z = x + Fraction(1, 6) > *y ? Fraction() : x **y;
    cout << "x = " << x << " y = " << *y << " z = " << z << "\n";
}
