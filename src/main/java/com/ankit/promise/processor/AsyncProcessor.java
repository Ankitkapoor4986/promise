package com.ankit.promise.processor;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class AsyncProcessor<R> {


    private R returnVal;


    public <T> void call(Consumer<T> consumer, T t) {
        Runnable runnable = () -> {
            consumer.accept(t);
        };
        startThread(runnable);
    }

    public <T> void call(Consumer<T> consumer, ValueHolder<T> valueHolder) {
        Runnable runnable = () -> {
            T val = valueHolder.get();
            consumer.accept(val);
        };
        startThread(runnable);
    }

    public <T>  ValueHolder<R> call(Function<T, R> functionToExecute, T t) {

        Runnable runnable = () -> {
            returnVal = functionToExecute.apply(t);
        };
        return buildValueHolder(runnable);

    }

    public <T>  ValueHolder<R> call(Function<T, R> functionToExecute, ValueHolder<T> valueHolder) {

        Runnable runnable = () -> {
            T val = valueHolder.get();
            returnVal = functionToExecute.apply(val);
        };
        return buildValueHolder(runnable);

    }

    private ValueHolder<R> buildValueHolder(Runnable runnable) {
        Thread valueCalculatorThread = startThread(runnable);
        ValueHolder<R> valueHolder = new ValueHolder<>();
        Runnable valueSetterRunnable = initializeValueSetterRunnable(valueCalculatorThread,valueHolder);
        Thread valueSetterThread = startThread(valueSetterRunnable);
        valueHolder.setValueSetterThread(valueSetterThread);
        return valueHolder;


    }

    public ValueHolder<R> call(Supplier<R> supplier) {
        Runnable runnable = () -> {
            returnVal = supplier.get();
        };

        return buildValueHolder(runnable);

    }

    private Runnable  initializeValueSetterRunnable(Thread valueCalculatorThread, ValueHolder<R> valueHolder) {
        return () -> {
            AsyncProcessorUtil.joinThread(valueCalculatorThread);
            valueHolder.setValue(returnVal);
        };

    }



    private Thread startThread(Runnable runnable) {
        Thread thread = new Thread(runnable);
        thread.start();
        return thread;

    }



}
