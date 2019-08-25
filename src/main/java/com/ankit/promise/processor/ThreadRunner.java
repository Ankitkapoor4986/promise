package com.ankit.promise.processor;

import java.util.function.Function;
import java.util.function.Supplier;

public class ThreadRunner<R> {

    private static final String VALUE_CALCULATOR_THREAD = "valueCalculatorThread";
    private static final String VALUE_SETTER_THREAD = "valueSetterThread";
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
        Thread valueCalculatorThread = startThread(runnable, VALUE_CALCULATOR_THREAD);
        Runnable valueSetterRunnable = initializeValueSetterRunnable(valueCalculatorThread);
        Thread valueSetterThread = startThread(valueSetterRunnable, VALUE_SETTER_THREAD);
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

    private Thread startThread(Runnable runnable, String name) {
        Thread thread = new Thread(runnable);
        thread.start();
        return thread;

    }



}
