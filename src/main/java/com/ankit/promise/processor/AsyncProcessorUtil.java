package com.ankit.promise.processor;

public class AsyncProcessorUtil {

    static void joinThread(Thread thread) {
        try {
            if(thread.isAlive()) {
                thread.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
