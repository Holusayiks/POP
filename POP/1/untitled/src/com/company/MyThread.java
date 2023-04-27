package com.company;

public class MyThread extends Thread{
    private final int id;

    private final BreakThread breakThread;


    public MyThread(int id, BreakThread breakThread) {
        this.id = id;
        this.breakThread = breakThread;
    }
    @Override
    public void run() {
        long sum = 0;
        long step=0;

        do{
            sum+=2;
            step++;

        } while (!breakThread.isCanBreak(id));
        System.out.println("Thread id" + id + "\tsum:" + sum + "\tstep:"+step);
    }
}

