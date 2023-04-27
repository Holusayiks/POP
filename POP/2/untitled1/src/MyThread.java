public class MyThread implements Runnable {
    private final int id;
    private final int firstBound;
    private final int secondBound;
    private final Controller Controller;

    public MyThread(int id, int firstBound, int secondBound, Controller Controller) {
        this.id = id;
        this.firstBound = firstBound;
        this.secondBound = secondBound;
        this.Controller = Controller;
    }

    @Override
    public void run() {
        int min = Controller.MinPath(firstBound, secondBound);
        Controller.SaveMin(id, min);
    }
}