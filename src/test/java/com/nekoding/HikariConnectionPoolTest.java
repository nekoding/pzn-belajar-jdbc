package com.nekoding;

import com.nekoding.util.ConnectionUtil;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.*;

public class HikariConnectionPoolTest {

    @Test
    void testConnectDatabase() {
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

        // meminjam koneksi dari koneksi pool
        try {
            HikariDataSource hikariDataSource = new HikariDataSource(hikariConfig);
            Connection connection = hikariDataSource.getConnection();
            System.out.println("Berhasil mengambil koneksi");

            connection.close();
            System.out.println("Sukses mengembalikan koneksi ke pool");

            hikariDataSource.close();
            System.out.println("Sukses menutup pool");
        } catch (SQLException exception) {
            Assertions.fail(exception);
        }
    }

    @Test
    void testConnectionUtil() {
        try (Connection connection = ConnectionUtil.getDataSource().getConnection()) {
            System.out.println("berhasil mengambil koneksi");
        } catch (SQLException exception) {
            Assertions.fail(exception);
        }
    }

    @Test
    void testExecuteInsert() {
        try (Connection connection = ConnectionUtil.getDataSource().getConnection()) {
            Statement stmt = connection.createStatement();

            String sql = """
                    INSERT INTO customers(id, name, email) 
                    VALUES ('enggar', 'enggar', 'enggar@mail.com')
                    """;

            int update = stmt.executeUpdate(sql);
            System.out.println("Rows affected : " + update);

            stmt.close();
        } catch (SQLException exception) {
            Assertions.fail(exception);
        }
    }

    @Test
    void testExecuteDelete() {
        try (Connection connection = ConnectionUtil.getDataSource().getConnection()) {
            Statement stmt = connection.createStatement();

            String sql = """
                    DELETE from customers;
                    """;

            int update = stmt.executeUpdate(sql);
            System.out.println("Rows affected : " + update);

            stmt.close();
        } catch (SQLException exception) {
            Assertions.fail(exception);
        }
    }

    @Test
    void testResultSet() {
        try (Connection connection = ConnectionUtil.getDataSource().getConnection()) {
            Statement stmt = connection.createStatement();

            String sql = """
                    SELECT * from customers;
                    """;

            ResultSet resultSet = stmt.executeQuery(sql);

            while (resultSet.next()) {
                System.out.println(resultSet.getString("id"));
                System.out.println(resultSet.getString("name"));
                System.out.println(resultSet.getString("email"));
            }

            stmt.close();
        } catch (SQLException exception) {
            Assertions.fail(exception);
        }
    }

    @Test
    void testPrepareStatement() {
        try (Connection connection = ConnectionUtil.getDataSource().getConnection()) {
            String sql = """
                    SELECT * from customers where id = ? and name = ?;
                    """;

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, "enggar");
            preparedStatement.setString(2, "enggar");

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                System.out.println(resultSet.getString("id"));
                System.out.println(resultSet.getString("name"));
                System.out.println(resultSet.getString("email"));
            }

            preparedStatement.close();
        } catch (SQLException exception) {
            Assertions.fail(exception);
        }
    }

    @Test
    void testBatchPreparedStatement() {
        try (Connection connection = ConnectionUtil.getDataSource().getConnection()) {
            String sql = "INSERT INTO comments(name, comment) VALUES (?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            for (int i = 0; i < 100; i++) {
                // index start dari 1
                preparedStatement.setString(1, "enggar" + i);
                preparedStatement.setString(2, "lorem ipsum");
                preparedStatement.addBatch();
                preparedStatement.clearParameters();
            }

            preparedStatement.executeBatch();
            preparedStatement.close();
        } catch (SQLException exception) {
            Assertions.fail(exception);
        }
    }

    @Test
    void testBatchStatement() {
        try (Connection connection = ConnectionUtil.getDataSource().getConnection()) {
            String sql = "INSERT INTO comments(name, comment) VALUES ('enggar', 'lorem ipsum dolor sit amet')";
            Statement statement = connection.createStatement();

            for (int i = 0; i < 10; i++) {
                statement.addBatch(sql);
            }

            statement.executeBatch();
            statement.close();
        } catch (SQLException exception) {
            Assertions.fail(exception);
        }
    }

    @Test
    void testBatchStatementTransaction() {
        try (Connection connection = ConnectionUtil.getDataSource().getConnection()) {
            // mematikan autocommit
            connection.setAutoCommit(false);

            String sql = "INSERT INTO comments(name, comment) VALUES ('enggar', 'lorem ipsum dolor sit amet')";
            Statement statement = connection.createStatement();

            for (int i = 0; i < 10; i++) {
                statement.addBatch(sql);
            }

            statement.executeBatch();

            // commit transaction
            connection.commit();

            // rollback tranction
            // connection.rollback();

            statement.close();
        } catch (SQLException exception) {
            Assertions.fail(exception);
        }
    }
}
