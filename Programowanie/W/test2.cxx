#include <iostream>
#include "strings.h"
#include <cassert>

using namespace std;

int main(int argc, char *argv[])
{
    Strings<> A("alicja");
    std::cout << A.name << "\n";
    Strings<> *B = new Strings<>("Bart");
    std::cout << *B << "\n";
    try {
        Strings<0, 1> e("blok 1");
        std::cout << e << "\n";
        Strings<0, 1> f("blok 2");
        std::cout << f << "\n";
        Strings<0, 1> g("blok 3");
        std::cout << g << "\n";
    } catch(ErrorObjectCreation err) {
        std::cout << "Error creating person " << err.id << "\n";
    }
    cout << "stos=" << Strings<0, 1>::objectsOnStack() << " sterta=" << Strings<0, 1>::objectsOnHeap() << "\n";
    Strings<0, 1> C("the only one");
    cout << "stos=" << Strings<0, 1>::objectsOnStack() << " sterta=" << Strings<0, 1>::objectsOnHeap() << "\n";
    return 0;
}
