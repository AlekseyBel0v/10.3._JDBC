package com.belov.codeexample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.JdbcClient;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

//ВАРИАНТ РАБОТЫ БЕЗ СПРИНГА
@SpringBootApplication
public class Application {

    public static void main(String[] args) throws SQLException {
        SpringApplication.run(Application.class, args);
        //устанавливаем связь с базой данных
        Connection connection = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/postgres",
                "postgres",
                "postgres");

        PreparedStatement statement = connection.prepareStatement(
                "select id, name from netology.customers"
        );
        ResultSet resultSet = statement.executeQuery();
        List<Customer> customers = new ArrayList<>();
        while (resultSet.next()) {
            long id = resultSet.getInt("id"); //В параметр передаем колонку. Можно также ставить номер колонки
            String name = resultSet.getString("name");
            customers.add(new Customer(id, name));
        }
        customers.forEach(System.out::println);
    }
}