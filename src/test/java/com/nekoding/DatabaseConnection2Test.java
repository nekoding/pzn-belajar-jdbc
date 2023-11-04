package com.nekoding;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection2Test {
    static {
        try {
            Driver pgsqlDriver = new org.postgresql.Driver();
            DriverManager.registerDriver(pgsqlDriver);
        } catch (SQLException exception) {
            System.out.println("failed initialize db driver");
        }
    }

    @Test
    void testConnectionWithResource() {
        String dbURL = "jdbc:postgresql://localhost:5432/java_jdbc";
        String dbUser = "nekoding";
        String dbPass = "nekoding";

        try (Connection connection = DriverManager.getConnection(dbURL, dbUser, dbPass);){
            System.out.println("Sukses membuat koneksi ke database");
        } catch (SQLException exception) {
            Assertions.fail(exception);
        }
    }
}
