package org.example.dao;

import org.example.CustomerRowMapper;
import org.example.model.Customer;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class CustomerDaoImpl implements CustomerDao {

    private final JdbcTemplate jdbcTemplate;

    public CustomerDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(Customer customer) {
        String sql = "INSERT INTO Customer (fullName, email, socialSecurityNumber) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, customer.getFullName(), customer.getEmail(), customer.getSocialSecurityNumber());
    }

    @Override
    public Customer findById(int id) {
        String sql = "SELECT * FROM Customer WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new CustomerRowMapper(), id);
    }

    @Override
    public void update(Customer customer) {
        String sql = "UPDATE Customer SET fullName = ?, email = ?, socialSecurityNumber = ? WHERE id = ?";
        jdbcTemplate.update(sql, customer.getFullName(), customer.getEmail(), customer.getSocialSecurityNumber(), customer.getId());
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM Customer WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }


    @Override
    public List<Customer> findAll() {
        String sql = "SELECT * FROM Customer";
        return jdbcTemplate.query(sql, new CustomerRowMapper());
    }
}
