package lv.javaguru.travel.insurance.loadtesting;

public class V2Call extends CommonCall {

    private LoadTestingStatistic statistic;

    public V2Call(LoadTestingStatistic statistic) {
        this.statistic = statistic;
    }

    @Override
    public void run() {
        long start = System.currentTimeMillis();
        processVersion("v2");
        long end = System.currentTimeMillis();

        statistic.addExecutionTime(end - start);
    }
}
