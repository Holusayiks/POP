package com.company;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;

public class Main {
    public static void main(String[] args)
    {

        int threads_number=25;
        int[] times= new int[threads_number];
        for(int i=0;i<threads_number;i++)
        {
            int time = ThreadLocalRandom.current().nextInt(0, 25);
            times[i]=time;
        }
        BreakThread breakThread = new BreakThread(times);
        new Thread(breakThread).start();
        for (int i = 0; i < threads_number; i++)
        {
            new Thread(new MyThread(i, breakThread)).start();
        }


    }
}
