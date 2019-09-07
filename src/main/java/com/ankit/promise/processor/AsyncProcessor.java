package com.ankit.promise.processor;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class AsyncProcessor<R> {


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

    public <T> ValueHolder<R> call(Function<T, R> functionToExecute, T t) {
        ValueHolder<R> valueHolder = new ValueHolder<>();
        Runnable runnable = () -> {
            declareThreadProcessing(functionToExecute, t, valueHolder);
        };
        return enrichValueHolder(valueHolder, runnable);

    }

    private <T> void declareThreadProcessing(Function<T, R> functionToExecute, T t, ValueHolder<R> valueHolder) {
        R returnVal = functionToExecute.apply(t);
        valueHolder.setValue(returnVal);
    }

    private ValueHolder<R> enrichValueHolder(ValueHolder<R> valueHolder, Runnable runnable) {
        Thread valueCalculatorThread = startThread(runnable);
        valueHolder.setValueSetterThread(valueCalculatorThread);
        return valueHolder;
    }

    public <T> ValueHolder<R> call(Function<T, R> functionToExecute, ValueHolder<T> valueHolder) {
        ValueHolder<R> valueHolderToReturn = new ValueHolder<>();
        Runnable runnable = () -> {
            T val = valueHolder.get();
            declareThreadProcessing(functionToExecute, val, valueHolderToReturn);
        };

        return enrichValueHolder(valueHolderToReturn, runnable);

    }


    public ValueHolder<R> call(Supplier<R> supplier) {
        ValueHolder<R> valueHolder = new ValueHolder<>();
        Runnable runnable = () -> {
            R returnVal = supplier.get();
            valueHolder.setValue(returnVal);
        };
        return enrichValueHolder(valueHolder, runnable);


    }


    private Thread startThread(Runnable runnable) {
        Thread thread = new Thread(runnable);
        thread.start();
        return thread;

    }


}
