import java.util.ArrayList;

public class Controller {
    private final int threadCount;
    private final int[] array;
    private final int[] minIndex;
    private int finishedThread;
    private final int[] minElement;

    public Controller(int threadCount, int[] array) {
        this.threadCount = threadCount;
        this.minIndex = new int[threadCount];
        this.array = array;
        this.minElement = new int[threadCount];
        this.finishedThread = 0;
        threadMin();
    }


    private void threadMin(){
        MyThread[] myThread = new MyThread[threadCount];

        for (int i = 0; i < myThread.length; i++) {
            myThread[i] = new MyThread(i,i * array.length / threadCount,(i + 1) * array.length / threadCount,this);
        }
        for (MyThread thread : myThread) {
            new Thread(thread).start();
        }
    }




    synchronized public void SaveMin(int id, int minElem){
        minElement[id] = minElem;
        minIndex[id]=id;
        finishedThread++;
        notify();
    }



    public synchronized int getMin() {
        while (getFinishedThread() < threadCount){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return  findMinInArray(minElement);
    }

    private int getFinishedThread() {
        return finishedThread;
    }

    public int MinPath(int firstBound, int secondBound) {
        int min = array[firstBound];

        for (int i = firstBound; i < secondBound; i++) {
            if (array[i] < min) {
                min = array[i];
            }
        }

        return  min;
    }

    private int findMinInArray(int[] array) {
        int min = array[0];

        for (int num : array) {
            if (num < min) {
                min = num;
            }
        }

        return  min;
    }
}
