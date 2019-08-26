package com.ankit.promise;

import com.ankit.promise.processor.ValueHolder;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public interface Promise<R> {

    <T> Promise<R> then(Function<T, R> resolve, T t, Function<T, R> reject, Predicate<T> predicate);

    <T> Promise<R> then(Function<T, R> resolve, T t);

    <T> Promise<R> then(Function<T, R> resolve, ValueHolder<T> valueHolder);

    <T> void thenAccept(Consumer<T> consumer, T t);

    <T> void thenAccept(Consumer<T> consumer, ValueHolder<T> valueHolder);

    ValueHolder<R> getValueHolder();
}
