package com.ankit.promise;

import java.util.function.Consumer;
import java.util.function.Function;

public class Block8Promise implements Promise {


    @Override
    public <T, R> Promise then(Function<T, R> function) {
        return null;
    }

    @Override
    public <T> void thenAccept(Consumer<T> consumer) {

    }
}
