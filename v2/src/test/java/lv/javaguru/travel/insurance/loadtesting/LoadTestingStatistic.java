package lv.javaguru.travel.insurance.loadtesting;

import java.util.ArrayList;
import java.util.List;

public class LoadTestingStatistic {

    private List<Long> executionTimes = new ArrayList<>();

    public synchronized void addExecutionTime(Long time) {
        executionTimes.add(time);
    }

    public synchronized double getAverageTime() {
        return executionTimes.stream()
                .mapToLong(Long::longValue)
                .average()
                .orElse(0);
    }

    public synchronized long getMinTime() {
        return executionTimes.stream()
                .mapToLong(Long::longValue)
                .min()
                .orElse(0);
    }

    public synchronized long getMaxTime() {
        return executionTimes.stream()
                .mapToLong(Long::longValue)
                .max()
                .orElse(0);
    }
}
