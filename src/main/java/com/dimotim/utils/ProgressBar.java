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

                System.out.println(tag+" "+formatProgress(startTime,System.currentTimeMillis(),steps,steps-left));
            }
        });
        thread.setDaemon(true);
        thread.start();
    }

    public void nextStep(){
        int left=stepsLeft.decrementAndGet();
        if(left==0) {
            System.out.println(tag + " completed, time="+prettifyMillis(System.currentTimeMillis() - startTime));
        }
    }

    public static String formatProgress(long startTime, long curTime, int steps, int curStep){
        long time=curTime-startTime;
        if(curStep==0){
            return curStep+"/"+steps+" work time: "+prettifyMillis(time);
        }else {
            long leftTime=(long) (1.0*time/curStep*(steps-curStep));
            return curStep+"/"+steps+", "+(int) (curStep*100.0/steps)+"%,"+" work time: "+prettifyMillis(time)+", left time: "+prettifyMillis(leftTime);
        }
    }

    public static String prettifyMillis(long millis){
        final long s=1000;
        final long m=60*s;
        final long h=60*m;
        final long d=24*h;

        if(millis>=d){
            return "("+millis/d+"d "+(millis%d)/h+"h)";
        }else if(millis>=h){
            return "("+millis/h+"h "+(millis%h)/m+"m)";
        }else if(millis>=m){
            return "("+millis/m+"m "+(millis%m)/s+"s)";
        }else if(millis>=s){
            return "("+millis/s+"s "+millis%s+"ms)";
        }else {
            return "("+millis+"ms)";
        }
    }
}