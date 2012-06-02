#ifndef STRINGS_H
#define STRINGS_H

#include <new>
#include <limits>
#include <string>
#include <iostream>
#include <stdexcept>

class ErrorObjectCreation : public std::logic_error
{
public:
    explicit ErrorObjectCreation(int id);

public:
    int id;
};

ErrorObjectCreation::ErrorObjectCreation(int id): logic_error(""), id(id)
{
}

template<int H, int S>
class Strings;

template<int H, int S>
std::ostream &operator<<(std::ostream &out, const Strings<H, S> &str)
{
    return out << '(' << str._id << ',' << str._data << ')';
}

template<int H = std::numeric_limits<int>::max(), int S = std::numeric_limits<int>::max()>
class Strings
{
    static_assert(H >= 0, "H nie może być ujemne");
    static_assert(S >= 0, "S nie może być ujemne");
    static_assert(H > 0 || S > 0, "H i S nie mogą być jednocześnie zerowe");
public:
    Strings() : name(" "), _id(_idPool) {
        if(_stackCount == S) {
            throw ErrorObjectCreation(_idPool);
        }
        ++_idPool;
        ++_stackCount;
    }

    Strings(const std::string &str) : name(" " + str), _data(str), _id(_idPool) {
        if(_stackCount == S) {
            throw ErrorObjectCreation(_idPool);
        }
        ++_idPool;
        ++_stackCount;
    }

    Strings(const Strings &o) : name(o.name), _data(o._data), _id(_idPool) {
        if(_stackCount == S) {
            throw ErrorObjectCreation(_idPool);
        }
        ++_idPool;
        ++_stackCount;
    }

    ~Strings() {
        --_stackCount;
    }

    Strings &operator=(const Strings &o) {
        name = o.name;
        _data = o._data;
        return *this;
    }

    friend std::ostream &operator<< <>(std::ostream &out, const Strings<H, S> &str);

    static int objectsOnStack() noexcept {
        return _stackCount;
    }

    static int objectsOnHeap() noexcept {
        return _heapCount;
    }

    static void *operator new(std::size_t) noexcept;
    static void *operator new[](std::size_t) noexcept;
    static void operator delete(void *) noexcept;
    static void operator delete[](void *, std::size_t) noexcept;

public:
    std::string name;

private:
    std::string _data;
    const int _id;
    static int _idPool;
    static int _stackCount;
    static int _heapCount;
};

template<int H, int S>
int Strings<H, S>::_idPool = 0;

template<int H, int S>
int Strings<H, S>::_stackCount = 0;

template<int H, int S>
int Strings<H, S>::_heapCount = 0;

template<int H, int S>
void *Strings<H, S>::operator new(std::size_t size) noexcept {
    if(Strings<H, S>::_heapCount == H) {
        return nullptr;
    }
    ++Strings<H, S>::_heapCount;
    --Strings<H, S>::_stackCount;
    return ::operator new(size);
}

template<int H, int S>
void Strings<H, S>::operator delete(void *ptr) noexcept {
    if(ptr != nullptr) {
        --Strings<H, S>::_heapCount;
        ++Strings<H, S>::_stackCount;
    }
    ::operator delete(ptr);
}

template<int H, int S>
static constexpr std::size_t getCookieSize()
{
    return std::max(sizeof(std::size_t), alignof(Strings<H, S>));
}

template<int H, int S>
void *Strings<H, S>::operator new[](std::size_t size) noexcept {
    if(Strings<H, S>::_heapCount + (size - getCookieSize<H, S>()) / sizeof(Strings<H, S>) > H) {
        return nullptr;
    }
    Strings<H, S>::_heapCount += (size - getCookieSize<H, S>()) / sizeof(Strings<H, S>);
    Strings<H, S>::_stackCount -= (size - getCookieSize<H, S>()) / sizeof(Strings<H, S>);
    return ::operator new(size);
}

template<int H, int S>
void Strings<H, S>::operator delete[](void *ptr, std::size_t size) noexcept {
    if(ptr != nullptr) {
        Strings<H, S>::_heapCount -= (size - getCookieSize<H, S>()) / sizeof(Strings<H, S>);
        Strings<H, S>::_stackCount += (size - getCookieSize<H, S>()) / sizeof(Strings<H, S>);
    }
    ::operator delete(ptr);
}

#endif // STRINGS_H
