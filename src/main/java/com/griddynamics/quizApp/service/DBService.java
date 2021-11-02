package com.griddynamics.quizApp.service;

import com.griddynamics.quizApp.db.MySqlConnector;
import com.griddynamics.quizApp.model.GameRecord;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Component
public class DBService {

    @Autowired
    private MySqlConnector connector;

    private final String saveDataSql = "INSERT INTO results (player_name, player_result) VALUES ('%s', %s)";
    private final String getTop5Games = "SELECT player_name, player_result FROM results ORDER BY player_result DESC LIMIT 5";

    @SneakyThrows
    public void saveDataToDb(String name, double score) {
        connector
                .getConnection()
                .prepareStatement(String.format(saveDataSql, name, String.format("%,.3f", score)))
                .execute();
    }

    @SneakyThrows
    public List<GameRecord> getTop5Games() {
        ArrayList<GameRecord> result = new ArrayList<>();
        ResultSet rs = connector
                .getConnection()
                .prepareStatement(getTop5Games)
                .executeQuery();
        
        while (rs.next()) {
            result.add(GameRecord.builder()
                    .name(rs.getString(1))
                    .score(rs.getDouble(2))
                    .build());
        }
        return result;
    }
}
