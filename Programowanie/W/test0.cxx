#include <iostream>
#include "strings.h"
#include <cassert>

using namespace std;

int main(int argc, char *argv[])
{
    Strings<2,1> *x = new Strings<2,1>("jeden");
    Strings<2,1> *y = new Strings<2,1>("dwa");
    Strings<2,1> *z = new Strings<2,1>("trzy");// utworzenie tego obiekt się nie powiedzie, z=NULL
    if(z!=nullptr)
        std::abort();
    Strings<2,1> t("cztery");                  // utworzenie obiektu na stosie
    delete y;
    Strings<2,1> *zz = new Strings<2,1>("trzy");  // teraz obiekt zostanie utworzony
    cout << *x  << " " << *zz << " " << t <<"\n";
    Strings<1,1> *a = new Strings<1,1>("JEDEN");
    Strings<1,1> b("DWA");
    cout << *a  << " " << b << "\n";
    cout << "stos=" << Strings<2,1>::objectsOnStack() << " sterta=" << Strings<2,1>::objectsOnHeap() << "\n";
    cout << "stos=" << Strings<1,1>::objectsOnStack() << " sterta=" << Strings<1,1>::objectsOnHeap() << "\n";
    return 0;
}
