package com.ankit.promise;

import com.ankit.promise.processor.AsyncProcessor;
import com.ankit.promise.processor.ValueHolder;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class PromiseImpl<R> implements Promise<R> {

    private ValueHolder<R> valueHolder;

    private PromiseImpl(ValueHolder<R> valueHolder) {
        this.valueHolder = valueHolder;
    }

    public PromiseImpl(){

    }

    @Override
    public <T> Promise<R> then(Function<T, R> resolve, T t, Function<T, R> reject, Predicate<T> predicate) {
        if(predicate.test(t)) {
            return doProcessing(resolve, t);
        }else {
            return doProcessing(reject,t);
        }
    }

    @Override
    public <T> Promise<R> then(Function<T, R> resolve, T t) {
        return doProcessing(resolve,t);
    }

    @Override
    public <T> Promise<R> then(Function<T, R> resolve, ValueHolder<T> valueHolder) {
        return doProcessing(resolve,valueHolder);
    }

    private <T> Promise<R> doProcessing(Function<T, R> function, T t) {
        AsyncProcessor<R> asyncProcessor = new AsyncProcessor<>();
        ValueHolder<R> valueHolder = asyncProcessor.call(function, t);
        return new PromiseImpl<>(valueHolder);
    }

    private <T> Promise<R> doProcessing(Function<T, R> function, ValueHolder<T> argsValueHolder) {
        AsyncProcessor<R> asyncProcessor = new AsyncProcessor<>();
        ValueHolder<R> valueHolder = asyncProcessor.call(function, argsValueHolder);
        return new PromiseImpl<>(valueHolder);
    }

    @Override
    public <T> void thenAccept(Consumer<T> consumer ,T t) {
        AsyncProcessor<R> asyncProcessor = new AsyncProcessor<>();
        asyncProcessor.call(consumer,t);
    }

    @Override
    public <T> void thenAccept(Consumer<T> consumer, ValueHolder<T> valueHolder) {
        AsyncProcessor<R> asyncProcessor = new AsyncProcessor<>();
        asyncProcessor.call(consumer,valueHolder);
    }


    public ValueHolder<R> getValueHolder() {
        return valueHolder;
    }
}
