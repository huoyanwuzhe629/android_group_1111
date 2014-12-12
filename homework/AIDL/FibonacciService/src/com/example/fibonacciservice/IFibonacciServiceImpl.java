
package com.example.fibonacciservice;

import android.util.Log;

import com.example.fibonaccicommon.FibonacciRequest;
import com.example.fibonaccicommon.FibonacciResponse;
import com.example.fibonaccicommon.IFibonacciService;
import com.example.fibonaccinative.FibLib;

public class IFibonacciServiceImpl extends IFibonacciService.Stub {
    private static final String TAG = "IFibonacciServiceImpl";

    public long fibJI(long n) {
        Log.d(TAG, "fibJI()");
        return FibLib.fibJI(n);
    }

    public long fibJR(long n) {
        Log.d(TAG, "fibJR()");
        return FibLib.fibJR(n);
    }

    public long fibNI(long n) {
        Log.d(TAG, "fibNI()");
        return FibLib.fibNI(n);
    }

    public long fibNR(long n) {
        Log.d(TAG, "fibNR()");
        return FibLib.fibNR(n);
    }

    public FibonacciResponse fib(FibonacciRequest request) {
        Log.d(TAG, "fib()");
        long timeInMillis = System.currentTimeMillis();
        long result;
        switch (request.getType()) {
            case FibonacciRequest.ITERATIVE_JAVA_TYPE:
                result = this.fibJI(request.getN());
                break;
            case FibonacciRequest.RECURSIVE_JAVA_TYPE:
                result = this.fibJR(request.getN());
                break;
            case FibonacciRequest.ITERATIVE_NATIVE_TYPE:
                result = this.fibNI(request.getN());
                break;
            case FibonacciRequest.RECURSIVE_NATIVE_TYPE:
                result = this.fibNR(request.getN());
                break;
            default:
                return null;
        }
        timeInMillis = System.currentTimeMillis() - timeInMillis;
        return new FibonacciResponse(result, timeInMillis);
    }
}
