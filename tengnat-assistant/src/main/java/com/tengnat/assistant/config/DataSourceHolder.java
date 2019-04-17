package com.tengnat.assistant.config;

public class DataSourceHolder {
    private static final ThreadLocal<String> contextHolder = new ThreadLocal<>();

    public static void setDataSourceType(Object dataSourceType) {
        contextHolder.set(String.valueOf(dataSourceType));
    }

    public static String getDataSourceType() {
        return contextHolder.get();
    }

    public static void clearDataSourceType() {
        contextHolder.remove();
    }
}
