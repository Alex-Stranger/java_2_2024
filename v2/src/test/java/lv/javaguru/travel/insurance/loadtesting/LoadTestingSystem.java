package lv.javaguru.travel.insurance.loadtesting;

import com.google.common.base.Stopwatch;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class LoadTestingSystem {

    public static void main(String[] args) {
        new LoadTestingSystem().executeForAMinute(10, 200);
    }

    public void executeForAMinute(int parallelThreadCount, int requestCount) {
        int executionTimeInMillis = 60000;
        long intervalBetweenRequestsInMillis = executionTimeInMillis / requestCount;

        Stopwatch stopwatch = Stopwatch.createStarted();

        LoadTestingStatistic statisticV2 = new LoadTestingStatistic();
        warmUp(statisticV2);
        ExecutorService executor = Executors.newFixedThreadPool(parallelThreadCount);

        for (int i = 1; i <= requestCount; i++) {
            for (int j = 1; j <= parallelThreadCount; j++) {
                executor.submit(new V2Call(statisticV2));
            }
            try {
                Thread.sleep(intervalBetweenRequestsInMillis);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        executor.shutdown();

        try {
            if (!executor.awaitTermination(70, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }

        stopwatch.stop();
        long elapsedTimeSeconds = stopwatch.elapsed(TimeUnit.SECONDS);

        long totalTasks = (long) requestCount * parallelThreadCount;
        long rps = totalTasks / elapsedTimeSeconds;

        System.out.println("=== Test Results for v2 using mySQL database===");
        System.out.println("Total tasks  = " + totalTasks);
        System.out.println("Execution time (s) = " + elapsedTimeSeconds);
        System.out.println("V2 average time (ms) = " + statisticV2.getAverageTime());
        System.out.println("V2 min time (ms) = " + statisticV2.getMinTime());
        System.out.println("V2 max time (ms) = " + statisticV2.getMaxTime());
        System.out.println("Requests per sec  = " + rps);
    }

    private static void warmUp(LoadTestingStatistic statisticV2) {
        System.out.println("Warming up the API before test (v2)");
        new V2Call(statisticV2).run();
        System.out.println("Warm-up is complete, let's start the load");
    }
}
