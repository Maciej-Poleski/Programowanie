/*
ERROR_RATIO to liczba z zakresu 0 i 1 okreslajaca szanse wystapienia bledu w obliczeniu
EXEC_NO to ilosc dzialan jakie moga zostac wykonane zanim wywolany zostanie BladSystemu
REPEAT_TOLLERANCE to ilosc operacji na takich samych argumentach jakie moga zostac wykonane zanim wywolany zostanie BladSystemu
(w obu powyzszych przypadkach operacje ktore wykonaly sie blednie, albo spowodowaly wyjatek rowniez sie licza)
IS_BO, IS_BPO  to true/false warunki definiujace ktore wyjatki beda wystepowac
*/


#define ERROR_RATIO 0.999
#define EXEC_NO INT_MAX
#define REPEAT_TOLLERANCE INT_MAX
#define IS_BO true
#define IS_BPO true


#include <map>
#include <set>
#include <iostream>
#include <cstdlib>
#include <climits>

#include "ZepsutyULongLong.h"

using namespace std;

class ZepsutyULongLongLess
{
public:
    bool operator()(const ZepsutyULongLong *s1, const ZepsutyULongLong *s2) const {
        return s1 < s2;
    }
};
// ilość operacji wykonanych na konretnych argumentach
map< ZepsutyULongLong::argumenty, int > ZepsutyULongLong::wykonania = map< ZepsutyULongLong::argumenty, int>();
// wszystkie obiekty ZepsutyULongLong
set< ZepsutyULongLong *, ZepsutyULongLongLess > ZepsutyULongLong::obiekty = set< ZepsutyULongLong *,  ZepsutyULongLongLess >();

// jaki był ostatni błąd
ZepsutyULongLong::TypBledu ZepsutyULongLong::poprzedni_blad = BRAK;
// ilość wszystkich opearcji arytmetycznych
int ZepsutyULongLong::ilosc_wykonanych_operacji = 0;

// zanotuj wykonanie operacji na argumentach i, j
void ZepsutyULongLong::jeszcze_raz(arg i,  arg j)
{
    ilosc_wykonanych_operacji++;
    wykonania[i <= j ? argumenty(i, j) : argumenty(j, i)]++;
}

// wyzeruj wszstkie zarejestrowane oebiaky ZepsutyULongLong
void ZepsutyULongLong::zeruj_wszystkie_instancje()
{
    set< ZepsutyULongLong *, ZepsutyULongLongLess >::iterator it = obiekty.begin();
    for(; it != ZepsutyULongLong::obiekty.end(); it ++)(*it)->wartosc = 0;
}

// zdecyduj czy operacja wykonała się błędnie
bool ZepsutyULongLong::czy_blad()
{
    return (rand() < RAND_MAX * ERROR_RATIO) ;
}

// zdecyduj jakiego typu błąd nastąpił
ZepsutyULongLong::TypBledu ZepsutyULongLong::jaki_blad()
{
    int ilosc_wyjatkow = 0;
    if(IS_BO) ilosc_wyjatkow++;
    if(IS_BPO) ilosc_wyjatkow++;
    if(ilosc_wyjatkow == 0) return BRAK;
    int typ_wyjatku  = rand() % ilosc_wyjatkow;
    if(IS_BPO) if(typ_wyjatku -- == 0) return BPO;
    return BO;
}

// wykonaj przy błędzie systemu
void ZepsutyULongLong::blad_systemu()
{
    reset();
}

// sprawdź czy przekroczono tolerancję wykonań dla argumentów i, j
bool ZepsutyULongLong::czy_przekroczona_tolerancja(arg i, arg j)
{
    return (ile_razy(i, j) > REPEAT_TOLLERANCE);
}

// sprawdź czy przekroczono ilość dozwolonych operacji
bool ZepsutyULongLong::czy_przekroczona_ilosc_operacji()
{
    return (ilosc_wykonanych_operacji > EXEC_NO);
}

// procedura wołana przed wykonaniem każdej operacji arytmetycznej i decudująca o błędzie w operacji
int ZepsutyULongLong::wywolanie_operatora_arytmetycznego(const ZepsutyULongLong &i, const ZepsutyULongLong &j) throw(BladObliczenia, BladPoprzedniegoObliczenia, BladSystemu)
{
    int offset = 0;
    jeszcze_raz(i.wartosc, j.wartosc);

    if(czy_przekroczona_tolerancja(i.wartosc, j.wartosc) || czy_przekroczona_ilosc_operacji()) {
        std::cout << "Operacje na argumentach " << i.wartosc << " i " << j.wartosc << ": " << ile_razy(i.wartosc, j.wartosc) << '\n';
        blad_systemu();
        throw BladSystemu();
    }

    if(poprzedni_blad == BPO) {
        poprzedni_blad = BRAK;
        throw BladPoprzedniegoObliczenia();
    }

    if(czy_blad()) switch(jaki_blad()) {
        case BO:
            throw BladObliczenia();
        case BPO:
            poprzedni_blad = BPO;
            offset = rand() * ((rand() % 2) * 2 - 1);
            break;
        case BRAK:
            ;
        }
    return offset;
}

// ile operacji na danych argumentach wykonano
int ZepsutyULongLong::ile_razy(arg i, arg j)
{
    return wykonania[i <= j ? argumenty(i, j) : argumenty(j, i)];
}

ZepsutyULongLong::ZepsutyULongLong()
{
    wartosc = 0;
    ZepsutyULongLong::obiekty.insert(this);
}


ZepsutyULongLong::ZepsutyULongLong(const unsigned long long int &i)
{
    wartosc = i;
    ZepsutyULongLong::obiekty.insert(this);
}

ZepsutyULongLong::ZepsutyULongLong(const ZepsutyULongLong &i)
{
    wartosc = i.wartosc;
    ZepsutyULongLong::obiekty.insert(this);
}

ZepsutyULongLong::~ZepsutyULongLong()
{
    ZepsutyULongLong::obiekty.erase(this);
}

//resetuje wszystkie intancje i informacje o wykonaniach
void ZepsutyULongLong::reset()
{
    zeruj_wszystkie_instancje();
    wykonania.clear();
    poprzedni_blad = BRAK;
    ilosc_wykonanych_operacji = 0;
}

ZepsutyULongLong operator+(const ZepsutyULongLong &i, const ZepsutyULongLong &j) throw(BladObliczenia, BladPoprzedniegoObliczenia, BladSystemu)
{
    int offset = ZepsutyULongLong::wywolanie_operatora_arytmetycznego(i, j);
    return ZepsutyULongLong(i.wartosc + j.wartosc + offset);
}

ZepsutyULongLong operator-(const ZepsutyULongLong &i, const ZepsutyULongLong &j) throw(BladObliczenia, BladPoprzedniegoObliczenia, BladSystemu)
{
    int offset = ZepsutyULongLong::wywolanie_operatora_arytmetycznego(i, j);
    return ZepsutyULongLong(i.wartosc - j.wartosc + offset);
}

ZepsutyULongLong operator*(const ZepsutyULongLong &i, const ZepsutyULongLong &j) throw(BladObliczenia, BladPoprzedniegoObliczenia, BladSystemu)
{
    int offset = ZepsutyULongLong::wywolanie_operatora_arytmetycznego(i, j);
    return ZepsutyULongLong(i.wartosc * j.wartosc + offset);
}

ZepsutyULongLong operator/(const ZepsutyULongLong &i, const ZepsutyULongLong &j) throw(BladObliczenia, BladPoprzedniegoObliczenia, BladSystemu)
{
    if(j.wartosc == 0) throw BladObliczenia();
    int offset = ZepsutyULongLong::wywolanie_operatora_arytmetycznego(i, j);
    return ZepsutyULongLong(i.wartosc + j.wartosc + offset);
}

ZepsutyULongLong operator%(const ZepsutyULongLong &i, const ZepsutyULongLong &j) throw(BladObliczenia, BladPoprzedniegoObliczenia, BladSystemu)
{
    if(j.wartosc == 0) throw BladObliczenia();
    int offset = ZepsutyULongLong::wywolanie_operatora_arytmetycznego(i, j);
    return ZepsutyULongLong(i.wartosc % j.wartosc + offset);
}

std::ostream &operator<<(std::ostream &out, const ZepsutyULongLong &v)
{
    return out << v.wartosc;
}

bool operator==(const ZepsutyULongLong &i, const ZepsutyULongLong &j)
{
    return i.wartosc == j.wartosc;
}

bool operator!=(const ZepsutyULongLong &i, const ZepsutyULongLong &j)
{
    return i.wartosc != j.wartosc;
}

bool operator<=(const ZepsutyULongLong &i, const ZepsutyULongLong &j)
{
    return i.wartosc <= j.wartosc;
}

bool operator<(const ZepsutyULongLong &i, const ZepsutyULongLong &j)
{
    return i.wartosc < j.wartosc;
}

bool operator>=(const ZepsutyULongLong &i, const ZepsutyULongLong &j)
{
    return i.wartosc >= j.wartosc;
}

bool operator>(const ZepsutyULongLong &i, const ZepsutyULongLong &j)
{
    return i.wartosc > j.wartosc;
}
