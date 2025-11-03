package lv.javaguru.travel.insurance.loadtesting;

import java.util.ArrayList;
import java.util.List;

public class RestCallExample {

    public static void main(String[] args) {

        LoadTestingStatistic statisticV1 = new LoadTestingStatistic();
        LoadTestingStatistic statisticV2 = new LoadTestingStatistic();
        warmUp(statisticV1, statisticV2);

        int numThreads = 50;
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < numThreads; i++) {
            Thread thread1 = new Thread(new V1Call(statisticV1), "V1Thread" + i);
            Thread thread2 = new Thread(new V2Call(statisticV2), "V2Thread" + i);

            threads.add(thread1);
            threads.add(thread2);

            thread1.start();
            thread2.start();
        }

        threads.forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        System.out.println("=== Results for v1 ===");
        System.out.println("Average: " + statisticV1.getAverageTime() + " ms");
        System.out.println("Min: " + statisticV1.getMinTime() + " ms");
        System.out.println("Max: " + statisticV1.getMaxTime() + " ms");

        System.out.println("=== Results for v2 ===");
        System.out.println("Average: " + statisticV2.getAverageTime() + " ms");
        System.out.println("Min: " + statisticV2.getMinTime() + " ms");
        System.out.println("Max: " + statisticV2.getMaxTime() + " ms");
    }

    private static void warmUp(LoadTestingStatistic statisticV1, LoadTestingStatistic statisticV2) {
        System.out.println("Warming up the API before tests (v1 and v2)");
        new V1Call(statisticV1).run();
        new V2Call(statisticV2).run();
        System.out.println("Warm-up is complete, let's start the load");
    }

}



