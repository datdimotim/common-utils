package com.dimotim.utils;

import java.util.concurrent.Callable;

public class Safe {
    public interface Action{
        void run()throws Exception;
    }

    public static void safe(Action action){
        try {
            action.run();
        }catch (Exception e){
            System.err.println("Safe: caught exception");
            e.printStackTrace();
        }
    }

    public static <T> T safe(Callable<T> t, T defVal){
        try {
            return t.call();
        }catch (Exception e){
            System.err.println("Safe: caught exception");
            e.printStackTrace();
            return defVal;
        }
    }
}
