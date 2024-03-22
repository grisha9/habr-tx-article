package com.example.demo;

import java.util.LongSummaryStatistics;
import java.util.function.Supplier;
import java.util.stream.LongStream;

abstract class TestUtil {

    public static LongSummaryStatistics performTest(Supplier<?> consumer, int iterationCount) {
        //warm up
        LongStream.range(0, iterationCount).forEach(i -> consumer.get());
        //test
        return LongStream.range(0, iterationCount)
                .map(i -> {
                    long l = System.currentTimeMillis();
                    consumer.get();
                    return System.currentTimeMillis() - l;
                })
                .summaryStatistics();
    }
}
