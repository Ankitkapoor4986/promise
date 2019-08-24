package com.ankit.promise;

import com.ankit.promise.processor.ThreadRunner;
import com.ankit.promise.processor.ValueHolder;

import java.util.function.Consumer;
import java.util.function.Function;

public class Block8Promise<R> implements Promise<R> {

    private ValueHolder<R> valueHolder;

    public Block8Promise(ValueHolder<R> valueHolder) {
        this.valueHolder = valueHolder;
    }

    public Block8Promise(){

    }

    @Override
    public <T> Promise<R> then(Function<T, R> function, T t) {
        ThreadRunner<R> threadRunner = new ThreadRunner<>();
        ValueHolder<R> valueHolder = threadRunner.call(function, t);
        return new Block8Promise<>(valueHolder);
    }

    @Override
    public <T> void thenAccept(Consumer<T> consumer ,T t) {
            consumer.accept(t);
    }


    public ValueHolder<R> getValueHolder() {
        return valueHolder;
    }
}
