#pragma once

#include <map>
#include <set>
#include <iosfwd>

class BladObliczenia {};

class BladPoprzedniegoObliczenia {};

class BladSystemu {};

class ZepsutyULongLongLess;

class ZepsutyULongLong {
 private:
  typedef std::pair<unsigned long long int, unsigned long long int> argumenty;
  typedef unsigned long long int arg;

  enum TypBledu{BRAK, BO, BPO, BPOZOJ};
  
  unsigned long long int wartosc;

  static std::set< ZepsutyULongLong*, ZepsutyULongLongLess > obiekty;
  static std::map< argumenty, int > wykonania;
  static TypBledu poprzedni_blad;
  static int ilosc_wykonanych_operacji;

  static bool czy_blad();
  static TypBledu jaki_blad();
  static int  ile_razy(arg i, arg j);
  static void jeszcze_raz(arg i, arg j);
  static void zeruj_wszystkie_instancje();
  static void blad_systemu();
  static bool czy_przekroczona_tolerancja(arg i, arg j);
  static bool czy_przekroczona_ilosc_operacji();
  static int wywolanie_operatora_arytmetycznego(const ZepsutyULongLong &i, const ZepsutyULongLong &j) 
		throw (BladObliczenia, BladPoprzedniegoObliczenia, BladSystemu);
  static void wywolanie_operatora_logicznego(const ZepsutyULongLong &i, const ZepsutyULongLong &j);

 public:
  ZepsutyULongLong();
  ZepsutyULongLong(const unsigned long long int &i);
  ZepsutyULongLong(const ZepsutyULongLong &i);
  ~ZepsutyULongLong();
  
  static void reset();

  friend ZepsutyULongLong operator+(const ZepsutyULongLong &i, const ZepsutyULongLong &j) 
	throw(BladObliczenia, BladPoprzedniegoObliczenia, BladSystemu);
  friend ZepsutyULongLong operator-(const ZepsutyULongLong &i, const ZepsutyULongLong &j) 
	throw(BladObliczenia, BladPoprzedniegoObliczenia, BladSystemu);
  friend ZepsutyULongLong operator*(const ZepsutyULongLong &i, const ZepsutyULongLong &j) 
	throw(BladObliczenia, BladPoprzedniegoObliczenia, BladSystemu);
  friend ZepsutyULongLong operator/(const ZepsutyULongLong &i, const ZepsutyULongLong &j) 
	throw(BladObliczenia, BladPoprzedniegoObliczenia, BladSystemu);
  friend ZepsutyULongLong operator%(const ZepsutyULongLong &i, const ZepsutyULongLong &j) 
	throw(BladObliczenia, BladPoprzedniegoObliczenia, BladSystemu);

  friend bool operator==(const ZepsutyULongLong &i, const ZepsutyULongLong &j);
  friend bool operator!=(const ZepsutyULongLong &i, const ZepsutyULongLong &j);
  friend bool operator<=(const ZepsutyULongLong &i, const ZepsutyULongLong &j);
  friend bool operator>=(const ZepsutyULongLong &i, const ZepsutyULongLong &j);
  friend bool operator<(const ZepsutyULongLong &i, const ZepsutyULongLong &j);
  friend bool operator>(const ZepsutyULongLong &i, const ZepsutyULongLong &j);

  friend std::ostream &operator<<(std::ostream &out, const ZepsutyULongLong &v);
};

