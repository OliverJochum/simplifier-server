package com.oljochum.simplifier_server.analyse;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;


import org.springframework.stereotype.Service;

@Service
public class DLexDBService {

    private static final String URL = "jdbc:duckdb:/Users/U462343/bachelors_thesis/duck-db-playground/dlex/data/dlex.duckdb";

    public Connection getConnection() throws SQLException {
        Properties props = new Properties();
        props.setProperty("duckdb.read_only", "true");
        return DriverManager.getConnection(URL, props);
    }

    public int querySyllableCount(String word) {
        String sql = """
            SELECT typ_syls_cnt
            FROM "typposlem"
            WHERE typ_cit = ?
        """;

        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, word);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("typ_syls_cnt");
                }
            }
            return -1; 
        } catch (SQLException e) {
            throw new RuntimeException("Failed to query syllable count", e);
        }
    }
}