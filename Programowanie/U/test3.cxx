#include <iostream>
#include "fraction.h"
using namespace std;
int main()
{
    Fraction x(1);
    Fraction y(2, 3);
    x += y += x *= Fraction(1, 2);
    cout << "x=" << x << " y=" << y << "\n";
    cout << ++++x << "\n";
    cout << x << "\n";
    cout << x++++ << "\n"; // dla typu int nie skompiluje się
    cout << x << "\n";
    cout << ++x++ << "\n"; // dla typu int nie skompiluje się
    cout << x << "\n";
}
