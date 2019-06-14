package com.app.api;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public final class SQLInjectionUtils {

    private static final List<String> maliciousDataSamples = Arrays.asList(
            "select adf from abc",
            "insert into abcd",
            "update abcd",
            "delete from abcd",
            "upsert abcd",
            "call abcd",
            "rollback ",
            "create table abc",
            "drop table",
            "drop view",
            "alter table abc",
            "truncate table abc",
            "desc abc");

    private static final List<String> sqlDisruptiveDataSamples = Arrays.asList(
            "--",
            "/*",
            "*/",
            ";",
            "root -- abcd",
            "abcd /* adf */ adf");

    public static String generateRandomDangerousQuery() {
        return getRandomSqlDisruptiveDataSample() + getRandomMaliciousDataSample();
    }

    private static String getRandomMaliciousDataSample() {
        return maliciousDataSamples.get(new Random().nextInt(maliciousDataSamples.size()));
    }

    private static String getRandomSqlDisruptiveDataSample() {
        return sqlDisruptiveDataSamples.get(new Random().nextInt(sqlDisruptiveDataSamples.size()));
    }


}
