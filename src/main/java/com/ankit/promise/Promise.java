package com.ankit.promise;

import java.util.function.Consumer;
import java.util.function.Function;

public interface Promise<R> {

    <T> Promise<R> then(Function<T, R> function,T t);


    <T> void thenAccept(Consumer<T> consumer);
}
