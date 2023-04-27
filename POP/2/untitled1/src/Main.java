import java.util.Random;

public class Main {
    public static void main(String[] args) {
        int threadCount = 3;
        int[] array = new int[10000000];
        Random random = new Random();



        for (int i = 0; i < array.length; i++) {
            array[i] = random.nextInt(1,10000000);
        }
        array[random.nextInt(array.length)] = (random.nextInt(-100,-10));

        long start = System.currentTimeMillis();
        Controller controller = new Controller(threadCount, array);
        long finish = System.currentTimeMillis();
        long timeElapsed = finish - start;
        System.out.println("Time: " + timeElapsed);
        System.out.println("Min element:");
        System.out.println(controller.getMin());


    }

}

