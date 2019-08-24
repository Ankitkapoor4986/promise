package com.ankit.promise.processor;

import java.util.function.Function;
import java.util.function.Supplier;

public class ThreadRunner<R> {

    private R returnVal;
    private ValueHolder<R> valueHolder;





    public <T>  ValueHolder<R> call(Function<T, R> functionToExecute, T t) {

        Runnable runnable = () -> {
            returnVal = functionToExecute.apply(t);
        };
        doProcessing(runnable);
        return valueHolder;
    }

    private void doProcessing(Runnable runnable) {
        Thread valueCalculatorThread = startThread(runnable);
        Runnable valueSetterRunnable = initializeValueSetterRunnable(valueCalculatorThread);
        Thread valueSetterThread = startThread(valueSetterRunnable);
        valueHolder = new ValueHolder<>(valueSetterThread);

    }

    public ValueHolder<R> call(Supplier<R> supplier) {
        Runnable runnable = () -> {
            returnVal = supplier.get();
        };

        doProcessing(runnable);
        return valueHolder;
    }

    private Runnable  initializeValueSetterRunnable(Thread valueCalculatorThread) {
        return () -> {
            joinThread(valueCalculatorThread);
            valueHolder.setValue(returnVal);
        };

    }

    private void joinThread(Thread thread) {
        try {
            if(thread.isAlive()) {
                thread.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private Thread startThread(Runnable runnable) {
        Thread thread = new Thread(runnable);
        thread.start();
        return thread;

    }



}
