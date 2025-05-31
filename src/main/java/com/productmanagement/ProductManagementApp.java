package com.productmanagement;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class ProductManagementApp extends JFrame {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ProductFrame().setVisible(true));
    }
}

class Product {
    int id;
    String name;
    String description;
    String farmer;
    File image1, image2, image3;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;

    public Product(int id, String name, String description, String farmer, File image1, File image2, File image3, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.farmer = farmer;
        this.image1 = image1;
        this.image2 = image2;
        this.image3 = image3;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}

class ProductFrame extends JFrame {
    private final DefaultTableModel tableModel;
    private final JTable table;
    private final java.util.List<Product> mockData = new ArrayList<>();
    private int currentId = 1;

    public ProductFrame() {
        setTitle("CRUD Productos");
        setSize(900, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        setContentPane(panel);

        String[] columns = {"ID", "Nombre", "Descripción", "Agricultor"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        JButton addButton = new JButton("Agregar Producto");
        JButton editButton = new JButton("Editar Producto");
        JButton deleteButton = new JButton("Eliminar Producto");
        JButton viewButton = new JButton("Ver Detalles");

        JPanel buttons = new JPanel();
        buttons.add(addButton);
        buttons.add(editButton);
        buttons.add(deleteButton);
        buttons.add(viewButton);

        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(buttons, BorderLayout.SOUTH);

        addButton.addActionListener(e -> openForm(null));
        editButton.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) openForm(mockData.get(row));
        });
        deleteButton.addActionListener(e -> deleteProduct());
        viewButton.addActionListener(e -> viewProduct());

        loadMockData();
    }

    private void openForm(Product product) {
        JDialog dialog = new JDialog(this, product == null ? "Nuevo Producto" : "Editar Producto", true);
        dialog.setSize(500, 500);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new GridLayout(0, 2, 10, 10));

        JTextField nameField = new JTextField(product != null ? product.name : "");
        JTextArea descField = new JTextArea(product != null ? product.description : "");
        JTextField farmerField = new JTextField(product != null ? product.farmer : "");
        JButton img1Btn = new JButton("Seleccionar Imagen 1");
        JButton img2Btn = new JButton("Seleccionar Imagen 2");
        JButton img3Btn = new JButton("Seleccionar Imagen 3");

        JLabel img1Label = new JLabel(product != null && product.image1 != null ? product.image1.getName() : "");
        JLabel img2Label = new JLabel(product != null && product.image2 != null ? product.image2.getName() : "");
        JLabel img3Label = new JLabel(product != null && product.image3 != null ? product.image3.getName() : "");

        File[] selectedFiles = new File[3];

        img1Btn.addActionListener(e -> selectedFiles[0] = selectFile(img1Label));
        img2Btn.addActionListener(e -> selectedFiles[1] = selectFile(img2Label));
        img3Btn.addActionListener(e -> selectedFiles[2] = selectFile(img3Label));

        dialog.add(new JLabel("Nombre:"));
        dialog.add(nameField);
        dialog.add(new JLabel("Descripción:"));
        dialog.add(new JScrollPane(descField));
        dialog.add(new JLabel("Agricultor:"));
        dialog.add(farmerField);
        dialog.add(img1Btn);
        dialog.add(img1Label);
        dialog.add(img2Btn);
        dialog.add(img2Label);
        dialog.add(img3Btn);
        dialog.add(img3Label);

        JButton saveBtn = new JButton("Guardar");
        dialog.add(saveBtn);
        dialog.setLayout(new FlowLayout());
        dialog.add(saveBtn);

        saveBtn.addActionListener(e -> {
            if (product == null) {
                Product newP = new Product(
                        currentId++, nameField.getText(), descField.getText(), farmerField.getText(),
                        selectedFiles[0], selectedFiles[1], selectedFiles[2],
                        LocalDateTime.now(), LocalDateTime.now()
                );
                mockData.add(newP);
            } else {
                product.name = nameField.getText();
                product.description = descField.getText();
                product.farmer = farmerField.getText();
                if (selectedFiles[0] != null) product.image1 = selectedFiles[0];
                if (selectedFiles[1] != null) product.image2 = selectedFiles[1];
                if (selectedFiles[2] != null) product.image3 = selectedFiles[2];
                product.updatedAt = LocalDateTime.now();
            }
            dialog.dispose();
            refreshTable();
        });

        dialog.setVisible(true);
    }

    private void viewProduct() {
        int row = table.getSelectedRow();
        if (row < 0) return;
        Product p = mockData.get(row);
        JOptionPane.showMessageDialog(this,
                "ID: " + p.id +
                        "\nNombre: " + p.name +
                        "\nDescripción: " + p.description +
                        "\nAgricultor: " + p.farmer +
                        "\nImagen 1: " + (p.image1 != null ? p.image1.getName() : "-") +
                        "\nImagen 2: " + (p.image2 != null ? p.image2.getName() : "-") +
                        "\nImagen 3: " + (p.image3 != null ? p.image3.getName() : "-") +
                        "\nCreado: " + p.createdAt +
                        "\nActualizado: " + p.updatedAt,
                "Detalles del Producto", JOptionPane.INFORMATION_MESSAGE);
    }

    private void deleteProduct() {
        int row = table.getSelectedRow();
        if (row >= 0 && JOptionPane.showConfirmDialog(this, "¿Estás seguro de eliminar este producto?", "Confirmar", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            mockData.remove(row);
            refreshTable();
        }
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        for (Product p : mockData) {
            tableModel.addRow(new Object[]{p.id, p.name, p.description, p.farmer});
        }
    }

    private void loadMockData() {
        mockData.add(new Product(currentId++, "Manzanas", "Manzanas frescas", "Pedro", null, null, null, LocalDateTime.now(), LocalDateTime.now()));
        mockData.add(new Product(currentId++, "Peras", "Peras orgánicas", "Juan", null, null, null, LocalDateTime.now(), LocalDateTime.now()));
        refreshTable();
    }

    private File selectFile(JLabel label) {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new FileNameExtensionFilter("Imágenes", "jpg", "jpeg", "png"));
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            label.setText(file.getName());
            return file;
        }
        return null;
    }

    // Código para usar la base de datos (comentado por defecto)
    /*
    private Connection connectDB() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/tu_basededatos";
        String user = "root";
        String pass = "password";
        return DriverManager.getConnection(url, user, pass);
    }

    private void loadFromDB() throws SQLException {
        Connection conn = connectDB();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM productos");
        while (rs.next()) {
            Product p = new Product(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("description"),
                rs.getString("farmer"),
                new File(""), null, null,
                rs.getTimestamp("created_at").toLocalDateTime(),
                rs.getTimestamp("updated_at").toLocalDateTime()
            );
            mockData.add(p);
        }
        conn.close();
    }
    */
}