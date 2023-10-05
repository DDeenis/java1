package step.learning.async;

import java.util.Locale;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

public class TaskDemo {
    private final ExecutorService threadPool = Executors.newFixedThreadPool(6);

    public void run() {
        long t1 = System.nanoTime();
        Future<String> task1 = threadPool.submit(new Callable<String>() {
            @Override
            public String call() throws InterruptedException {
                System.out.println("Task 1 start");
                Thread.sleep(1000);
                return "Task 1 finish";
            }
        });

        for (int i = 0; i < 10; i++) {
            printNumber(i + 10);
        }

        CompletableFuture
                .supplyAsync(supplier)
                .thenApply(string -> string + " continuation 1")
                .thenApply(string -> string + " continuation 2")
                .thenAccept((s) -> System.out.println(s + " accepted"));

        try {
            String res = task1.get();
            System.out.println(res);
            //System.out.println(supplyTask.get());
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }

        try {
            threadPool.shutdown();
            threadPool.awaitTermination(1, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        long t2 = System.nanoTime();

        System.out.printf("Main finish: %f%n", (t2 - t1) / 1e9);
    }

    private Future<?> printNumber(int num) {
        return threadPool.submit(() -> {
            try {
                System.out.printf("Task started for number %d%n", num);
                Thread.sleep(1000);
                System.out.printf("Number: %d%n", num);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private Supplier<String> supplier = () -> {
        System.out.println("Supply 1 start");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return "Supply 1 finish";
    };

    private double sum;
    private final Object sumLocker = new Object();
    private final AtomicInteger valuesToCalculate = new AtomicInteger();

    public void dzMonths() {
        sum = 100.0;
        final int months = 12;
        valuesToCalculate.set(months);
        for (int i = 0; i < months; i++) {
            int month = i;
            threadPool.submit(() -> {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ignore) {
                }

                double p = 0.1;
                double localSum;
                synchronized (sumLocker) {
                    localSum = sum *= 1.0 + p;
                }

                System.out.printf(Locale.US, "Month: %02d | Percent: %.2f | Sum: %.2f%n", month, p, localSum);
                if(valuesToCalculate.decrementAndGet() == 0) {
                    System.out.printf(Locale.US, "----------%ntotal: %.2f%n", sum);
                }
            });
        }

        threadPool.shutdown();
    }
}
