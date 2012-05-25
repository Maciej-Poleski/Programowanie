#include <iostream>
#include "fraction.h"
using namespace std;
int main()
{
    Fraction x(2);
    Fraction y(1, 2);
    cout << "x = " << x << "\ny = " << y << "\n";
    cout << "x+y = " << x + y << "\n";
    cout << "x-y = " << x - y << "\n";
    cout << "x*y = " << x *y << "\n";
    cout << "x/y = " << x / y << "\n";
    cout << x + y + x << "\n";
    cout << x + x *y << "\n";
    cout << Fraction(10, 30) + Fraction(4, 6) - Fraction(1)*Fraction(20, 10) / Fraction(2) << "\n";
}
