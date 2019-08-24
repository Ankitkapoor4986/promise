package com.ankit.promise.processor;

public class ValueHolder<R> {

    private R returnValue;
    private Thread valueSetterThread;

    public ValueHolder(Thread thread) {
        this.valueSetterThread = thread;

    }


     void setValue(R returnVal) {
        this.returnValue = returnVal;
    }



    public R get(){
        joinValueSetterThread();
        return returnValue;
    }

    private void joinValueSetterThread() {
        try {
            if(valueSetterThread.isAlive()) {
                valueSetterThread.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
