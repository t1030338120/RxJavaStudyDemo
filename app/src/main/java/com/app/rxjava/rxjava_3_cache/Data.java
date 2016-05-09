package com.app.rxjava.rxjava_3_cache;

/**
 * Simple data class, keeps track of when it was created
 * so that it knows when the its gone stale.
 * 简单的数据类，根据时间判断数据的有效性。
 */
public class Data {

    private static final long STALE_MS = 2 * 1000; // Data is stale after 5 seconds

    final String value;

    final long timestamp;

    public Data(String value) {
        this.value = value;
        this.timestamp = System.currentTimeMillis();
    }

    public boolean isUpToDate() {
        return System.currentTimeMillis() - timestamp < STALE_MS;
    }
}
