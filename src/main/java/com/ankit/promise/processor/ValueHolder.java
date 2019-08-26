package com.ankit.promise.processor;

public class ValueHolder<R> {

    private R returnValue;


    private Thread valueSetterThread;

     ValueHolder(Thread valueSetterThread) {
        this.valueSetterThread = valueSetterThread;

    }

     ValueHolder() {

    }


    void setValue(R returnVal) {
        this.returnValue = returnVal;
    }

    public Thread getValueSetterThread() {
        return valueSetterThread;
    }


    void setValueSetterThread(Thread valueSetterThread) {
        this.valueSetterThread = valueSetterThread;
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
