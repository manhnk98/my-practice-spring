package com.nkm.mypracticespring.test.multithreads.send_mail_notify;
import java.util.Random;
import java.util.concurrent.*;

public class EmailNotificationSystem {
    private static final int TOTAL_CUSTOMERS = 10;
    private static final int THREAD_POOL_SIZE = 5;
    private static final int MAX_RETRIES = 3;
    private static final int FAILURE_RATE = 20; // 20% thất bại

    private static final Random random = new Random();

    public static void main(String[] args) {
        // Tạo thread pool với 5 thread
        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
        Callable<String> callable = () -> {
            return "ManhNK";
        };

        Runnable runnable = () -> {
            System.out.println("ManhNK");
        };

        Future<String> xxx = executorService.submit(callable);

        for (int i = 1; i <= TOTAL_CUSTOMERS; i++) {
            final int customerId = i;
            executorService.submit(() -> sendEmailWithRetries(customerId));
        }

        // Tắt ExecutorService sau khi hoàn thành các task
        executorService.shutdown();
//        try {
//            if (!executorService.awaitTermination(1, TimeUnit.HOURS)) {
//                executorService.shutdownNow();
//            }
//        } catch (InterruptedException e) {
//            executorService.shutdownNow();
//        }
//        finally {
//            executorService.close();
//        }

        System.out.println("Email sending process completed.");
    }

    // Hàm gửi email với retry logic
    private static void sendEmailWithRetries(int customerId) {
        int attempts = 0;
        boolean success = false;

        while (attempts < MAX_RETRIES && !success) {
            attempts++;
            System.out.println("Sending email to customer " + customerId + " (Attempt " + attempts + ")");

            try {
                // Giả lập thời gian gửi email từ 2 đến 5 giây
                int delay = 2000 + random.nextInt(3000);
                Thread.sleep(delay);

                // Giả lập 20% thất bại
                if (random.nextInt(100) < FAILURE_RATE) {
                    throw new Exception("Simulated email sending failure");
                }

                success = true;
                System.out.println("Email sent successfully to customer " + customerId + " by " + Thread.currentThread().getName());

            } catch (Exception e) {
                System.err.println("Failed to send email to customer " + customerId + " (Attempt " + attempts + ")");
            }
        }

        if (!success) {
            System.err.println("Email failed to send to customer " + customerId + " after " + MAX_RETRIES + " attempts.");
        }
    }
}

