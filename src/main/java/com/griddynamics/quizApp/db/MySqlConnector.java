package com.griddynamics.quizApp.db;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class MySqlConnector {
    
    @Value("${spring.datasource.url}")
    private String dbUrl;

    @Value("${spring.datasource.username}")
    private String login;

    @Value("${spring.datasource.password}")
    private String pass;
    
    @SneakyThrows
    public Connection getConnection() {
        return DriverManager.getConnection(dbUrl, login, pass);
    }
}
