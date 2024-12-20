package com.nkm.mypracticespring.test.multithreads.send_mail_notify;

import lombok.SneakyThrows;

import java.util.Random;
import java.util.concurrent.*;

public class Main {

    /**
     * Demo Hệ Thống Gửi Email Thông Báo
     * Mô Tả
     * Bạn đang phát triển một hệ thống gửi email thông báo cho khách hàng. Yêu cầu như sau:
     * Có 100 khách hàng cần gửi email.
     * Sử dụng ExecutorService với thread pool 5 thread để gửi email.
     * Mỗi email cần mất từ 2 đến 5 giây để gửi (giả lập bằng Thread.sleep).
     * In ra log cho biết email nào được gửi và bởi thread nào, ví dụ:
     * "Email sent to customer X by Thread-Y".
     * Yêu Cầu Nâng Cao
     * Thêm retry logic: Nếu gửi thất bại (giả lập 20% thất bại), thực hiện gửi lại tối đa 3 lần.
     * Ghi lại log những khách hàng nào gửi thất bại hoàn toàn sau 3 lần thử.
     */

    @SneakyThrows
    public static void main(String[] args) {
        BlockingQueue<String> mailQueue = new LinkedBlockingQueue<>();
        for (int i = 0; i < 10; i++) {
            mailQueue.put(String.format("test_mail%s@gmail.com", i));
        }

        int fixedThread = 5;
        ExecutorService executorService = Executors.newFixedThreadPool(fixedThread);

        for (int i = 0; i < 100; i++) {
            executorService.submit(() -> {
                String email = mailQueue.poll();
                if (email == null) {
                    return;
                }
                Main main = new Main();
                main.sentEmail(email, 0);
                System.out.printf("Email sent to \"%s\" by Thread \"%s\"%n", email, Thread.currentThread().getName());
            });
        }

        // Tắt ExecutorService sau khi hoàn thành các task
        executorService.shutdown();

        try {
            if (!executorService.awaitTermination(20, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
        } finally {
            executorService.close();
        }

        System.out.println("Email sending process completed.");
    }

    @SneakyThrows
    public void sentEmail(String email, int count) {
        if (count > 3) {
            return;
        }

        int chance = 2 + new Random().nextInt(4);
        TimeUnit.SECONDS.sleep(chance);

        int failureRatePercent = 1 + new Random().nextInt(100);
        if (failureRatePercent <= 20) {
            System.out.println("Resent email " + email);
            count = count + 1;
            sentEmail(email, count);
        }
    }

}
