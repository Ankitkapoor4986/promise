package com.ankit.promise;

import java.util.function.Consumer;
import java.util.function.Function;

public interface Promise {

    <T, R> Promise then(Function<T, R> function);

    <T> void thenAccept(Consumer<T> consumer);
}
