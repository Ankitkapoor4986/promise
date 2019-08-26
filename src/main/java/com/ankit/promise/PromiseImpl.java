package com.ankit.promise;

import com.ankit.promise.processor.ThreadRunner;
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

    private <T> Promise<R> doProcessing(Function<T, R> function, T t) {
        ThreadRunner<R> threadRunner = new ThreadRunner<>();
        ValueHolder<R> valueHolder = threadRunner.call(function, t);
        return new PromiseImpl<>(valueHolder);
    }

    @Override
    public <T> void thenAccept(Consumer<T> consumer ,T t) {
        ThreadRunner<R> threadRunner = new ThreadRunner<>();
        threadRunner.call(consumer,t);
    }


    public ValueHolder<R> getValueHolder() {
        return valueHolder;
    }
}
