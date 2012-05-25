cmake_minimum_required(VERSION 2.8 FATAL_ERROR)

project(satori)

if(DEBUG)
    add_definitions(-DDEBUG)
endif(DEBUG)

add_executable(test0 test0.cxx)
add_executable(test1 test1.cxx)
add_executable(test2 test2.cxx)
add_executable(test3 test3.cxx)
