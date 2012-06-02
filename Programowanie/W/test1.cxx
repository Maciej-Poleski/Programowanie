#include <iostream>
#include "strings.h"
#include <cassert>

using namespace std;

int main(int argc, char *argv[])
{
    //Strings<1, 2, 3> a; // blad kompilacji
    //Strings<324, 35, 4, 56, 54, 457, 4> b; // blad kompilacji
    Strings<2, 3> c; // kompilacja poprawna
    //Strings < 1, -1 > d; // blad kompilacji
    //Strings < -7, 9 > e; // blad kompilacji
    //Strings < -8 > f; // blad kompilacji
    Strings<0> g;    // kompilacja poprawna
    //Strings<0, 0> h; // blad kompilacji
    Strings<1, 0> *i = new Strings<1, 0>("oko"); // kompilacja poprawna
    new Strings<10,10>[10];
    return 0;
}
