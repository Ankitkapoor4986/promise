package com.ankit.promise;

import java.util.function.Consumer;
import java.util.function.Function;

public class Block8Promise<R> implements Promise<R> {

    ThreadValueReturner<R> threadValueReturner;

    public Block8Promise(ThreadValueReturner<R> threadValueReturner) {
        this.threadValueReturner = threadValueReturner;
    }

    public Block8Promise(){

    }

    @Override
    public <T> Promise<R> then(Function<T, R> function,T t) {

        threadValueReturner = new ThreadValueReturner<>();
        threadValueReturner.call(function,t);

        return new Block8Promise<>(threadValueReturner);
    }

    @Override
    public <T> void thenAccept(Consumer<T> consumer) {

    }
}
