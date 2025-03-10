package org.example;


import org.example.dao.CustomerDaoImpl;
import org.example.model.Customer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class App {

    public static void main(String[] args) {
        String dbUser = System.getenv("DB_USER");
        String dbPassword = System.getenv("DB_PASSWORD");

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/my_database");
        dataSource.setUsername(dbUser);
        dataSource.setPassword(dbPassword);


        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        CustomerDaoImpl customerDao = new CustomerDaoImpl(jdbcTemplate);

        createTable(jdbcTemplate);

        Customer newCustomer = new Customer(0, "Patric", "patric@example.com", "444-555-222");
        customerDao.save(newCustomer);

        Customer foundCustomer = customerDao.findById(1);
        System.out.println("Found customer: " + foundCustomer);
    }

    public static void createTable(JdbcTemplate jdbcTemplate) {
        String sql = "CREATE TABLE IF NOT EXISTS Customer (" +
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "fullName VARCHAR(255) NOT NULL, " +
                "email VARCHAR(255) NOT NULL, " +
                "socialSecurityNumber VARCHAR(50) NOT NULL)";

        jdbcTemplate.execute(sql);
    }

}
