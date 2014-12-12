package com.example.fibonaccicommon;

import com.example.fibonaccicommon.FibonacciRequest;
import com.example.fibonaccicommon.FibonacciResponse;

interface IFibonacciService {
    long fibJR(in long n);
    long fibJI(in long n);
    long fibNR(in long n);
    long fibNI(in long n);
    FibonacciResponse fib(in FibonacciRequest request);
}