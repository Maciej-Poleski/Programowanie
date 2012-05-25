#ifndef FRACTION_H
#define FRACTION_H

#include <iostream>
#include <algorithm>
#include <utility>
#include <vector>

static int gcd(int a, int b)
{
    while(b != 0) {
        int t = b;
        b = a % b;
        a = t;
    }
    return a;
}

class Fraction
{
public:
    explicit Fraction(int a = 0, int b = 1) : _numerator(a), _denominator(b) {
        clean();
    }

    Fraction(const Fraction &o) :
        _numerator(o._numerator), _denominator(o._denominator) {
    }

    Fraction &operator=(const Fraction &o) {
        _numerator = o._numerator;
        _denominator = o._denominator;
        return *this;
    }

    ~Fraction() {
        for(std::vector<Fraction *>::iterator i = _increment.begin(),
                e = _increment.end(); i != e; ++i) {
            ++(**i);
        }
        for(std::vector<Fraction *>::iterator i = _decrement.begin(),
                e = _decrement.end(); i != e; ++i) {
            --(**i);
        }

    }

    friend const Fraction operator+(const Fraction &a, const Fraction &b) {
        return Fraction(a._numerator * b._denominator +
                        a._denominator * b._numerator,
                        a._denominator * b._denominator);
    }

    friend const Fraction operator-(const Fraction &a, const Fraction &b) {
        return Fraction(a._numerator * b._denominator -
                        a._denominator * b._numerator,
                        a._denominator * b._denominator);
    }

    friend const Fraction operator*(const Fraction &a, const Fraction &b) {
        return Fraction(a._numerator * b._numerator,
                        a._denominator * b._denominator);
    }

    friend const Fraction operator/(const Fraction &a, const Fraction &b) {
        return Fraction(a._numerator * b._denominator,
                        a._denominator * b._numerator);
    }

    Fraction &operator+=(const Fraction &b) {
        _numerator = _numerator * b._denominator + _denominator * b._numerator;
        _denominator *= b._denominator;
        clean();
        return *this;
    }

    Fraction &operator-=(const Fraction &b) {
        _numerator = _numerator * b._denominator - _denominator * b._numerator;
        _denominator *= b._denominator;
        clean();
        return *this;
    }

    Fraction &operator*=(const Fraction &b) {
        _numerator *= b._numerator;
        _denominator *= b._denominator;
        clean();
        return *this;
    }

    Fraction &operator/=(const Fraction &b) {
        _numerator *= b._denominator;
        _denominator *= b._numerator;
        clean();
        return *this;
    }

    friend bool operator<(const Fraction &a, const Fraction &b) {
        return a._numerator * b._denominator < b._numerator * a._denominator;
    }

    friend bool operator>(const Fraction &a, const Fraction &b) {
        return a._numerator * b._denominator > b._numerator * a._denominator;
    }

    friend bool operator<=(const Fraction &a, const Fraction &b) {
        return a._numerator * b._denominator <= b._numerator * a._denominator;
    }

    friend bool operator>=(const Fraction &a, const Fraction &b) {
        return a._numerator * b._denominator >= b._numerator * a._denominator;
    }

    friend bool operator==(const Fraction &a, const Fraction &b) {
        return a._numerator == b._numerator && a._denominator == b._denominator;
    }

    friend bool operator!=(const Fraction &a, const Fraction &b) {
        return a._numerator != b._numerator || a._denominator != b._denominator;
    }

    Fraction &operator++() {
        _numerator += _denominator;
        clean();
        return *this;
    }

    Fraction &operator--() {
        _numerator -= _denominator;
        clean();
        return *this;
    }

    Fraction operator++(int) {
        Fraction result = *this;
        result._increment.push_back(this);
        return result;
    }

    Fraction operator--(int) {
        Fraction result = *this;
        result._decrement.push_back(this);
        return result;
    }

    friend std::ostream &operator<<(std::ostream &out, const Fraction &o) {
        return out << o._numerator << '/' << o._denominator;
    }

private:
    void clean() {
        if(_denominator < 0) {
            _numerator = -_numerator;
            _denominator = -_denominator;
        }
        int g = gcd(std::abs(_numerator), _denominator);
        if(g != 0) {
            _numerator /= g;
            _denominator /= g;
        } else if(_numerator == 0) {
            _denominator = 1;
        }
    }

private:
    int _numerator;
    int _denominator;

    std::vector<Fraction *> _decrement;
    std::vector<Fraction *> _increment;
};

#endif // FRACTION_H
