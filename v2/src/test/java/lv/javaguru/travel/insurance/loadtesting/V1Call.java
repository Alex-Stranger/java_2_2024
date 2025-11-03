package lv.javaguru.travel.insurance.loadtesting;

public class V1Call extends CommonCall {

    private LoadTestingStatistic statistic;

    public V1Call(LoadTestingStatistic statistic) {
        this.statistic = statistic;
    }

    @Override
    public void run() {
        long start = System.currentTimeMillis();
        processVersion("v1");
        long end = System.currentTimeMillis();

        statistic.addExecutionTime(end - start);

    }
}
