package com.ankit.promise;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

public class ThreadValueReturner<R> {

    private R returnVal;
    private Thread thread ;


    public <T>  void call(Function<T, R> functionToExecute, T t) {

        Runnable runnable = () -> {
            returnVal = functionToExecute.apply(t);
        };
        startThread(runnable);
      
    }

    public void call(Supplier<R> supplier) {

        Runnable runnable = () -> {
            returnVal = supplier.get();
        };

        startThread(runnable);
       
    }

    private void startThread(Runnable runnable) {
        thread = new Thread(runnable);
        thread.start();
    }

    public R get(){
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //while(!Optional.ofNullable(returnVal).isPresent());
        
        return returnVal;
    }

}
