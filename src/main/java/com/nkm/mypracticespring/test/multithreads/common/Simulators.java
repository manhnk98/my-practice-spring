package com.nkm.mypracticespring.test.multithreads.common;

import lombok.SneakyThrows;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Simulators {

    private static final Random random = new Random();

    @SneakyThrows
    public static void delayTask() {
        int chance = 2 + random.nextInt(4);
        TimeUnit.SECONDS.sleep(chance);
    }

    public static boolean taskExecSuccess(int failureRatePercent) {
        int chance = 1 + random.nextInt(99);
        return chance > failureRatePercent;
    }

}
