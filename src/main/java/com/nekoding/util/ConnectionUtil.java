package com.nekoding.util;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class ConnectionUtil {
    private static final HikariDataSource hikariDataSource;

    static {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setDriverClassName("org.postgresql.Driver");
        hikariConfig.setJdbcUrl("jdbc:postgresql://localhost:5432/java_jdbc?serverTimezone=Asia/Jakarta");
        hikariConfig.setUsername("nekoding");
        hikariConfig.setPassword("nekoding");

        // konfigurasi connection pool
        hikariConfig.setMaximumPoolSize(10); // set maksimal 10 koneksi
        hikariConfig.setMinimumIdle(5); // set 5 koneksi yang idle (jika 10 koneksi tadi idle semua maka sisakan 5 saja, sisanya di close)
        hikariConfig.setIdleTimeout(60_000); // ketika koneksi idle selama 60detik tidak ada yang menggunakan close koneksinya
        hikariConfig.setMaxLifetime(10 * 60_000); // masa hidup koneksi yang masih idle adalah 10menit, jika lebih dari itu maka koneksi akan diperbarui

        hikariDataSource = new HikariDataSource(hikariConfig);
    }

    public static HikariDataSource getDataSource() {
        return hikariDataSource;
    }
}
