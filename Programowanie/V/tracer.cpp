#include <string>
#include <vector>
#include <iostream>

class tracer
{
public:
    tracer() : _printCount(0) {
        ++_objectCount;
    }

    tracer(const std::string *t, int s) : _data(t, t + s), _printCount(0) {
        ++_objectCount;
    }

    tracer(const tracer &o) : _data(o._data), _printCount(0) {
        ++_objectCount;
    }

    tracer &operator=(const tracer &o) {
        _data = o._data;
        return *this;
    }

    ~tracer() {
        --_objectCount;
    }

    void concat(int arg1, int arg2) {
        _data[arg1] += _data[arg2];
    }

    int printno() const {
        return _printCount;
    }

    friend std::ostream &operator<<(std::ostream &out, const tracer &o) {
        ++o._printCount;
        for(std::vector<std::string>::const_iterator i = o._data.begin(),
                e = o._data.end(); i != e; ++i) {
            out << *i << std::endl;
        }
        return out;
    }

    static int objectno() {
        return _objectCount;
    }

private:
    std::vector<std::string> _data;
    mutable int _printCount;
    static int _objectCount;
};

int tracer::_objectCount = 0;
