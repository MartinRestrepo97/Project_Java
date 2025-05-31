package com.productmanagement.controller;

import com.productmanagement.dao.ProductDAO;
import com.productmanagement.model.Product;

import java.sql.SQLException;
import java.util.List;

public class ProductController {
    private final ProductDAO productDAO;

    public ProductController() {
        this.productDAO = new ProductDAO();
    }

    public List<Product> getAllProducts() throws SQLException {
        return productDAO.getAllProducts();
    }

    public Product getProductById(int id) throws SQLException {
        return productDAO.getProductById(id);
    }

    public void createProduct(Product product) throws SQLException {
        productDAO.createProduct(product);
    }

    public void updateProduct(Product product) throws SQLException {
        productDAO.updateProduct(product);
    }

    public void deleteProduct(int id) throws SQLException {
        productDAO.deleteProduct(id);
    }
}
