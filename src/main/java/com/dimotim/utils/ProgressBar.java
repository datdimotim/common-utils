package com.dimotim.utils;

import java.util.concurrent.atomic.AtomicInteger;

import static com.dimotim.utils.Safe.safe;

public class ProgressBar{
    private final String tag;
    private final long startTime=System.currentTimeMillis();
    private final AtomicInteger stepsLeft;

    public ProgressBar(String tag, int steps, long updatePeriodMs){
        this.tag=tag;
        stepsLeft=new AtomicInteger(steps);

        Thread thread=new Thread(()->{
            while (true) {
                safe(() -> Thread.sleep(updatePeriodMs));
                int left=stepsLeft.get();
                if(left==0)return;

                long time=System.currentTimeMillis()-startTime;
                int stepsComplete=steps-left;
                if(stepsComplete==0){
                    System.out.println(tag+" "+stepsComplete+"/"+steps+" work time="+time+"ms");
                }else {
                    long leftTime=(long) (1.0*time/stepsComplete*left);
                    System.out.println(tag+" "+stepsComplete+"/"+steps+" work time="+time+"ms, left time: "+leftTime+"ms");
                }
            }
        });
        thread.setDaemon(true);
        thread.start();
    }

    public void nextStep(){
        int left=stepsLeft.decrementAndGet();
        if(left==0) {
            System.out.println(tag + " completed, time=" + (System.currentTimeMillis() - startTime) + "ms");
        }
    }
}