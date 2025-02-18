package org.example.dao;

import org.example.CustomerRowMapper;
import org.example.model.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CustomerDaoImplTest {

    private JdbcTemplate jdbcTemplate;
    private CustomerDaoImpl customerDao;

    @BeforeEach
    void setUp() {
        jdbcTemplate = mock(JdbcTemplate.class);
        customerDao = new CustomerDaoImpl(jdbcTemplate);
    }

    @Test
    void testSave() {
        Customer customer = new Customer(0, "John", "john@ukr.net", "123-45-67-890");
        customerDao.save(customer);
        verify(jdbcTemplate, times(1)).update(
                eq("INSERT INTO Customer (fullName, email, socialSecurityNumber) VALUES (?, ?, ?)"),
                eq("John"),
                eq("john@ukr.net"),
                eq("123-45-67-890")
        );
    }

    @Test
    void testFindById() {
        Customer customer = new Customer(1, "John Doe", "john@example.com", "123-45-6789");
        when(jdbcTemplate.queryForObject(anyString(), any(CustomerRowMapper.class), any()))
                .thenReturn(customer);

        Customer foundCustomer = customerDao.findById(1);
        assertEquals(customer, foundCustomer);
    }

    @Test
    void testUpdate() {
        Customer customer = new Customer(1, "John Doe", "john@example.com", "123-45-6789");
        customerDao.update(customer);
        verify(jdbcTemplate, times(1)).update(anyString(), anyString(), anyString(), anyString(), anyInt());
    }


    @Test
    void testDeleteById() {
        customerDao.delete(1);
        verify(jdbcTemplate, times(1)).update(
                eq("DELETE FROM Customer WHERE id = ?"),
                eq(1)
        );
    }


    @Test
    void testFindAll() {
        List<Customer> customers = List.of(
                new Customer(1, "John Doe", "john@example.com", "123-45-6789"),
                new Customer(2, "Jane Doe", "jane@example.com", "987-65-4321")
        );
        when(jdbcTemplate.query(anyString(), any(CustomerRowMapper.class))).thenReturn(customers);

        List<Customer> foundCustomers = customerDao.findAll();
        assertEquals(customers.size(), foundCustomers.size());
    }
}
