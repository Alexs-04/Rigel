package atlix.ui;

import atlix.data.Product;
import atlix.logic.services.ProductsService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.Objects;

public class InventoryUI extends JFrame {

    private final DefaultTableModel model;

    private final ProductsService service = new ProductsService();

    public InventoryUI() {
        setTitle("Lista de Productos");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
        setResizable(false);
        setIconImage(new ImageIcon(Objects.requireNonNull(getClass().getResource("/img/icon.png"))).getImage());
        // Crear columnas
        String[] col = {"Código de Barras", "Nombre", "Stock", "Precio"};
        model = new DefaultTableModel(col, 0);
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        // Cargar datos
        loadProducts();

        add(scrollPane, BorderLayout.CENTER);
    }

    private void loadProducts() {
        List<Product> products = service.getProductsLowsStock();
        for (Product p : products) {
            Object[] fila = {
                    p.barCode(),
                    p.name(),
                    p.stock(),
                    p.price()
            };
            model.addRow(fila);
        }
    }
}
