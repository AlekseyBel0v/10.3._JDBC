package com.belov.codeexample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;
import java.sql.*;
import java.util.*;

@SpringBootApplication
public class CommandLineApp implements CommandLineRunner {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(CommandLineApp.class);
        //отключаем баннер spring boot, если не хотим видеть его лого в консоли
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
    }

    @Override
    public void run(String... args) throws SQLException {
//        //устанавливаем связь с базой данных через DataSource
//        //соединение с базой данных будет установлено уже в пуле соединений
//        Connection connection = dataSource.getConnection();
//        PreparedStatement statement = connection.prepareStatement(
//                "select id, name from netology.customers"
//        );
//        ResultSet resultSet = statement.executeQuery();
//        List<Customer> customers = new ArrayList<>();
//        while (resultSet.next()) {
//            long id = resultSet.getInt("id"); //В параметр передаем колонку. Можно также ставить номер колонки
//            String name = resultSet.getString("name");
//            customers.add(new Customer(id, name));
//        }
//        customers.forEach(System.out::println);


//        // Установка соединения и отправка запроса с помощью jdbcTemplate
//        // jdbcTemplate сам создает dataSource
//        List<Customer> customers = jdbcTemplate.query(
//                "select id, name " +
//                        "from netology.customers " +
//                        "where id = ?", (resultSet, rowNum) -> {
//                    long id = resultSet.getInt("id"); //В параметр передаем колонку. Можно также ставить номер колонки
//                    String name = resultSet.getString("name");
//                    return new Customer(id, name);
//                },
//                3);  //в данном случае "3" будет передано в запрос в качестве параметра вместо "?".
//        customers.forEach(System.out::println);


        // Установка соединения и отправка запроса с помощью namedParameterJdbcTemplate
        //namedParameterJdbcTemplate создает DataSource и передает его в jdbcTemplate
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("id1", 3);
        paramMap.put("id2", 5);
        List<Customer> customers = namedParameterJdbcTemplate.query(
                "select id, name from netology.customers where id = :id1 or id = :id2", //":" перед id - это подсказка, что нужно подставить сюда
                paramMap,   //  мапа с параметрами
                (resultSet, rowNum) -> {
                    long id = resultSet.getInt("id"); //В параметр передаем колонку. Можно также ставить номер колонки
                    String name = resultSet.getString("name");
                    return new Customer(id, name);
                });
        customers.forEach(System.out::println);
    }
}