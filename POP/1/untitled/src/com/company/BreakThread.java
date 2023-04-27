package com.company;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;

public class BreakThread implements Runnable{

    public int [] times;
    public int [] id;
    public boolean[] isStop;
     public BreakThread(int[]times)
     {
         this.times=times;
         this.id= new int[times.length];
         this.isStop=new boolean[times.length];
     }
    @Override
    public void run()
    {
        for (int i=0;i< times.length ;i++)
        {
            id[i]=i;
        }

        for (int i=0;i< times.length-1 ;i++)
        {

            if (times[i]>times[i+1])
            {
                int temp=times[i];
                int temp1=id[i];

                times[i]=times[i+1];
                id[i]=id[i+1];

                times[i+1]=temp;
                id[i+1]=temp1;

                i=-1;
            }
        }
            for(int i=0;i< times.length;i++)
            {
                if(times[i]==0)
                {
                    isStop[id[i]]=true;
                }
                else
                {
                    int minTime=times[i];
                    for(int j=i;j< times.length;j++)
                    {
                        times[j]=times[j]-minTime;

                    }
                    try {
                        Thread.sleep( (long)minTime * 1000);
                        i--;
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                }

            }


    }
    synchronized public boolean isCanBreak(int id) {
        return isStop[id];
    }
}
