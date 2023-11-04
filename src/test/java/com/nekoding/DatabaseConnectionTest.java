package com.nekoding;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnectionTest {

    @BeforeAll
    static void beforeAll() {

    }

    @Test
    void testRegisterDriver() {
        try {
            Driver driver = new org.postgresql.Driver();
            DriverManager.registerDriver(driver);
        } catch (SQLException exception) {
            Assertions.fail(exception);
        }
    }

    @Test
    void testConnectDb() {
        String dbURL = System.getenv("DB_URL");
        String dbUser = System.getenv("DB_USER");
        String dbPass = System.getenv("DB_PASS");

        try {
            Driver pgsqlDriver = new org.postgresql.Driver();
            DriverManager.registerDriver(pgsqlDriver);

            Connection connection = DriverManager.getConnection(dbURL, dbUser, dbPass);
            System.out.println("Sukses membuat koneksi ke database");

            connection.close();
            System.out.println("Koneksi database berhasil ditutup");
        } catch (SQLException exception) {
            Assertions.fail(exception);
        }
    }
}
