package Database.Generic;

import org.h2.jdbcx.JdbcConnectionPool;

import java.sql.Connection;
import java.sql.SQLException;

public class JdbcConnection {


        private static final String username = "admin";
        private static final String password = "admin";
        private static JdbcConnectionPool pool;

        public static Connection getConnection(String url) {
            if (pool == null) {
                pool = JdbcConnectionPool.create(url, username, password);
            }
            try {
                return pool.getConnection();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

