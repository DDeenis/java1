package step.learning.async;

import java.util.Locale;

public class AsyncDemo {
    public void run() {
        System.out.println("Async demo");

        sum = 100.0;
        final int months = 12;
        activeThreadsCount = months;
        Thread[] threads = new Thread[months];
        for (int i = 0; i < months; i++) {
            threads[i] = new Thread(new MonthRate(i + 1));
            threads[i].start();
        }

        for(int i = 0; i < maxNumbers; i++) {
            new Thread(new NumberPrinter(i + 1)).start();
        }
    }

    private void multiThreadDemo() {
        Thread thread = new Thread(() -> {
            try {
                System.out.println("Thread start");
                Thread.sleep(2000);
                System.out.println("Thread finished");
            } catch (InterruptedException e) {
                System.err.printf("Sleep exception: %s%n", e.getMessage());
            }
        });
        thread.start();

        try {
            thread.join();
        } catch (InterruptedException e) {
            System.err.printf("Failed to join thread: %s%n", e.getMessage());
        }

        new Thread(() -> System.out.println("Thread 2 start")).start();
        new Thread(this::methodForThread).start();

        System.out.println("multiThreadDemo() finished");
    }

    private void methodForThread() {
        System.out.println("methodForThread start");
    }

    private void dzSequence() {
        Thread thread1 = new Thread(() -> {
            try {
                System.out.println("1 start");
                Thread.sleep(3000);
                System.out.println("1 finish");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        Thread thread2 = new Thread(() -> {
            try {
                System.out.println("2 start");
                Thread.sleep(1000);
                System.out.println("2 finish");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        Thread thread3 = new Thread(() -> {
            try {
                System.out.println("3 start");
                Thread.sleep(1000);
                System.out.println("3 finish");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        thread1.start();
        thread2.start();

        try {
            thread2.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        thread3.start();
        try {
            thread3.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("final");
    }

    private int activeThreadsCount;
    private double sum;
    private final Object sumLocker = new Object();
    private final Object atcLocker = new Object();
    class MonthRate implements Runnable {
        private final int month;

        public MonthRate(int month) {
            this.month = month;
        }

        @Override
        public void run() {
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

            synchronized (atcLocker) {
                --activeThreadsCount;
                if(activeThreadsCount == 0) {
                    System.out.printf(Locale.US, "----------%ntotal: %.2f%n", sum);
                }
            }
        }
    }

    private final Object numbersLocker = new Object();
    private final int maxNumbers = 9;
    class NumberPrinter implements Runnable {
        private final int number;

        public NumberPrinter(int number) {
            this.number = number;
        }

        @Override
        public void run() {
            System.out.println(number);

            synchronized (numbersLocker) {
                if(number == maxNumbers) {
                    System.out.printf("Last thread finished with number: %d%n", number);
                }
            }
        }
    }
}
