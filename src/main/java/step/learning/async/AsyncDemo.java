package step.learning.async;

public class AsyncDemo {
    public void run() {
        System.out.println("Async demo");
        //multiThreadDemo();
        dzSequence();
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
}
