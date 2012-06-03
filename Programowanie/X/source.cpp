#include "ZepsutyULongLong.h"

ZepsutyULongLong s(const unsigned long long &doInt) throw()
{
    ZepsutyULongLong::reset();
    for(;;) {
        try {
            ZepsutyULongLong result;
            ZepsutyULongLong confirmedResult = 1;
            ZepsutyULongLong arg = doInt;
            ZepsutyULongLong confirmedArg = 4;
            ZepsutyULongLong doMultiply = 1;
            if(arg <=3)
                return arg;
            while(confirmedArg != 1) {
                if(doMultiply == 1) {
                    for(;;) {
                        try {
                            result = confirmedResult * arg;
                            confirmedArg = arg;
                            doMultiply = 0;
                            break;
                        } catch(BladObliczenia) {
                        } catch(BladPoprzedniegoObliczenia) {
                            doMultiply = 0;
                            break;
                        }
                    }
                } else {
                    for(;;) {
                        try {
                            arg = confirmedArg - 2;
                            confirmedResult = result;
                            doMultiply = 1;
                            break;
                        } catch(BladObliczenia) {
                        } catch(BladPoprzedniegoObliczenia) {
                            doMultiply = 1;
                            break;
                        }
                    }
                }
            }
            return confirmedResult;
        } catch(BladSystemu) {
        }
    }
}
