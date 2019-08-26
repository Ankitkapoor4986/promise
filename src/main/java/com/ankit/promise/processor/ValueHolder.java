package com.ankit.promise.processor;

public class ValueHolder<R> {

    private R returnValue;


    private Thread valueSetterThread;


     ValueHolder() {

    }


    void setValue(R returnVal) {
        this.returnValue = returnVal;
    }


    void setValueSetterThread(Thread valueSetterThread) {
        this.valueSetterThread = valueSetterThread;
    }


    public R get(){
        AsyncProcessorUtil.joinThread(valueSetterThread);
        return returnValue;
    }


}
