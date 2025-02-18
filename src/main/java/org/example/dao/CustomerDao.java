package org.example.dao;

import org.example.model.Customer;

import java.util.List;

public interface CustomerDao {

    void save (Customer customer);

    void update(Customer customer);

    void delete(int id);

    Customer findById(int id);

    List<Customer> findAll();
}
